package pl.poznan.put.restservice.rabbit

import mu.KotlinLogging
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Component
import pl.poznan.put.restservice.account.AccountService
import pl.poznan.put.restservice.account.event.RollbackAccountDeleted
import pl.poznan.put.restservice.rabbit.config.EventsConfig.Companion.SOAP_QUEUE

@Component
class RabbitEventListener(
    private val accountService: AccountService
) {
    private val logger = KotlinLogging.logger {}

    @RabbitListener(queues = [SOAP_QUEUE])
    fun handle(event: RabbitEvent<RollbackAccountDeleted>) {
        logger.info { "Received event: $event" }
        accountService.handle(event.payload)
    }
}