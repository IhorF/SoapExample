package com.soapexample.endpoint;

import com.soapexample.generated.DownloadMessageRequest;
import com.soapexample.generated.DownloadMessageResponse;
import com.soapexample.generated.DownloadResponseType;
import com.soapexample.somelogic.ObjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import javax.activation.DataHandler;
import java.net.MalformedURLException;

import static com.soapexample.ProjectConstants.NAMESPACE_URI;

/**
 * Created by Ivan on 28.01.2018.
 */
@Endpoint
public class FileTransferEndpoint {
    @Autowired
    private ObjectService objectService;

    @PayloadRoot(localPart = "downloadMessageRequest", namespace = NAMESPACE_URI)
    @ResponsePayload
    public DownloadMessageResponse getCSVFile(@RequestPayload DownloadMessageRequest request) {
        DownloadResponseType responseType = new DownloadResponseType();
        System.out.println("Got an ID: " + request.getRandomInt().intValue());

        try {
            DownloadResponseType.PayLoad handler = new DownloadResponseType.PayLoad();

            handler.setMessagePayLoad(new DataHandler(objectService.getObjectsInCSVFile().toURI().toURL()));
            responseType.setPayLoad(handler);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        DownloadMessageResponse response = new DownloadMessageResponse();
        response.setDownloadResponse(responseType);

        return response;
    }
}
