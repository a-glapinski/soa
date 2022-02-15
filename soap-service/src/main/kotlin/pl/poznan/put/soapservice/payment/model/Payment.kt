package pl.poznan.put.soapservice.payment.model

import pl.poznan.put.soapservice.payment.model.PaymentStatus.SETTLED
import java.math.BigDecimal
import java.util.*
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
class Payment(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val transactionId: UUID = UUID.randomUUID(),

    val accountUsername: String,

    val amount: BigDecimal,

    @Enumerated(EnumType.STRING)
    var status: PaymentStatus
) {
    fun settle() = apply {
        status = SETTLED
    }
}