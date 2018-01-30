package com.soapexample.somelogic;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by Ivan.Malynovskyi on 30.01.2018  12:15.
 */
public interface FileService {

	List<String> getFileNamesList();

	File getFile(String fileName)
			throws IOException;
}
