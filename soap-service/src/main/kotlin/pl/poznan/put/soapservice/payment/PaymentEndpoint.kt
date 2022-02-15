package pl.poznan.put.soapservice.payment

import org.springframework.ws.server.endpoint.annotation.Endpoint
import org.springframework.ws.server.endpoint.annotation.PayloadRoot
import org.springframework.ws.server.endpoint.annotation.RequestPayload
import org.springframework.ws.server.endpoint.annotation.ResponsePayload
import pl.poznan.put.soap.gen.GetAccountPaymentsRequest
import pl.poznan.put.soap.gen.GetAccountPaymentsResponse
import pl.poznan.put.soap.gen.IssuePaymentRequest
import pl.poznan.put.soap.gen.IssuePaymentResponse
import pl.poznan.put.soap.gen.SettlePaymentRequest
import pl.poznan.put.soap.gen.SettlePaymentResponse

@Endpoint
class PaymentEndpoint(
    private val paymentService: PaymentService
) {
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "issuePaymentRequest")
    @ResponsePayload
    fun issuePayment(@RequestPayload request: IssuePaymentRequest): IssuePaymentResponse {
        return IssuePaymentResponse()
            .apply {
                payment = paymentService.issuePayment(request.issuePayment)
            }
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "settlePaymentRequest")
    @ResponsePayload
    fun settlePayment(@RequestPayload request: SettlePaymentRequest): SettlePaymentResponse {
        return SettlePaymentResponse()
            .apply {
                payment = paymentService.settlePayment(request.transactionId)
            }
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAccountPaymentsRequest")
    @ResponsePayload
    fun getUserPayments(@RequestPayload request: GetAccountPaymentsRequest): GetAccountPaymentsResponse {
        return GetAccountPaymentsResponse()
            .apply {
                payments.addAll(paymentService.getAccountPayments(request.accountUsername))
            }
    }

    companion object {
        const val NAMESPACE_URI = "http://put.poznan.pl/soap/gen"
    }
}