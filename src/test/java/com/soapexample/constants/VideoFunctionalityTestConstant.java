package com.soapexample.constants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ivan.Malynovskyi on 01.02.2018  12:35.
 */
public abstract class VideoFunctionalityTestConstant {
	public final static String FILE_WHICH_BIGGER_THAN_LIMIT = "Metalurg - day before sale.ea";

	public static List<String> expectedListOfNames = new ArrayList<>();
	static {
		expectedListOfNames.add("file1.txt");
		expectedListOfNames.add("file3.txt");
		expectedListOfNames.add(FILE_WHICH_BIGGER_THAN_LIMIT);
	}

}
