package pl.poznan.put.restservice.rabbit

import java.time.Instant

data class RabbitEvent<T>(
    val payload: T,
    val type: EventType,
    val timestamp: Instant = Instant.now()
)