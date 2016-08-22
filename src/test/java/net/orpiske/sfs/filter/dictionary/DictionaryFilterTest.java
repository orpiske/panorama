/**
 Copyright 2014 Otavio Rodolfo Piske

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
 */

package net.orpiske.sfs.filter.dictionary;

import junit.framework.Assert;
import net.orpiske.sfs.filter.Filter;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class DictionaryFilterTest {

	private Filter filter;

	@Before
	public void setup() throws IOException {
		filter = new DictionaryFilter();
	}

	/**
	 * Tests whether the dictionary filter can filter a name. The input string
	 * "Carlos" is a common brazilian name
	 */
	@Test
	public void testName() {
		//
		String ret = filter.filter("Carlos");

		Assert.assertEquals("The dictionary did not filter a name", "", ret);
	}


	/**
	 * Tests whether the dictionary filter won't filter a substantive. The input
	 * string "bonita" means beautiful.
	 */
	@Test
	public void testSubstantive() {
		String ret = filter.filter("casa");

		Assert.assertEquals("The dictionary did not filter a substantive", "", ret);
	}


	/**
	 * Tests whether the dictionary filter can filter a substantive. The input
	 * string "bonita" means house.
	 */
	@Test
	public void testAdjective() {
		String ret = filter.filter("ruim");

		Assert.assertEquals("The dictionary filtered an adjective", "ruim", ret);
	}
}
