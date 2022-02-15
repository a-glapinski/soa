package pl.poznan.put.soapservice.rabbit

import java.time.Instant

data class RabbitEvent<T>(
    val payload: T,
    val type: EventType,
    val timestamp: Instant = Instant.now()
)