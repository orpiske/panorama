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
import org.apache.log4j.Logger;

import java.util.HashSet;
import java.util.Set;


/**
 * A string collector is a simple data collector that collects metadata out of text data
 */
public class StringCollector implements Collector {
    private static final Logger logger = Logger.getLogger(StringCollector.class);

    @Override
    public Set<MetaData> collect(final DataSet dataSet, final Vocabulary vocabulary) {
        logger.debug("Collecting data");
        Set<MetaData> metaDataSet = new HashSet<MetaData>();

        for (Word word : vocabulary.getWords()) {
            MetaData metaData = new MetaData(word);

            metaDataSet.add(metaData);
        }

        while (dataSet.hasNext()) {
           logger.trace("Processing next data in the data set");
           String textData = dataSet.next().dataToString();

           for (MetaData metaData : metaDataSet) {
               Word word = metaData.getWord();

               if (word.existsInText(textData)) {
                    metaData.addOccurrence();
               }
           }
        }

        return metaDataSet;
    }
}
