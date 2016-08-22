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
package net.orpiske.dcd.collector.metadata;

import net.orpiske.dcd.collector.vocabulary.Word;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple metadata representation
 */
public class MetaData {
    private Word word;
    private List<Occurrence> occurrenceList = new
            ArrayList<Occurrence>();

    /**
     * Constructor
     * @param word the word tied to the metadata
     */
    public MetaData(final Word word) {
        this.word = word;
    }


    /**
     * Gets the word
     * @return the word object
     */
    public Word getWord() {
        return word;
    }

    /**
     * Sets the word object
     * @param word the word object
     */
    public void setWord(Word word) {
        this.word = word;
    }


    /**
     * Gets the number of occurrences of 'word' in the processed data set
     * @return the number of occurrences
     */
    public int getOccurrenceCount() {
        return occurrenceList.size();
    }


    /**
     * Adds an occurrence of 'word'
     */
    public void addOccurrence(final Occurrence occurrence) {
        occurrenceList.add(occurrence);
    }


    /**
     * Gets the list of occurrences for the word
     * @return a list object with all occurrences for this word
     */
    public List<Occurrence> getOccurrenceList() {
        return occurrenceList;
    }

    @Override
    public String toString() {
        return "MetaData{" +
                "word=" + word +
                ", occurrences=" + occurrenceList +
                '}';
    }
}
