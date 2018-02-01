package com.soapexample.somelogic;

import java.io.File;
import java.io.InputStream;
import java.util.regex.Pattern;

/**
 * Created by Ivan.Malynovskyi on 01.02.2018  11:17.
 *
 * Service should count return occurrence of word in text and in file
 *
 */
public interface WordService {

	int getOccurrenceInStream(String word, InputStream inputStream);

	int getOccurrenceInText(Pattern pattern, String text);
}
