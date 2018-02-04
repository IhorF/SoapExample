package com.soapexample.somelogic.impl;

import com.soapexample.somelogic.WordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Ivan.Malynovskyi on 01.02.2018  11:19.
 *
 * The implementation of {@link WordService}
 * @version 1.1.0
 * @since 1.1.0
 *
 */
@Service
public class WordServiceImpl implements WordService {
	private final Logger LOGGER = LoggerFactory.getLogger(WordService.class);

	/**
	 * @see WordService#getOccurrenceInStream(String, InputStream)
	 */
	@Override
	public int getOccurrenceInStream(String word, InputStream inputStream) {
		int count= 0;

		Pattern pattern = Pattern.compile(word, Pattern.CASE_INSENSITIVE);
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
			String line;
			while ((line = reader.readLine()) != null) {
				count += getOccurrenceInText(pattern, line);
			}
		} catch (IOException e) {
			LOGGER.warn("Cant't count word occurrence. {}", e.getMessage());
		}

		return count;
	}

	/**
	 * @see WordService#getOccurrenceInText(Pattern, String)
	 */
	@Override
	public int getOccurrenceInText(Pattern pattern, String text) {
		int count = 0;

		Matcher matcher = pattern.matcher(text);
		while (matcher.find()) {
			count++;
		}

		return count;
	}
}
