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

package net.orpiske.sfs.filter.runner;

import net.orpiske.sfs.filter.Filter;

import java.util.List;

public class DefaultFilterRunner implements FilterRunner {

	private List<Filter> filterList;


	/**
	 * Constructs a new filter runner and sets the list of filters
	 * @param filterList the list of filters
	 */
	public DefaultFilterRunner(List<Filter> filterList) {
		this.filterList = filterList;
	}

	@Override
	public String run(String source) {
		String ret = source;

		for (Filter filter : filterList) {
			ret = filter.filter(ret);
			if (ret.isEmpty()) {
				return ret;
			}
		}

		return ret;
	}
}
