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
package net.orpiske.dcd.collector.dataset;

import java.util.Date;

/**
 * An abstract representation of any data
 */
public interface Data {


    /**
     * Gets the originator of the data or reference
     * @return the originator of the data or reference
     */
    String getOriginator();

    /**
     * Gets the header of the data or reference
     * @return the header of the data or reference
     */
    String getHeader();

    /**
     * Gets the body of the data or reference
     * @return the body of the data or reference
     */
    String getBody();

    /**
     * Converts the data to String in a processable format (do not confuse with
     * toString())
     * @return the data as a processable string format
     */
    String dataToString();

    /**
     * Gets an associated date with the data
     * @return a Date object with data or null if none
     */
    Date getDate();
}
