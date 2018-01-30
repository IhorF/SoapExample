package com.soapexample.exceptions;

import java.io.IOException;

/**
 * Created by Ivan.Malynovskyi on 30.01.2018  13:02.
 */
public class ExceedFileSizeException extends IOException {
	long size;

	public ExceedFileSizeException(long size) {
		super(String.format("Size of file(%d) is to big", size));
		this.size = size;
	}

	public long getSize() {
		return size;
	}
}
