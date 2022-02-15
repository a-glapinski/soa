package pl.poznan.put.soapservice.ws

import org.springframework.boot.web.servlet.ServletRegistrationBean
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ClassPathResource
import org.springframework.ws.config.annotation.EnableWs
import org.springframework.ws.config.annotation.WsConfigurerAdapter
import org.springframework.ws.transport.http.MessageDispatcherServlet
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition
import org.springframework.xml.xsd.SimpleXsdSchema
import org.springframework.xml.xsd.XsdSchema

@EnableWs
@Configuration
class WebServiceConfig : WsConfigurerAdapter() {
    @Bean
    fun messageDispatcherServlet(applicationContext: ApplicationContext): ServletRegistrationBean<MessageDispatcherServlet> {
        val servlet = MessageDispatcherServlet().apply {
            setApplicationContext(applicationContext)
            isTransformWsdlLocations = true
        }
        return ServletRegistrationBean(servlet, "/ws/*")
    }

    @Bean(name = ["payments"])
    fun defaultWsdl11Definition(paymentsSchema: XsdSchema): DefaultWsdl11Definition =
        DefaultWsdl11Definition().apply {
            setPortTypeName("PaymentsPort")
            setLocationUri("/ws")
            setTargetNamespace("http://put.poznan.pl/soap/gen")
            setSchema(paymentsSchema)
        }

    @Bean
    fun countriesSchema(): XsdSchema =
        SimpleXsdSchema(ClassPathResource("payments.xsd"))
}