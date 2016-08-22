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

import net.orpiske.sfs.filter.Filter;
import net.orpiske.sfs.filter.dictionary.spell.DefaultDictionary;
import org.apache.log4j.Logger;


import java.io.IOException;


public class DictionaryFilter implements Filter {
	private static final Logger logger = Logger.getLogger(DictionaryFilter.class);

	private static Dictionary dictionary;

	public DictionaryFilter() {
		synchronized (this) {
			if (dictionary == null) {
				dictionary = new DefaultDictionary();
			}
		}
	}

	@Override
	public String filter(String source) {
		DictionaryEntry entry = dictionary.lookup(source);

		if (entry == null) {
			if (logger.isTraceEnabled()) {
				logger.trace("Input word " + source + " was not found in the dictionary");
			}

			return "";
		}

		return source;
	}
}
