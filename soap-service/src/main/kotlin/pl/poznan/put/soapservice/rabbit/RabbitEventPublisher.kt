package pl.poznan.put.soapservice.rabbit

import mu.KotlinLogging
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.stereotype.Component
import pl.poznan.put.soapservice.rabbit.config.EventsConfig.Companion.SOAP_QUEUE

@Component
class RabbitEventPublisher(
    private val rabbitTemplate: RabbitTemplate
) {
    private val logger = KotlinLogging.logger {}

    fun <T> publish(event: RabbitEvent<T>) {
        try {
            rabbitTemplate.convertAndSend(SOAP_QUEUE, event)
            logger.info { "Event: $event published" }
        } catch (ex: Exception) {
            logger.error(ex) { "Sending event: $event failed" }
        }
    }
}