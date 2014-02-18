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
package net.orpiske.dcd.collector.impl;

import net.orpiske.dcd.collector.Collector;
import net.orpiske.dcd.collector.dataset.DataSet;
import net.orpiske.dcd.collector.metadata.MetaData;
import net.orpiske.dcd.collector.vocabulary.Vocabulary;
import net.orpiske.dcd.collector.vocabulary.Word;

import java.util.HashSet;
import java.util.Set;


public class StringCollector implements Collector {

    @Override
    public Set<MetaData> collect(final DataSet dataSet, final Vocabulary vocabulary) {
        Set<MetaData> metaDataSet = new HashSet<MetaData>();

        for (Word word : vocabulary.getWords()) {
            MetaData metaData = new MetaData();

            metaDataSet.add(metaData);
        }

       while (dataSet.hasNext()) {
           String textData = dataSet.next().toString();

           for (MetaData metaData : metaDataSet) {
               Word word = metaData.getWord();

               if (word.existsInText(textData)) {
                   metaData.setWord(word);
                   metaData.addOccurrence();
               }
           }
       }


        return metaDataSet;
    }
}
