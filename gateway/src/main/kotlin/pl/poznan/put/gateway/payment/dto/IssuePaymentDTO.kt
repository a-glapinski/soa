package pl.poznan.put.gateway.payment.dto

import java.math.BigDecimal

data class IssuePaymentDTO(
    val accountUsername: String,
    val amount: BigDecimal
)