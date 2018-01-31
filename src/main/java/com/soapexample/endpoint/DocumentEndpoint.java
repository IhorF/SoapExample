package com.soapexample.endpoint;

import com.soapexample.generated.Document;
import com.soapexample.generated.StoreDocumentRequest;
import com.soapexample.generated.StoreDocumentResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.io.*;
import java.math.BigInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.soapexample.ProjectConstants.NAMESPACE_URI;

@Endpoint
public class DocumentEndpoint {
    final private Logger LOGGER = LoggerFactory.getLogger(DocumentEndpoint.class);

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "storeDocumentRequest")
    @ResponsePayload
    public StoreDocumentResponse storeDocument(@RequestPayload StoreDocumentRequest request)
			throws IOException {

        Document document = request.getDocument();
        BufferedReader reader = null;
        int counter2 = 0;
        try (InputStream in = document.getContent().getInputStream()) {
            long counter = 0;
            while (in.read() != -1) {
                ++counter;
            }
            reader = new BufferedReader(new InputStreamReader(document.getContent()
					.getInputStream()));
            String line;

            LOGGER.info(document.getName());
            Pattern p = Pattern.compile("fff", Pattern.CASE_INSENSITIVE);
            while ((line = reader.readLine()) != null) {
                Matcher m = p.matcher(line);

                while (m.find()) {
                    counter2++;
                }
            }

            LOGGER.info("Received {} bytes. Author: {}. Name: {}",
					counter, document.getAuthor(), document.getName());
        } catch (IOException e) {
			LOGGER.error(e.getMessage());
        } finally {
            try {
                reader.close();
            } catch (IOException | NullPointerException e) {
                LOGGER.error("Can't close BufferedReader. {}", e.getMessage());
            }
        }
        StoreDocumentResponse response = new StoreDocumentResponse();
        response.setCount(BigInteger.valueOf(counter2));
        return response;
    }
}
