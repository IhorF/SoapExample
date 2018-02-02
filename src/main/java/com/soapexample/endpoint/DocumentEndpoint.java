package com.soapexample.endpoint;

import com.soapexample.generated.StoreDocumentRequest;
import com.soapexample.generated.StoreDocumentResponse;
import com.soapexample.somelogic.WordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.context.MessageContext;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import org.springframework.ws.soap.saaj.SaajSoapMessage;

import javax.activation.DataHandler;
import java.io.*;
import java.math.BigInteger;

import static com.soapexample.ProjectConstants.NAMESPACE_URI;

@Endpoint
public class DocumentEndpoint {
    final private Logger LOGGER = LoggerFactory.getLogger(DocumentEndpoint.class);

    @Autowired
	private WordService wordService;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "storeDocumentRequest")
    @ResponsePayload
    public StoreDocumentResponse storeDocument(@RequestPayload StoreDocumentRequest request, MessageContext context)
			throws IOException {

        StoreDocumentResponse response = new StoreDocumentResponse();
		SaajSoapMessage message = (SaajSoapMessage) context.getRequest();
		DataHandler handler = message.getAttachment("content").getDataHandler();
        response.setCount(BigInteger.valueOf(wordService.getOccurrenceInStream(request.getWord(),
				request.getContent().getInputStream())));

        return response;
    }
}
