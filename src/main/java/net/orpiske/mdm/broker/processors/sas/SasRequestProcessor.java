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
package net.orpiske.mdm.broker.processors.sas;

import net.orpiske.mdm.broker.types.wrapper.LoadServiceWrapper;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.log4j.Logger;

public class SasRequestProcessor implements Processor {
    private static final Logger logger =
            Logger.getLogger(SasRequestProcessor.class);

    @Override
    public void process(Exchange exchange) throws Exception {
        Object object = exchange.getIn().getBody();

        if (!(object instanceof LoadServiceWrapper)) {
            logger.error("Invalid object type");
        }
        else {
            logger.error("Correct object type");
            LoadServiceWrapper wrapper = (LoadServiceWrapper) object;

            // wrapper.ge
        }

        exchange.getOut().setBody("Active MQ request");

    }
}
