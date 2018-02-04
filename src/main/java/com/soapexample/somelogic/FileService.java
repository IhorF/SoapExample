package com.soapexample.somelogic;

import com.soapexample.exceptions.ExceedFileSizeException;

import java.io.File;
import java.util.List;

/**
 * Created by Ivan.Malynovskyi on 30.01.2018  12:15.
 *
 * Interface with methods which work with files.
 * @version 1.1.0
 * @since 1.1.0
 *
 */
public interface FileService {

	/**
	 * Method should return a list of file names
	 * from resources/file folder
	 *
	 * @return list of names of files
	 *
	 */
	List<String> getFileNamesList();

	/**
	 * Method returns a file be name.
	 * If file does not exists will be return null value
	 * If chosen file is bigger than limit {@link com.soapexample.ProjectConstants#FILE_SIZE_LIMIT}
	 * exception will be thrown.
	 *
	 * @throws com.soapexample.exceptions.ExceedFileSizeException file is too big
	 * @return list of names of files
	 *
	 */
	File getFile(String fileName) throws ExceedFileSizeException;
}
