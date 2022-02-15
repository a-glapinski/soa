package pl.poznan.put.soapservice.payment.model

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface PaymentRepository : JpaRepository<Payment, UUID> {
    fun findAllByAccountUsername(accountUsername: String): List<Payment>
    fun deleteAllByAccountUsername(accountUsername: String)
}