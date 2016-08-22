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
package net.orpiske.dcd.collector.vocabulary.contexts;

import net.orpiske.dcd.collector.vocabulary.Context;

/**
 * A string context that simply validates whether the word exists in a
 * text or not
 */
public class StringContext implements Context {

    @Override
    public String getName() {
        return "String occurrence";
    }

    @Override
    public boolean isValid(final String word, final String textData) {
        return textData.indexOf(word) > 0;
    }
}
