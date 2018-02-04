package com.soapexample.service;

import com.soapexample.exceptions.ExceedFileSizeException;
import com.soapexample.somelogic.FileService;
import com.soapexample.somelogic.impl.FileServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.soapexample.constants.VideoFunctionalityTestConstant.expectedListOfNames;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Created by Ivan.Malynovskyi on 31.01.2018  16:34.
 *
 * Test functionality of {@link FileService}
 */
@RunWith(SpringRunner.class)
public class FileServiceTest {

	private FileService fileService = new FileServiceImpl();

	@Test
	public void getFileNamesTest() {
		List<String> resultList = fileService.getFileNamesList();

		assertEquals(expectedListOfNames, resultList);
	}

	@Test(expected = com.soapexample.exceptions.ExceedFileSizeException.class)
	public void getFileByValidNameTest() throws ExceedFileSizeException {
		fileService.getFile("jre-8u91-linux-i586.tar.gz");
	}

	@Test(expected = NullPointerException.class)
	public void getFileByUnExistingNameTest() {
		File file = null;

		try {
			file = fileService.getFile("jre-8u91-linux-i586.tar.gzz");
		} catch (ExceedFileSizeException e) {
			assert false;
		}

		assertNull(file);
	}

	@Test
	public void getFileByNameTest() {
		String fileName = expectedListOfNames.get(0);

		File expectedFile = new File("src/main/resources/" + fileName);
		File receivedFile = null;
		try {
			receivedFile = fileService.getFile(fileName);
		} catch (ExceedFileSizeException e) {
			assert false;
		}

		assertNotNull(receivedFile);
		assertEquals(expectedFile.getName(), receivedFile.getName());
		/*
		Need to find if this is necessary

		try {
			assertTrue(FileUtils.contentEquals(expectedFile, receivedFile));
		} catch (IOException e) {
			assert false;
		}*/
	}
}
