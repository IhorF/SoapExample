package com.soapexample.somelogic;

import java.io.InputStream;
import java.util.regex.Pattern;

/**
 * Created by Ivan.Malynovskyi on 01.02.2018  11:17.
 *
 * Service should count return occurrence of word in text and in file
 *
 * @version 1.1.0
 * @since 1.1.0
 */
public interface WordService {

	/**
	 * Count a number of word in input stream
	 *
	 * @param word word which appearance should be counted
	 * @param inputStream where method will be looking for a word
	 * @return count of occurrence of word
	 */
	int getOccurrenceInStream(String word, InputStream inputStream);

	/**
	 * Count a number of word in input stream
	 *
	 * @param pattern pattern {@link Pattern} with word whi should be counted
	 * @param text where method will be looking for a word
	 * @return count of occurrence of word
	 */
	int getOccurrenceInText(Pattern pattern, String text);
}
