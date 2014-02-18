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

/**
 * Represents a word
 */
public class Word {
    private String word;
    private Context context;

    /**
     * Constructor
     * @param word the string representation of the word
     * @param context the context where this word is valid
     */
    public Word(String word, Context context) {
        this.word = word;
        this.context = context;
    }

    /**
     * Gets the string representation of the word
     * @return a string object that represents the word
     */
    public String getWord() {
        return word;
    }


    /**
     * Gets the context where this word is valid/acceptable
     * @return the context where this word is valid/acceptable
     */
    public Context getContext() {
        return context;
    }
}
