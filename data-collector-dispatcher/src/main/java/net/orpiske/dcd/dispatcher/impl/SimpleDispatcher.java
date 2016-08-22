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
package net.orpiske.dcd.dispatcher.impl;

import net.orpiske.dcd.collector.metadata.MetaData;
import net.orpiske.dcd.dispatcher.Dispatcher;
import org.apache.log4j.Logger;

import java.util.Set;

public class SimpleDispatcher implements Dispatcher {
    private static final Logger logger = Logger.getLogger(SimpleDispatcher.class);

    @Override
    public void dispatch(MetaData metaData) throws Exception {
        System.out.println(metaData.toString());
    }


    @Override
    public void dispatch(Set<MetaData> metaDataSet) throws Exception {
        logger.debug("Printing results");
        for (MetaData metaData : metaDataSet) {
            dispatch(metaData);
        }
    }
}
