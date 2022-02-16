package pl.poznan.put.gateway.payment.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.oxm.jaxb.Jaxb2Marshaller
import pl.poznan.put.gateway.payment.PaymentServiceClient

@Configuration
class PaymentServiceClientConfig {
    @Bean
    fun marshaller(): Jaxb2Marshaller =
        Jaxb2Marshaller().apply {
            contextPath = "pl.poznan.put.gateway.wsdl"
        }

    @Bean
    fun paymentServiceClient(marshaller: Jaxb2Marshaller): PaymentServiceClient =
        PaymentServiceClient().apply {
            this.defaultUri = "http://localhost:8080/ws"
            this.marshaller = marshaller
            this.unmarshaller = marshaller
        }
}