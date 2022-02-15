package pl.poznan.put.soapservice.payment.xml

import pl.poznan.put.soapservice.payment.model.Payment
import pl.poznan.put.soapservice.payment.model.PaymentStatus

fun Payment.toPaymentXML() = PaymentXML().apply {
    transactionId = this@toPaymentXML.transactionId.toString()
    accountUsername = this@toPaymentXML.accountUsername
    amount = this@toPaymentXML.amount
    status = this@toPaymentXML.status.toPaymentStatusXML()
}

fun PaymentStatus.toPaymentStatusXML(): PaymentStatusXML =
    when (this) {
        PaymentStatus.ERROR -> throw IllegalStateException("Error")
        PaymentStatus.ISSUED -> PaymentStatusXML.ISSUED
        PaymentStatus.SETTLED -> PaymentStatusXML.SETTLED
    }

fun IssuePaymentXML.toPaymentEntity() = Payment(
    accountUsername = this.accountUsername,
    amount = this.amount,
    status = PaymentStatus.ISSUED
)