package pl.poznan.put.gateway.payment

import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.ws.client.core.support.WebServiceGatewaySupport
import org.springframework.ws.soap.client.core.SoapActionCallback
import pl.poznan.put.gateway.payment.dto.IssuePaymentDTO
import pl.poznan.put.gateway.payment.dto.PaymentDTO
import pl.poznan.put.gateway.wsdl.GetAccountPaymentsRequest
import pl.poznan.put.gateway.wsdl.GetAccountPaymentsResponse
import pl.poznan.put.gateway.wsdl.IssuePayment
import pl.poznan.put.gateway.wsdl.IssuePaymentRequest
import pl.poznan.put.gateway.wsdl.IssuePaymentResponse
import pl.poznan.put.gateway.wsdl.SettlePaymentRequest
import pl.poznan.put.gateway.wsdl.SettlePaymentResponse

class PaymentServiceClient : WebServiceGatewaySupport() {
    @Value("\${services.payment.wsdl.url}")
    private lateinit var paymentsWsdlUrl: String

    private val log = KotlinLogging.logger {}

    fun getAccountPayments(accountUsername: String): List<PaymentDTO> {
        val request = GetAccountPaymentsRequest().apply {
            this.accountUsername = accountUsername
        }

        val response = webServiceTemplate.marshalSendAndReceive(
            paymentsWsdlUrl,
            request,
            SoapActionCallback("$NAMESPACE_URI/GetAccountPaymentsRequest")
        ) as GetAccountPaymentsResponse

        return response.payments.map { PaymentDTO(it) }
    }

    fun settlePayment(transactionId: String) {
        val request = SettlePaymentRequest()
            .apply {
                this.transactionId = transactionId
            }

        log.info { "Settle payment: $transactionId" }

        webServiceTemplate.marshalSendAndReceive(
            paymentsWsdlUrl,
            request,
            SoapActionCallback("$NAMESPACE_URI/SettlePaymentRequest")
        ) as SettlePaymentResponse
    }

    fun issuePayment(issuePaymentDTO: IssuePaymentDTO) {
        val request = IssuePaymentRequest()
            .apply {
                issuePayment = IssuePayment().apply {
                    accountUsername = issuePaymentDTO.accountUsername
                    amount = issuePaymentDTO.amount
                }
            }

        log.info { "Issue payment: ${issuePaymentDTO.amount} for user: ${issuePaymentDTO.accountUsername}" }

        webServiceTemplate.marshalSendAndReceive(
            paymentsWsdlUrl,
            request,
            SoapActionCallback("$NAMESPACE_URI/IssuePaymentRequest")
        ) as IssuePaymentResponse
    }

    private companion object {
        const val NAMESPACE_URI = "http://put.poznan.pl/soap/gen"
    }
}