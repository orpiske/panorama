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

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.cxf.message.MessageContentsList;
import org.apache.log4j.Logger;
import org.apache.log4j.MDC;

import javax.xml.ws.Holder;

/**
 * A processor for the load service
 */
public class LoadServiceProcessor implements Processor {
    private static final Logger logger = Logger.getLogger(LoadServiceProcessor.class);
    private static final int SUCCESS = 0;
    private static final String SUCCESS_MSG = "Success";

    @Override
    public void process(Exchange exchange) throws Exception {
        logger.info("Processing a load service message");
        Object object = exchange.getIn().getBody();


        /**
         * This adds the exchange ID, when available, to the logs
         * which makes it easier to correlate request, processing
         * and response.
         */
        String id = exchange.getExchangeId();
        if (id != null) {
            MDC.put("id", id);
        }
        
        if (object instanceof MessageContentsList) {
            MessageContentsList messageContentsList = (MessageContentsList) object;

            /*
             * We don't really do any processing here. We just want to
             * acknowledge that we received the message
             */

            Holder<Integer> code = (Holder<Integer>) messageContentsList.get(4);
            Holder<String> message = (Holder<String>) messageContentsList.get(5);

            code.value = SUCCESS;
            message.value = SUCCESS_MSG;

            logger.debug("Setting up reply message");
            exchange.getOut().setBody(new Object[] { code, message });
        }
        else {
            /*
             * A CXF in POJO modo should give us a MessageContentsList object.
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
