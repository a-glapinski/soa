package pl.poznan.put.soapservice.rabbit

import mu.KotlinLogging
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Component
import pl.poznan.put.soapservice.account.event.AccountDeleted
import pl.poznan.put.soapservice.payment.PaymentService
import pl.poznan.put.soapservice.rabbit.config.EventsConfig.Companion.REST_QUEUE

@Component
class RabbitEventListener(
    private val paymentService: PaymentService
) {
    private val logger = KotlinLogging.logger {}

    @RabbitListener(queues = [REST_QUEUE])
    fun handle(event: RabbitEvent<AccountDeleted>) {
        logger.info { "Received event: $event" }
        paymentService.handle(event.payload)
    }
}