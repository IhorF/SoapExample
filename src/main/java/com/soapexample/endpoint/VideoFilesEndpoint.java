package com.soapexample.endpoint;

import com.soapexample.exceptions.ExceedFileSizeException;
import com.soapexample.generated.GetFileNamesRequest;
import com.soapexample.generated.GetFileNamesResponse;
import com.soapexample.generated.GetFileRequest;
import com.soapexample.generated.GetFileResponse;
import com.soapexample.somelogic.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.context.MessageContext;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import org.springframework.ws.soap.saaj.SaajSoapMessage;
import org.springframework.ws.soap.saaj.SaajSoapMessageFactory;

import javax.activation.DataHandler;

import java.io.File;
import java.net.MalformedURLException;

import static com.soapexample.ProjectConstants.DIRECTORY_WITH_FILES;
import static com.soapexample.ProjectConstants.NAMESPACE_URI;

/**
 * Created by Ivan.Malynovskyi on 30.01.2018  15:13.
 *
 * It is an endpoint class, which has two web-services (SOAP)
 * Class responsible for to action:
 * 1) return list of available files
 * 2) return file by name
 *
 * @version 1.1.0
 * @since 1.0.2
 */
@Endpoint
public class VideoFilesEndpoint {

	private final Logger LOGGER = LoggerFactory.getLogger(VideoFilesEndpoint.class);

	@Autowired
	private FileService fileService;

	@Autowired
	private SaajSoapMessageFactory messageFactory;

	/**
	 * Web service which return list of file names from source folder.
	 * For this {@link FileService} is used
	 *
	 * @param request object of simple class without any field
	 * @return response of type {@link GetFileNamesResponse} with list of names
	 */
	@PayloadRoot(localPart = "getFileNamesRequest", namespace = NAMESPACE_URI)
	@ResponsePayload
	public GetFileNamesResponse getFileNames(@RequestPayload GetFileNamesRequest request) {
		GetFileNamesResponse response = new GetFileNamesResponse();

		fileService.getFileNamesList().forEach(response.getFileNamesList()::add);
		if (response.getFileNamesList().isEmpty()) {
			LOGGER.warn("{} directory does not contain any files.", DIRECTORY_WITH_FILES);
		}

		return response;
	}


	/**
	 * Web service which return list of file names from source folder.
	 * For this {@link FileService} is used
	 *
	 * @param fileRequest object wilt file name
	 * @throws ExceedFileSizeException thrown when file is too big. @see {@link FileService#getFile(String)}
	 *
	 * @return response of type {@link GetFileResponse} with attached file
	 */
	@PayloadRoot(localPart = "getFileRequest", namespace = NAMESPACE_URI)
	@ResponsePayload
	public GetFileResponse getFile(@RequestPayload GetFileRequest fileRequest, MessageContext context)
			throws ExceedFileSizeException {

		String fileName = fileRequest.getFileName();
		DataHandler dataHandler;
		File requestedFile = fileService.getFile(fileName);
		try {
			dataHandler = new DataHandler(requestedFile.toURI().toURL());

			SaajSoapMessage message = messageFactory.createWebServiceMessage();
			message.addAttachment("file", dataHandler);
			context.setResponse(message);
		} catch (MalformedURLException e) {
			LOGGER.warn("Can't get DataHandler object from {} file. ", fileName);
		}

		return new GetFileResponse();
	}
}
