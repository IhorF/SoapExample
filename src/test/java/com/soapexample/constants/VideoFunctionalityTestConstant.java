package com.soapexample.constants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ivan.Malynovskyi on 01.02.2018  12:35.
 */
public abstract class VideoFunctionalityTestConstant {
	public static List<String> expectedListOfNames = new ArrayList<>();
	static {
		expectedListOfNames.add("file1.txt");
		expectedListOfNames.add("file2.txt");
		expectedListOfNames.add("file3");
		expectedListOfNames.add("jre-8u91-linux-i586.tar.gz");
	}

}
