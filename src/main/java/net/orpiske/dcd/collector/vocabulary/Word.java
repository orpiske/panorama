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
package net.orpiske.dcd.collector.vocabulary;

import org.apache.log4j.Logger;

import java.util.HashSet;
import java.util.Set;

/**
 * Represents a word
 */
public class Word {
    private static final Logger logger = Logger.getLogger(Word.class);

    private String word;
    private Set<Context> contexts = new HashSet<Context>();

    /**
     * Constructor
     * @param word the string representation of the word

     */
    public Word(String word) {
        this.word = word;
    }

    /*
     * Adds a valid context to the word
     * @param context the context where this word is valid
     */
    public void addContext(final Context context) {
        contexts.add(context);
    }

    /**
     * Gets the string representation of the word
     * @return a string object that represents the word
     */
    public String getWord() {
        return word;
    }


    /**
     * Checks whether the word exists in a given tag and is valid within
     * any of the given contexts
     * @param text the tag to check against
     * @return true if exists and is valid or false otherwise
     */
    public boolean existsInText(final String text) {

        for (Context context : contexts) {
            if (context.isValid(getWord(), text)) {

                if (logger.isTraceEnabled()) {
                    logger.trace("The word '" + word + "' was considered valid "
                        + "within the " + context.getName() + " context");
                }

                return true;
            }
        }

        return false;
    }

    @Override
    public String toString() {
        return "Word{" +
                "word='" + word + '\'' +
                ", contexts=" + contexts +
                '}';
    }
}
