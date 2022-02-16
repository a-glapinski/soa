package pl.poznan.put.restservice.rabbit

import mu.KotlinLogging
import org.springframework.amqp.rabbit.annotation.RabbitHandler
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Component
import pl.poznan.put.restservice.account.AccountService
import pl.poznan.put.restservice.account.event.RollbackAccountDeleted
import pl.poznan.put.restservice.rabbit.config.EventsConfig.Companion.SOAP_QUEUE

@Component
@RabbitListener(queues = [SOAP_QUEUE])
class RabbitEventListener(
//    val rabbitTemplate: RabbitTemplate,
    private val accountService: AccountService
) {
    private val logger = KotlinLogging.logger {}

    @RabbitHandler
    fun <T> handle(event: RabbitEvent<T>) {
//        rabbitTemplate.receiveAndConvert(ParameterizedTypeReference.forType<T>(T::class.java))
        logger.info { "Received event: $event" }
        when (event.type) {
            EventType.AccountDeleted -> throw IllegalStateException()
            EventType.RollbackAccountDeleted -> accountService.handle(event.payload as RollbackAccountDeleted)
        }
    }
}