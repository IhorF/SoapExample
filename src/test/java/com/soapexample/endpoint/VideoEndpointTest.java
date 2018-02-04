package com.soapexample.endpoint;

import com.soapexample.ProjectConstants;
import com.soapexample.generated.*;
import com.soapexample.matcher.SoapFaultResponseMatcher;
import com.soapexample.util.SourceBuilder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.ws.mime.Attachment;
import org.springframework.ws.soap.saaj.SaajSoapMessage;
import org.springframework.ws.test.server.MockWebServiceClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static com.soapexample.constants.VideoFunctionalityTestConstant.*;
import static org.junit.Assert.assertNotNull;
import static org.springframework.ws.test.server.RequestCreators.withPayload;
import static org.springframework.ws.test.server.ResponseMatchers.noFault;
import static org.springframework.ws.test.server.ResponseMatchers.payload;
import static org.springframework.ws.test.server.ResponseMatchers.serverOrReceiverFault;
import static org.springframework.ws.test.server.ResponseMatchers.validPayload;

/**
 * Created by Ivan.Malynovskyi on 01.02.2018  12:29.
 *
 * Tests {@link VideoFilesEndpoint}
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class VideoEndpointTest {
	@Autowired
	private ApplicationContext applicationContext;

	private MockWebServiceClient mockClient;
	private Resource xsdSchema = new ClassPathResource("example.xsd");
	private Jaxb2Marshaller marshaller;

	@Before
	public void init() {
		mockClient = MockWebServiceClient.createClient(applicationContext);
		marshaller = (Jaxb2Marshaller) applicationContext.getBean("marshaller");
	}

	@Test
	public void getFileNamesTest()
			throws IOException {
		GetFileNamesRequest request = new GetFileNamesRequest();
		GetFileNamesResponse response = new GetFileNamesResponse();

		response.getFileNamesList().addAll(expectedListOfNames);

		SourceBuilder<GetFileNamesRequest> requestSourceBuilder = new SourceBuilder<>(marshaller);
		SourceBuilder<GetFileNamesResponse> responseSourceBuilder = new SourceBuilder<>(marshaller);

		mockClient.sendRequest(withPayload(requestSourceBuilder.buildSource(request)))
				.andExpect(noFault())
				.andExpect(payload(responseSourceBuilder.buildSource(response)))
				.andExpect(validPayload(xsdSchema));
	}

	@Test
	public void getFileByUnExistingNameTest() {
		GetFileRequest request = getFileRequestObject("someFileName");
		SourceBuilder<GetFileRequest> sourceBuilder = new SourceBuilder<>(marshaller);

		mockClient.sendRequest(withPayload(sourceBuilder.buildSource(request)))
				.andExpect(serverOrReceiverFault(NullPointerException.class.getName()));
	}

	@Test
	public void getToBigFileByExistingNameTest() {
		GetFileRequest request = getFileRequestObject(FILE_WHICH_BIGGER_THAN_LIMIT);
		SourceBuilder<GetFileRequest> sourceBuilder = new SourceBuilder<>(marshaller);

		mockClient.sendRequest(withPayload(sourceBuilder.buildSource(request)))
				.andExpect(new SoapFaultResponseMatcher("{" + ProjectConstants.NAMESPACE_URI + "}SERVER_SPECIFIC",
						"Size of file is to big"));
	}

	@Test
	public void getFileByExistingNameSuccessTest() throws IOException {
		GetFileRequest request = getFileRequestObject(expectedListOfNames.get(0));
		SourceBuilder<GetFileRequest> sourceBuilder = new SourceBuilder<>(marshaller);

		mockClient.sendRequest(withPayload(sourceBuilder.buildSource(request)))
				.andExpect(noFault())
				.andExpect((requestMessage, responseMessage) -> {
					SaajSoapMessage responseSAAJMessage = (SaajSoapMessage) responseMessage;
					Attachment attachment = responseSAAJMessage.getAttachment("file");
					assertNotNull(attachment);

					BufferedReader reader = new BufferedReader(new InputStreamReader(attachment.getInputStream()));
					assertNotNull(reader.readLine());
				});
	}

	private GetFileRequest getFileRequestObject(String name) {
		GetFileRequest request = new GetFileRequest();
		request.setFileName(name);

		return request;
	}
}
