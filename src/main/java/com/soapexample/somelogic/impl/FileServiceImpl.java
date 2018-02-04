package com.soapexample.somelogic.impl;

import com.soapexample.ProjectConstants;
import com.soapexample.exceptions.ExceedFileSizeException;
import com.soapexample.somelogic.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ivan.Malynovskyi on 30.01.2018  12:17.
 *
 * Implementation of {@link FileService}
 *
 * @version 1.1.0
 * @since 1.1.0
 *
 */
@Component
public class FileServiceImpl implements FileService {

	private final Logger LOGGER = LoggerFactory.getLogger(FileServiceImpl.class);

    /**
     * @see FileService#getFileNamesList()
     */
	@Override
	public List<String> getFileNamesList() {
		List<String> fileNamesList = new ArrayList<>();
		File directory;

		try {
			directory = getFileFromResourceByPath("");

			for (File file : getFiles(directory)) {
				fileNamesList.add(file.getName());
			}
		} catch (IOException e) {
			LOGGER.error("Error occurred: {}", e.getMessage());
		}

		return fileNamesList;
	}

    /**
     * @see FileService#getFile(String)
     */
	@Override
	public File getFile(String fileName) throws ExceedFileSizeException {
		File file = null;
		try {
			file = getFileFromResourceByPath(fileName);
		} catch (IOException e) {
			LOGGER.warn("File {} not found.", fileName);
		}

		long fileSize = file != null ? file.length() : 0;
		if (fileSize > ProjectConstants.FILE_SIZE_LIMIT) {
			throw new ExceedFileSizeException(fileSize);
		}

		return file;
	}

	/**
	 * Return an array of files from directory.
	 *
	 * @param directory directory from which method will return an array
	 * @return array of files
	 * @throws FileNotFoundException param directory is not actually a directory
	 * 								 of directory is empty
	 *
	 */
	private File[] getFiles(File directory) throws FileNotFoundException {
		if (!directory.isDirectory()) {
			throw new FileNotFoundException("Directory was not found!");
		}

		File[] files = directory.listFiles();
		if (files == null) {
			String message = "Folder is empty";

			LOGGER.info(message);
			throw new FileNotFoundException(message);
		}

		return files;
	}

	/**
	 *
	 * Return file by name from resources folder
	 *
	 * @param name name of file from resource folder
	 * @return file
	 * @throws IOException thrown when are not able to get file from resources
	 */
	private File getFileFromResourceByPath(String name) throws IOException {
		return new ClassPathResource(ProjectConstants.DIRECTORY_WITH_FILES +
				(name.isEmpty() ? "" : (File.separator + name))).getFile();
	}
}
