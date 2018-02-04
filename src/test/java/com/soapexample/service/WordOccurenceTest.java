package com.soapexample.service;

import com.soapexample.somelogic.WordService;
import com.soapexample.somelogic.impl.WordServiceImpl;
import org.junit.BeforeClass;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.regex.Pattern;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Ivan.Malynovskyi on 31.01.2018  19:22.
 *
 * Test functionality of {@link WordService}
 */
@RunWith(Theories.class)
public class WordOccurenceTest {

	private WordService wordService = new WordServiceImpl();
	public static @DataPoints Pair[] values = new Pair[3];

	@BeforeClass
	public static void initialize() {
		values[0] = new Pair("text", 1);
		values[1] = new Pair("we", 2);
		values[2] = new Pair("example", 0);
	}

	@Theory
	public void getWordCountInText(Pair value) {
		String testText = "here we can find some text which we used to test our service";
		assertTrue(wordService.getOccurrenceInText(getPattern(value.getWord()), testText) == value.getExpectedCount());
	}

	@Theory
	public void getWordCountInStream(Pair value) throws FileNotFoundException {
		File file = new File("src/test/resources/haveText.txt");
		InputStream inputStream = new FileInputStream(file);

		assertEquals(wordService.getOccurrenceInStream(value.getWord(), inputStream), value.getExpectedCount());
	}

	public static Pattern getPattern(String word) {
		return Pattern.compile(word, Pattern.CASE_INSENSITIVE);
	}

	static class Pair {
		private String word;
		private int expectedCount;

		Pair(String word, int count) {
			this.word = word;
			this.expectedCount = count;
		}

		String getWord() {
			return word;
		}

		void setWord(String word) {
			this.word = word;
		}

		int getExpectedCount() {
			return expectedCount;
		}

		void setExpectedCount(int count) {
			this.expectedCount = count;
		}
	}
}
