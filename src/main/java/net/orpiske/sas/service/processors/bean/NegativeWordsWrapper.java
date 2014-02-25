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
 * Wrapper to calculate the negativity index
 */
public class NegativeWordsWrapper extends WordsWrapper {
    @Override
    protected Set<String> loadWords() {
        Set<String> stringSet = new HashSet<String>();

        stringSet.add("ruim");
        stringSet.add("instavel");
        stringSet.add("lento");
        stringSet.add("lenta");
        stringSet.add("problematica");
        stringSet.add("fora");
        stringSet.add("down");
        stringSet.add("horrivel");
        stringSet.add("pessimo");
        stringSet.add("lixo");
        stringSet.add("problema");
        stringSet.add("problemas");


        return stringSet;
    }

    @Override
    protected int getScore(final String phrase) {
        int count = super.countWords(phrase);

        return count * -1;
    }
}
