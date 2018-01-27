package com.soapexample.endpoint;

import com.soapexample.somelogic.RandomFactory;
import io.spring.guides.gs_producing_web_service.SomeRandomObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

/**
 * Created by Ivan on 28.01.2018.
 */
@Endpoint
public class FirstEndpoint {

    @Autowired
    private RandomFactory randomFactory;

    @PayloadRoot(namespace = "https://localhost:8080/wss", localPart = "getObject")
    public @ResponsePayload SomeRandomObject getRandomObject(@RequestPayload String userName) {
        System.out.println(userName);

        SomeRandomObject randomObject = randomFactory.getSomeRandomObject();
        randomObject.setRandomString(userName + " - " + randomObject.getRandomString());

        return randomObject;
    }
}
