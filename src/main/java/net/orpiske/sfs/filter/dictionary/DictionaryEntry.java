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

import com.google.common.base.Splitter;

import java.util.Iterator;

public final class DictionaryEntry implements Comparable<DictionaryEntry> {

	/**
	 * There are many more ... I won't even list them all here ...
	 */
	public enum Category {
		ADJECTIVE,
		OTHER;

		public static Category fromString(final String string) {
			if (string == null) {
				return OTHER;
			}

			if (string.equals("#a")) {
				return ADJECTIVE;
			}

			if (string.equals("#af")) {
				return ADJECTIVE;
			}

			if (string.equals("#am")) {
				return ADJECTIVE;
			}

			if (string.equals("#an")) {
				return ADJECTIVE;
			}

			return OTHER;
		}
	};

	private String word;
	private Category category;

	private DictionaryEntry(final String word, final Category category) {
		this.word = word;
		this.category = category;
	}


	public static DictionaryEntry fromString(final String string) {
		if (string.trim().isEmpty()) {
			return null;
		}

		Iterable<String> iterable = Splitter.on("/").split(string);
		Iterator<String> it = iterable.iterator();

		String word = it.next();
		String categoryTag = it.next();

		Category cat = Category.fromString(categoryTag);

		return new DictionaryEntry(word, cat);
	}

	public static DictionaryEntry newAdjective(final String word) {
		return new DictionaryEntry(word, Category.ADJECTIVE);
	}

	public String getWord() {
		return word;
	}

	public Category getCategory() {
		return category;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		DictionaryEntry that = (DictionaryEntry) o;

		if (category != that.category) return false;
		if (word != null ? !word.equals(that.word) : that.word != null) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = word != null ? word.hashCode() : 0;
		result = 31 * result + (category != null ? category.hashCode() : 0);
		return result;
	}

	@Override
	public int compareTo(DictionaryEntry other) {
		String otherWord = other.getWord();

		return word.compareTo(otherWord);
	}
}
