package pl.poznan.put.restservice.rabbit

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.amqp.core.Queue
import org.springframework.amqp.core.QueueBuilder
import org.springframework.amqp.rabbit.annotation.EnableRabbit
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@EnableRabbit
class EventsConfig {
    @Bean
    fun messageConverter(objectMapper: ObjectMapper) =
        Jackson2JsonMessageConverter(objectMapper)

    @Bean
    fun soapQueue(): Queue =
        QueueBuilder.durable(SOAP_QUEUE)
            .withArgument("x-dead-letter-exchange", "dead_letter_exchange")
            .withArgument("x-dead-letter-routing-key", "dead_letter_queue")
            .build()

    @Bean
    fun restQueue(): Queue =
        QueueBuilder.durable(REST_QUEUE)
            .withArgument("x-dead-letter-exchange", "dead_letter_exchange")
            .withArgument("x-dead-letter-routing-key", "dead_letter_queue")
            .build()

    companion object {
        const val REST_QUEUE = "rest_queue"
        const val SOAP_QUEUE = "soap_queue"
    }
}