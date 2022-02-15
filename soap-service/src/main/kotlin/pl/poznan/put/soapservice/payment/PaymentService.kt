package pl.poznan.put.soapservice.payment

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import pl.poznan.put.soapservice.account.event.AccountDeleted
import pl.poznan.put.soapservice.account.event.RollbackAccountDeleted
import pl.poznan.put.soapservice.payment.model.Payment
import pl.poznan.put.soapservice.payment.model.PaymentRepository
import pl.poznan.put.soapservice.payment.model.PaymentStatus.ISSUED
import pl.poznan.put.soapservice.payment.xml.IssuePaymentXML
import pl.poznan.put.soapservice.payment.xml.PaymentXML
import pl.poznan.put.soapservice.payment.xml.toPaymentEntity
import pl.poznan.put.soapservice.payment.xml.toPaymentXML
import pl.poznan.put.soapservice.rabbit.EventType
import pl.poznan.put.soapservice.rabbit.RabbitEvent
import pl.poznan.put.soapservice.rabbit.RabbitEventPublisher
import java.util.*

@Service
class PaymentService(
    private val paymentRepository: PaymentRepository,
    private val eventPublisher: RabbitEventPublisher
) {
    fun getAccountPayments(accountUsername: String): List<PaymentXML> =
        paymentRepository
            .findAllByAccountUsername(accountUsername)
            .map { it.toPaymentXML() }

    fun issuePayment(issuePayment: IssuePaymentXML): PaymentXML =
        paymentRepository
            .save(issuePayment.toPaymentEntity())
            .let(Payment::toPaymentXML)

    fun settlePayment(id: String): PaymentXML? =
        paymentRepository
            .findByIdOrNull(UUID.fromString(id))
            ?.settle()
            ?.let(paymentRepository::save)
            ?.toPaymentXML()

    fun handle(event: AccountDeleted) {
        if (paymentRepository.findAllByAccountUsername(event.username).any { it.status == ISSUED }) {
            eventPublisher.publish(
                RabbitEvent(
                    payload = RollbackAccountDeleted(event),
                    type = EventType.RollbackAccountDeleted
                )
            )
        } else {
            paymentRepository.deleteAllByAccountUsername(event.username)
        }
    }
}