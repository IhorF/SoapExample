package com.soapexample.util;

import com.soapexample.generated.StoreDocumentRequest;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.xml.transform.ResourceSource;
import org.springframework.xml.transform.StringResult;

import javax.xml.transform.Source;
import java.io.IOException;

/**
 * Created by Ivan on 03.02.2018.
 *
 * Generic util class which transform objects, which described in example.xsd,
 * to type {@link Source}
 *
 */
public class SourceBuilder<T> {

    public SourceBuilder(Jaxb2Marshaller marshaller) {
        this.marshaller = marshaller;
    }

    private Jaxb2Marshaller marshaller;

    public Source buildSource(T requestObject) {

        StringResult result = new StringResult();
        marshaller.marshal(requestObject, result);

        Source requestSource = null;
        try {
            requestSource = new ResourceSource(new ByteArrayResource(result.toString().getBytes()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return requestSource;
    }

    public void setMarshaller(Jaxb2Marshaller marshaller) {
        this.marshaller = marshaller;
    }
}
