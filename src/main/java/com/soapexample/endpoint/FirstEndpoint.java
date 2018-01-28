package com.soapexample.endpoint;

import com.soapexample.somelogic.RandomFactory;
import com.soapexample.generated.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.math.BigInteger;

/**
 * Created by Ivan on 28.01.2018.
 */
@Endpoint
public class FirstEndpoint {

    @Autowired
    private RandomFactory randomFactory;

    @PayloadRoot(namespace = "http://generated.soapexample.com", localPart = "getObjectRequest")
    @ResponsePayload
    public GetObjectResponse getObject(@RequestPayload GetObjectRequest userName) {
        System.out.println(userName.getUserName());

        SomeRandomObject randomObject = randomFactory.getSomeRandomObject();
        randomObject.setRandomString(userName.getUserName() + " - " + randomObject.getRandomString());

        GetObjectResponse objectResponse = new GetObjectResponse();
        objectResponse.setObject(randomObject);

        return objectResponse;
    }

    @PayloadRoot(namespace = "http://generated.soapexample.com", localPart = "getRandomIntRequest")
    @ResponsePayload
    public GetRandomIntResponse getRandomInt(@RequestPayload GetRandomIntRequest request) {
        int maxValue = request.getRandomIntMax().intValue();
        System.out.println("Got a max parameter = " + maxValue);

        GetRandomIntResponse response = new GetRandomIntResponse();
        response.setRandomInt(new BigInteger(String.valueOf(randomFactory.getRandomInteger(maxValue))));

        return response;
    }
}
