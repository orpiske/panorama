/**
 * Copyright 2014 Otavio Rodolfo Piske
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.orpiske.sfs.filter;

import net.orpiske.sfs.filter.simple.CharacterFilter;
import org.junit.Assert;
import org.junit.Test;


public class CharacterFilterTest {
	private static Filter filter = new CharacterFilter();

	private static final String UNWANTED_CHARS = "!@#$^&*()_+-=\\][{}|;\":?/>.<,";
	private static final String SPECIAL_CHARS = "œ∑´†¥¨ˆπåß∂ƒ©˙∆˚¬Ω≈ç√∫˜≤≥ç…æ“‘«¡™£¢∞§¶•ªº–≠";

	private static final String WORD = "Word";
	private static final String NUMBER = "780789123";

	@Test
	public void testWord() {
		String ret = filter.filter(WORD);

		Assert.assertEquals("The string was not filtered correctly", WORD, ret);
	}

	@Test
	public void testWordWithUnwantedChars() {
		String ret = filter.filter(WORD + UNWANTED_CHARS);

		Assert.assertEquals("The string was not filtered correctly", WORD, ret);
	}

	@Test
	public void testWordInTheMiddleWithUnwantedChars() {
		String ret = filter.filter(UNWANTED_CHARS + WORD + UNWANTED_CHARS);

		Assert.assertEquals("The string was not filtered correctly", WORD, ret);
	}


	@Test
	public void testWordInTheEndWithUnwantedChars() {
		String ret = filter.filter(UNWANTED_CHARS + WORD);

		Assert.assertEquals("The string was not filtered correctly", WORD, ret);
	}

	@Test
	public void testWordSpecialChars() {
		String ret = filter.filter(WORD + SPECIAL_CHARS);

		Assert.assertEquals("The string was not filtered correctly", WORD, ret);
	}

	@Test
	public void testWordInTheMiddleWithSpecialChars() {
		String ret = filter.filter(SPECIAL_CHARS + WORD + SPECIAL_CHARS);

		Assert.assertEquals("The string was not filtered correctly", WORD, ret);
	}


	@Test
	public void testWordInTheEndWithSpecialChars() {
		String ret = filter.filter(SPECIAL_CHARS + WORD);

		Assert.assertEquals("The string was not filtered correctly", WORD, ret);
	}

	@Test
	public void testNumber() {
		String ret = filter.filter(NUMBER);

		Assert.assertEquals("The string was not filtered correctly", "", ret);
	}

	@Test
	public void testNumberWithUnwantedChars() {
		String ret = filter.filter(NUMBER + UNWANTED_CHARS);

		Assert.assertEquals("The string was not filtered correctly", "", ret);
	}

	@Test
	public void testNumberInTheMiddleWithUnwantedChars() {
		String ret = filter.filter(UNWANTED_CHARS + NUMBER + UNWANTED_CHARS);

		Assert.assertEquals("The string was not filtered correctly", "", ret);
	}


	@Test
	public void testNumberSpecialChars() {
		String ret = filter.filter(NUMBER + SPECIAL_CHARS);

		Assert.assertEquals("The string was not filtered correctly", "", ret);
	}

	@Test
	public void testNumberInTheMiddleWithSpecialChars() {
		String ret = filter.filter(SPECIAL_CHARS + NUMBER + SPECIAL_CHARS);

		Assert.assertEquals("The string was not filtered correctly", "", ret);
	}


	@Test
	public void testNumberInTheEndWithSpecialChars() {
		String ret = filter.filter(SPECIAL_CHARS + NUMBER);

		Assert.assertEquals("The string was not filtered correctly", "", ret);
	}


	@Test
	public void testUnwantedChars() {
		String ret = filter.filter(UNWANTED_CHARS);

		Assert.assertEquals("The string was not filtered correctly", "", ret);
	}

	@Test
	public void testSpecialChars() {
		String ret = filter.filter(SPECIAL_CHARS);

		Assert.assertEquals("The string was not filtered correctly", "", ret);
	}
}
