package com.soapexample.endpoint;

import com.soapexample.generated.StoreDocumentRequest;
import com.soapexample.generated.StoreDocumentResponse;
import com.soapexample.service.WordOccurenceTest;
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
import org.springframework.ws.test.server.MockWebServiceClient;

import javax.activation.DataHandler;

import java.io.*;
import java.net.MalformedURLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.springframework.ws.test.server.RequestCreators.withPayload;
import static org.springframework.ws.test.server.ResponseMatchers.*;

/**
 * Created by Ivan.Malynovskyi on 01.02.2018  12:29.
 *
 * Tests {@link DocumentEndpoint}
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class WordCountEndpointTest {
	@Autowired
	private ApplicationContext applicationContext;

	private MockWebServiceClient mockClient;
	private Resource xsdSchema = new ClassPathResource("example.xsd");
	private Jaxb2Marshaller marshaller;
	private SourceBuilder<StoreDocumentRequest> sourceBuilder;

	private static String WORD = "test";

	@Before
	public void init() {
		mockClient = MockWebServiceClient.createClient(applicationContext);
		marshaller = (Jaxb2Marshaller) applicationContext.getBean("marshaller");
		sourceBuilder = new SourceBuilder<>(marshaller);
	}

	@Test
	public void getWordCountFromFileTest() {
		StoreDocumentRequest requestObject = new StoreDocumentRequest();
		requestObject.setWord(WORD);

		mockClient.sendRequest(withPayload(sourceBuilder.buildSource(requestObject)))
				.andExpect(serverOrReceiverFault(NullPointerException.class.getName()));
	}

	@Test
	public void getWordCountFromValidFileTest() throws IOException {
		File file = new File("src/test/resources/haveText.txt");
		StoreDocumentRequest requestObject = new StoreDocumentRequest();
		requestObject.setWord(WORD);

		try {
			requestObject.setContent(new DataHandler(file.toURI().toURL()));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		mockClient.sendRequest(withPayload(sourceBuilder.buildSource(requestObject)))
				.andExpect(noFault())
				.andExpect((requestMessage, responseMessage) -> {
					StoreDocumentResponse response = (StoreDocumentResponse) marshaller
							.unmarshal(responseMessage.getPayloadSource());

					assert response.getCount().intValue() == getExpectedCount(file, WORD);
				})
				.andExpect(validPayload(xsdSchema));
	}

	public static int getExpectedCount(File file, String word) throws IOException {
		int expectedCount = 0;

		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
		Pattern pattern = WordOccurenceTest.getPattern(word);
		String line;
		while ((line = reader.readLine()) != null) {
			Matcher matcher = pattern.matcher(line);
			while (matcher.find()) {
				expectedCount++;
			}
		}

		return expectedCount;
	}
}
