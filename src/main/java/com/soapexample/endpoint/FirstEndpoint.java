package com.soapexample.endpoint;

import com.soapexample.somelogic.RandomFactory;
import com.soapexample.generated.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.math.BigInteger;

import static com.soapexample.ProjectConstants.NAMESPACE_URI;

/**
 * Created by Ivan on 28.01.2018.
 *
 * Deprecated endpoint.
 *
 * @version 1.0.0
 * @since 1.0.0
 */
@Deprecated
@Endpoint
public class FirstEndpoint {

    @Autowired
    private RandomFactory randomFactory;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getObjectRequest")
    @ResponsePayload
    public GetObjectResponse getObject(@RequestPayload GetObjectRequest userName) {

        SomeRandomObject randomObject = randomFactory.getSomeRandomObject();
        randomObject.setRandomString(userName.getUserName() + " - " + randomObject.getRandomString());

        GetObjectResponse objectResponse = new GetObjectResponse();
        objectResponse.setObject(randomObject);

        return objectResponse;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getRandomIntRequest")
    @ResponsePayload
    public GetRandomIntResponse getRandomInt(@RequestPayload GetRandomIntRequest request) {
        int maxValue = request.getRandomIntMax().intValue();

        GetRandomIntResponse response = new GetRandomIntResponse();
        response.setRandomInt(new BigInteger(String.valueOf(randomFactory.getRandomInteger(maxValue))));

        return response;
    }
}
