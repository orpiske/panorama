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
package net.orpiske.mdm.broker.processors;

import net.orpiske.mdm.broker.types.wrapper.LoadServiceWrapper;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.cxf.message.MessageContentsList;
import org.apache.log4j.Logger;

import javax.xml.ws.Holder;

public class InternalProcessor implements Processor {
    private static final Logger logger = Logger.getLogger(InternalProcessor.class);

    @Override
    public void process(Exchange exchange) throws Exception {
        logger.debug("Processing a load service message");
        Object object = exchange.getIn().getBody();

        if (object instanceof MessageContentsList) {
            MessageContentsList messageContentsList = (MessageContentsList) object;


            LoadServiceWrapper wrapper = new LoadServiceWrapper();

            logger.debug("Wrapping message for further processing");
            wrapper.set(messageContentsList.get(0));
            wrapper.set(messageContentsList.get(1));
            wrapper.set(messageContentsList.get(2));
            wrapper.set(messageContentsList.get(3));

            logger.debug("Setting up reply message");
            exchange.getOut().setBody(wrapper);

            /**
             * We enrich the exchange header with additional data because some
             * of the messages we will exchange won't contain that information.
             * This will allow us to easily route and aggregate the messages in
             * some scenarios.
             */
            logger.debug("Enriching the message");
            exchange.getOut().setHeader("CSP.NAME",
                    wrapper.getCspType().getName());
            exchange.getOut().setHeader("CSP.REFERENCE_COUNT",
                    wrapper.getCspType().getOccurrences());
        }
        else {
            /*
             * A CXF in POJO mode should give us a MessageContentsList object.
             * If that's not the case, then it's an unhandled error.
             *
             * Obs.: this is not a production-ready code since it should return
             * _some_ error and does not.
             */
            logger.error("Unhandled message type: " + (object == null ? "null"
                    : object.getClass()));
        }
    }
}
