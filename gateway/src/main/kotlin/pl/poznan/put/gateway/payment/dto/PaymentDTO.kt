package pl.poznan.put.gateway.payment.dto

import java.math.BigDecimal
import pl.poznan.put.gateway.wsdl.Payment as PaymentXML

data class PaymentDTO(
    val transactionId: String,
    val accountUsername: String,
    val amount: BigDecimal,
    val status: PaymentStatusDTO
) {
    constructor(payment: PaymentXML) : this(
        transactionId = payment.transactionId,
        accountUsername = payment.accountUsername,
        amount = payment.amount,
        status = PaymentStatusDTO(payment.status)
    )
}
