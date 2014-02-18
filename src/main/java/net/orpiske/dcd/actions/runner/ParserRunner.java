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
import net.orpiske.dcd.collector.vocabulary.contexts.StringContext;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ParserRunner {
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
        Vocabulary vocabulary = new Vocabulary() {
            private List<Word> words = new ArrayList<Word>();

            @Override
            public void addWord(Word word) {}

            @Override
            public List<Word> getWords() {
                Word word = new Word("Virtua");
                word.addContext(new StringContext());

                words.add(word);
                return words;
            }
        };
        Set<MetaData> metaDataSet;

        try {
            dataSet = new MBoxDataSet(file);

            metaDataSet = collector.collect(dataSet, vocabulary);

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
