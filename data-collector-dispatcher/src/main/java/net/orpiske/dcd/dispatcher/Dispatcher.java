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
package net.orpiske.dcd.dispatcher;

import net.orpiske.dcd.collector.metadata.MetaData;

import java.util.Set;

/**
 * An abstraction for dispatching the metadata for further processing
 */
public interface Dispatcher {

    /**
     * Dispatches the metadata for processing
     * @param metaData the metadata to dispatch
     * @throws Exception for any issue within the dispatcher
     */
    void dispatch(final MetaData metaData) throws Exception;


    /**
     * Dispatches the metadata for processing
     * @param metaDataSet a set of metadata to dispatch
     * @throws Exception for any issue within the dispatcher
     */
    void dispatch(final Set<MetaData> metaDataSet) throws Exception;
}
