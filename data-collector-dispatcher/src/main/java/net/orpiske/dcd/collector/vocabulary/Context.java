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
 * An abstract of a context where the occurrence of an word is valid
 * as part of a metadata collection
 */
public interface Context {

    /**
     * Gets the context name
     * @return the context name
     */
    String getName();

    /**
     * Checks whether 'word' is valid within textData - if existent - in the
     * implemented context
     * @param word the word to check
     * @param textData the data to check agains
     * @return true if the word if valid within textData in the implemented
     * context or false otherwise
     */
    boolean isValid(final String word, final String textData);
}
