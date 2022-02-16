package pl.poznan.put.soapservice.rabbit

import mu.KotlinLogging
import org.springframework.amqp.rabbit.annotation.RabbitHandler
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Component
import pl.poznan.put.soapservice.account.event.AccountDeleted
import pl.poznan.put.soapservice.payment.PaymentService
import pl.poznan.put.soapservice.rabbit.config.EventsConfig.Companion.REST_QUEUE

@Component
@RabbitListener(queues = [REST_QUEUE])
class RabbitEventListener(
    private val paymentService: PaymentService
) {
    private val logger = KotlinLogging.logger {}

    @RabbitHandler
    fun <T> handle(event: RabbitEvent<T>) {
        logger.info { "Received event: $event" }
        when (event.type) {
            EventType.AccountDeleted -> paymentService.handle(event.payload as AccountDeleted)
            EventType.RollbackAccountDeleted -> throw IllegalStateException()
        }
    }
}