package com.soapexample.matcher;

import org.springframework.ws.WebServiceMessage;
import org.springframework.ws.soap.SoapBody;
import org.springframework.ws.soap.SoapFault;
import org.springframework.ws.soap.saaj.SaajSoapMessage;
import org.springframework.ws.test.server.ResponseMatcher;

import javax.xml.namespace.QName;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

/**
 * Created by Ivan.Malynovskyi on 01.02.2018  13:48.
 */
public class SoapFaultResponseMatcher implements ResponseMatcher {
	private final String faultCode;
	private final String faultReason;

	public SoapFaultResponseMatcher(String faultCode, String faultReason) {
		this.faultCode = faultCode;
		this.faultReason = faultReason;
	}

	@Override
	public void match(WebServiceMessage request, WebServiceMessage response) throws IOException, AssertionError {
		SaajSoapMessage soapResponse = (SaajSoapMessage) response;
		SoapBody soapBody = null;
		soapBody = soapResponse.getSoapBody();
		SoapFault soapFault = soapBody.getFault();
		QName expectedFaultCode = new QName(faultCode);
		assertEquals("Invalid SOAP Fault Code", expectedFaultCode.toString(), soapFault.getFaultCode().toString());
		assertEquals("Invalid SOAP Fault string/reason", faultReason, (String) soapFault.getFaultStringOrReason());
	}
}
