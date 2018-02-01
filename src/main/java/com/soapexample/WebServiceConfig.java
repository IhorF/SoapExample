package com.soapexample;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.soap.saaj.SaajSoapMessageFactory;
import org.springframework.ws.soap.server.endpoint.SoapFaultAnnotationExceptionResolver;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

import javax.xml.soap.SOAPException;
import java.util.regex.Pattern;

/**
 * Created by Ivan on 28.01.2018.
 */
@EnableWs
@Configuration
public class WebServiceConfig extends WsConfigurerAdapter {
    @Bean
    public ServletRegistrationBean messageDispatcherServlet(ApplicationContext applicationContext) {
        MessageDispatcherServlet servlet = new MessageDispatcherServlet();
        servlet.setApplicationContext(applicationContext);
        servlet.setTransformWsdlLocations(true);
        return new ServletRegistrationBean(servlet, "/ws/*");
    }

    @Bean(name = "someObjects")
    public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema objectsSchema) {
        DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
        wsdl11Definition.setPortTypeName("SomeObjectPort");
        wsdl11Definition.setLocationUri("/ws");
        wsdl11Definition.setTargetNamespace("http://generated.soapexample.com");
        wsdl11Definition.setSchema(objectsSchema);
        return wsdl11Definition;
    }

    @Bean
    public XsdSchema objectsSchema() {
        return new SimpleXsdSchema(new ClassPathResource("example.xsd"));
    }

    @Bean
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        // this package must match the package in the <generatePackage> specified in
        // pom.xml
        marshaller.setPackagesToScan("com.soapexample.generated");
        //marshaller.setSchema(new ClassPathResource("example.xsd"));
        marshaller.setMtomEnabled(true);
        return marshaller;
    }

    @Bean
    public SaajSoapMessageFactory saajSoapMessageFactory() {
        SaajSoapMessageFactory factory = null;
        try {
            factory = new SaajSoapMessageFactory(javax.xml.soap.MessageFactory.newInstance());
        } catch (SOAPException e) {
            e.printStackTrace();
        }

        return factory;
    }

    @Bean
    public SoapFaultAnnotationExceptionResolver exceptionResolver() {

        return new SoapFaultAnnotationExceptionResolver();
    }
}
