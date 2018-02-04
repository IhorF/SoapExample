package com.soapexample.endpoint;

import com.soapexample.generated.StoreDocumentRequest;
import com.soapexample.generated.StoreDocumentResponse;
import com.soapexample.somelogic.WordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.io.*;
import java.math.BigInteger;

import static com.soapexample.ProjectConstants.NAMESPACE_URI;

/**
 * Created by Ivan.Malynovskyi on 30.01.2018  15:13.
 *
 * It is an endpoint class, which responsible for handle request with attachment
 *
 * @version 1.1.1
 * @since 1.0.1
 */
@Endpoint
public class DocumentEndpoint {
    final private Logger LOGGER = LoggerFactory.getLogger(DocumentEndpoint.class);

    @Autowired
	private WordService wordService;

    /**
     * Web service which recieve a word and a file and count a number
     * of occurrence of the word in the file.
     * For this {@link WordService#getOccurrenceInStream(String, InputStream)} is used.
     *
     * @param request object {@link StoreDocumentRequest} with a word and a file
     * @return response of type {@link StoreDocumentRequest} with number of occurrence
     */
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "storeDocumentRequest")
    @ResponsePayload
    public StoreDocumentResponse storeDocument(@RequestPayload StoreDocumentRequest request)
			throws IOException {

        StoreDocumentResponse response = new StoreDocumentResponse();
        response.setCount(BigInteger.valueOf(wordService.getOccurrenceInStream(request.getWord(),
				request.getContent().getInputStream())));

        return response;
    }
}
