package com.soapexample.exceptions;

import org.springframework.ws.soap.server.endpoint.annotation.FaultCode;
import org.springframework.ws.soap.server.endpoint.annotation.SoapFault;

import java.io.IOException;

/**
 * Created by Ivan.Malynovskyi on 30.01.2018  13:02.
 *
 * Custom exception which will be thrown when requested
 * files is bigger than ProjectConstants.FILE_SIZE_LIMIT
 *
 */
@SoapFault(faultCode = FaultCode.CUSTOM, customFaultCode = "{http://generated.soapexample.com}SERVER_SPECIFIC")
public class ExceedFileSizeException extends IOException {
	private long size;

	public ExceedFileSizeException(long size) {
		super("Size of file is to big");
		this.size = size;
	}

	public long getSize() {
		return size;
	}
}
