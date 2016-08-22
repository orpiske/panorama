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
package net.orpiske.dcd.collector.vocabulary.impl;

import net.orpiske.dcd.collector.vocabulary.Vocabulary;
import net.orpiske.dcd.collector.vocabulary.Word;
import net.orpiske.dcd.collector.vocabulary.contexts.RegexContext;
import net.orpiske.dcd.utils.ConfigurationWrapper;
import net.orpiske.dcd.utils.Constants;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.log4j.Logger;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DefaultVocabulary implements Vocabulary {
    private static final Logger logger = Logger.getLogger(DefaultVocabulary.class);

    private PropertiesConfiguration config;
    private List<Word> words = new ArrayList<Word>();

    public DefaultVocabulary() throws FileNotFoundException, ConfigurationException {
        DictionaryConfigurationWrapper.initConfiguration(Constants.DCD_CONFIG_DIR,
                "vocabulary.properties");

        config = DictionaryConfigurationWrapper.getConfig();


        Iterator<String> it = config.getKeys();
        while (it.hasNext()) {
            String key = it.next();

            List<Object> list = config.getList(key);

            for (Object value : list) {
                logger.debug("Adding word " + key + " with context " + value);
                Word word = new Word(key);

                word.addContext(new RegexContext((String) value));
                addWord(word);
            }

        }
    }

    @Override
    public void addWord(Word word) {
        words.add(word);
    }

    @Override
    public List<Word> getWords() {
        return words;
    }
}
