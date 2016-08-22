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
package net.orpiske.sas.service.processors.bean;

import java.util.Set;

/**
 * A wrapper for the negativity/positivity score calculation
 */
public abstract class WordsWrapper {

    private Set<String> wordSet;
    private int wordCount;


    public WordsWrapper() {
        if (wordSet == null) {
            wordSet = loadWords();
        }
    }

    protected abstract Set<String> loadWords();


    protected int countWords(final String phrase) {
        wordCount = 0;

        for (String word : wordSet) {
            if (phrase.contains(word)) {
                wordCount++;
            }
        }

        return wordCount;
    }


    protected abstract int getScore(final String phrase);
}
