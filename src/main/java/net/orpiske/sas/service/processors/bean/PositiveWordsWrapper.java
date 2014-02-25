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

import java.util.HashSet;
import java.util.Set;

/**
 * Wrapper to calculate the positivity index
 */
public class PositiveWordsWrapper extends WordsWrapper {
    @Override
    protected Set<String> loadWords() {
        Set<String> stringSet = new HashSet<String>();

        stringSet.add("boa");
        stringSet.add("estavel");
        stringSet.add("rapido");
        stringSet.add("rapida");
        stringSet.add("confiavel");
        stringSet.add("sem problemas");
        stringSet.add("excelente");
        stringSet.add("otima");
        stringSet.add("boa performance");
        stringSet.add("resolvido");

        return stringSet;
    }

    @Override
    protected int getScore(final String phrase) {
        int count = super.countWords(phrase);

        return count;
    }
}
