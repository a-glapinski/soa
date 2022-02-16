package pl.poznan.put.restservice.rabbit

import mu.KotlinLogging
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.stereotype.Component
import pl.poznan.put.restservice.rabbit.config.EventsConfig.Companion.REST_QUEUE

@Component
class RabbitEventPublisher(
    private val rabbitTemplate: RabbitTemplate
) {
    private val logger = KotlinLogging.logger {}

    fun <T> publish(event: RabbitEvent<T>) {
        try {
            rabbitTemplate.convertAndSend(REST_QUEUE, event)
            logger.info { "Published event: $event" }
        } catch (ex: Exception) {
            logger.error(ex) { "Sending event: $event failed" }
        }
    }
}