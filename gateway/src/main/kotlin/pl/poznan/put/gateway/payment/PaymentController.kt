package pl.poznan.put.gateway.payment

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import pl.poznan.put.gateway.payment.dto.IssuePaymentDTO
import pl.poznan.put.gateway.payment.dto.PaymentDTO

@RequestMapping("/payments")
@RestController
class PaymentController(
    private val paymentServiceClient: PaymentServiceClient
) {
    @GetMapping
    fun getUserPayments(@RequestParam accountUsername: String): List<PaymentDTO> =
        paymentServiceClient.getAccountPayments(accountUsername)

    @PutMapping("/settle-payment")
    fun settlePayment(@RequestParam transactionId: String) {
        paymentServiceClient.settlePayment(transactionId)
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/issue-payment")
    fun issuePayment(@RequestBody issuePaymentDTO: IssuePaymentDTO) {
        paymentServiceClient.issuePayment(issuePaymentDTO)
    }
}
