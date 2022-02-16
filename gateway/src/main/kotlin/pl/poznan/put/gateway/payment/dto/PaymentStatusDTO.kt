package pl.poznan.put.gateway.payment.dto

import pl.poznan.put.gateway.wsdl.PaymentStatus as PaymentStatusXML

enum class PaymentStatusDTO {
    ISSUED,
    SETTLED;

    companion object {
        operator fun invoke(paymentStatus: PaymentStatusXML) =
            when (paymentStatus) {
                PaymentStatusXML.ISSUED -> ISSUED
                PaymentStatusXML.SETTLED -> SETTLED
            }
    }
}
