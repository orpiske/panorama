package net.orpiske.sfs.filter.simple;

import com.google.common.base.CharMatcher;
import net.orpiske.sfs.filter.Filter;

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
public class AlphaNumericFilter implements Filter {
	@Override
	public String filter(String source) {
		CharMatcher legalChars = CharMatcher.inRange('a', 'z')
				.or(CharMatcher.inRange('A', 'Z'))
				.or(CharMatcher.inRange('0', '9'));

		return legalChars.retainFrom(source.subSequence(0, source.length()));
	}
}
