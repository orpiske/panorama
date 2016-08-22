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

package net.orpiske.sfs.filter.dictionary.spell;

import net.orpiske.sfs.filter.dictionary.Dictionary;
import net.orpiske.sfs.filter.dictionary.DictionaryEntry;
import net.orpiske.sfs.filter.dictionary.exception.DictionaryReadException;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.LinkedHashSet;

public class DefaultDictionary implements Dictionary {
	private static final Logger logger = Logger.getLogger(DefaultDictionary.class);

	private static final LinkedHashSet<DictionaryEntry> hashSet =
			new LinkedHashSet<DictionaryEntry>(30000);

	public DefaultDictionary() {
		InputStream stream = getClass().getResourceAsStream("/dictionaries/pt/port-big.dic");

		Iterator<String> i = null;

		try {
			i = IOUtils.lineIterator(stream, Charset.defaultCharset());

			while (i.hasNext()) {
				String line = i.next();

				if (line.startsWith("#")) {
					continue;
				}

				DictionaryEntry entry = DictionaryEntry.fromString(line);
				if (entry == null) {
					continue;
				}

				if (entry.getCategory() == DictionaryEntry.Category.OTHER) {
					continue;
				}

				if (logger.isTraceEnabled()) {
					logger.trace("Adding entry " + entry.getWord() + " to the cache");
				}

				hashSet.add(entry);
			}
		}
		catch (IOException e) {
			/*
			 * We all know this is not really true, but this should never
			 * actually happen. If it does, we're screwed o.O
			 */
			logger.error("Unhandled I/O exception: " + e.getMessage(), e);

			throw new DictionaryReadException(e);
		}
		finally {
			IOUtils.closeQuietly(stream);
		}
	}


	public DictionaryEntry lookup(final String word) {
		DictionaryEntry entry = DictionaryEntry.newAdjective(word);

		if (hashSet.contains(entry)) {
			return entry;
		}

		return null;
	}

}
