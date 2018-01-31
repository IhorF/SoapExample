package com.soapexample.somelogic;

import com.soapexample.exceptions.ExceedFileSizeException;

import java.io.File;
import java.util.List;

/**
 * Created by Ivan.Malynovskyi on 30.01.2018  12:15.
 */
public interface FileService {

	List<String> getFileNamesList();

	File getFile(String fileName) throws ExceedFileSizeException;
}
