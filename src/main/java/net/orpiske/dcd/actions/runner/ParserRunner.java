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
package net.orpiske.dcd.actions.runner;

import net.orpiske.dcd.collector.Collector;
import net.orpiske.dcd.collector.dataset.DataSet;
import net.orpiske.dcd.collector.dataset.impl.MBoxDataSet;
import net.orpiske.dcd.collector.impl.StringCollector;
import net.orpiske.dcd.collector.metadata.MetaData;
import net.orpiske.dcd.collector.vocabulary.Vocabulary;
import net.orpiske.dcd.collector.vocabulary.Word;
import net.orpiske.dcd.collector.vocabulary.contexts.RegexContext;
import net.orpiske.dcd.collector.vocabulary.contexts.StringContext;
import net.orpiske.dcd.collector.vocabulary.impl.DefaultVocabulary;
import org.apache.log4j.Logger;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


public class ParserRunner {
    private static final Logger logger = Logger.getLogger(ParserRunner.class);

    private File file;

    public File getFile() {
        return file;
    }

    public void setFile(String fileName) {
        this.file = new File(fileName);
    }

    public void setFile(File file) {
        this.file = file;
    }

    public int run() {
        Collector collector = new StringCollector();
        DataSet dataSet;

        try {
            Vocabulary vocabulary = new DefaultVocabulary();

            Set<MetaData> metaDataSet;

            logger.debug("Created mbox data set");
            dataSet = new MBoxDataSet(file);

            logger.debug("Running data collector");
            metaDataSet = collector.collect(dataSet, vocabulary);

            logger.debug("Printing results");
            for (MetaData metaData : metaDataSet) {
                System.out.println(metaData.toString());
            }
        }
        catch (Exception e) {
            e.printStackTrace();

            return -1;
        }

        return 0;
    }

}
