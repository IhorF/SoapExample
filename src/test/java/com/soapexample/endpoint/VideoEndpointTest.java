package com.soapexample.endpoint;

import com.soapexample.ProjectConstants;
import com.soapexample.matcher.SoapFaultResponseMatcher;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.ws.soap.saaj.SaajSoapMessage;
import org.springframework.ws.test.server.MockWebServiceClient;
import org.springframework.xml.transform.StringSource;

import javax.xml.transform.Source;

import java.io.IOException;

import static org.springframework.ws.test.server.RequestCreators.withPayload;
import static org.springframework.ws.test.server.ResponseMatchers.noFault;
import static org.springframework.ws.test.server.ResponseMatchers.payload;
import static org.springframework.ws.test.server.ResponseMatchers.serverOrReceiverFault;
import static org.springframework.ws.test.server.ResponseMatchers.validPayload;

/**
 * Created by Ivan.Malynovskyi on 01.02.2018  12:29.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class VideoEndpointTest {
	@Autowired
	private ApplicationContext applicationContext;

	private MockWebServiceClient mockClient;
	private Resource xsdSchema = new ClassPathResource("example.xsd");

	@Before
	public void init(){
		mockClient = MockWebServiceClient.createClient(applicationContext);
	}

	@Test
	public void getFileNamesTest()
			throws IOException {
		Source request = new StringSource("<ns2:getFileNamesRequest xmlns:ns2=\"http://generated.soapexample.com\"/>");

		Source response = new StringSource(
				"<ns2:getFileNamesResponse xmlns:ns2=\"http://generated.soapexample.com\">" +
					"<ns2:fileNamesList>file1.txt</ns2:fileNamesList>" +
					"<ns2:fileNamesList>file2.txt</ns2:fileNamesList>" +
					"<ns2:fileNamesList>file3</ns2:fileNamesList>" +
					"<ns2:fileNamesList>jre-8u91-linux-i586.tar.gz</ns2:fileNamesList>" +
				"</ns2:getFileNamesResponse>");
		mockClient.sendRequest(withPayload(request))
				.andExpect(noFault())
				.andExpect(payload(response))
				.andExpect(validPayload(xsdSchema));
	}

	@Test
	public void getFileByUnExistingNameTest() {
		Source request = new StringSource("<ns2:getFileRequest xmlns:ns2=\"http://generated.soapexample.com\">" +
					"<ns2:fileName>file100</ns2:fileName>" +
				"</ns2:getFileRequest>");

		mockClient.sendRequest(withPayload(request)).andExpect(serverOrReceiverFault(NullPointerException.class.getName()));
	}

	@Test
	public void getToBigFileByExistingNameTest() {
		Source request = new StringSource("<ns2:getFileRequest xmlns:ns2=\"http://generated.soapexample.com\">" +
					"<ns2:fileName>jre-8u91-linux-i586.tar.gz</ns2:fileName>" +
				"</ns2:getFileRequest>");

		mockClient.sendRequest(withPayload(request))
				.andExpect(new SoapFaultResponseMatcher("{" + ProjectConstants.NAMESPACE_URI + "}SERVER_SPECIFIC", "Size of file is to big"));
	}

	@Test
	public void getFileByExistingNameSuccessTest() throws IOException {
		Source request = new StringSource("<ns2:getFileRequest xmlns:ns2=\"http://generated.soapexample.com\">" +
				"<ns2:fileName>file1.txt</ns2:fileName>" +
				"</ns2:getFileRequest>");

		mockClient.sendRequest(withPayload(request))
				.andExpect(noFault())
				//.andExpect(validPayload(xsdSchema))
				.andExpect((request1, response) -> {
					SaajSoapMessage message = (SaajSoapMessage) response;

					/*GetFileResponse responseFile = (GetFileResponse) response;

					File file = new File("testFile.txt");
					BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
					BufferedReader reader = new BufferedReader(new InputStreamReader(responseFile.getFile().getInputStream()));

					String line;
					while ((line = reader.readLine()) != null) {
						System.out.println(line);
					}
*/
					assert true;
				});
	}
}
