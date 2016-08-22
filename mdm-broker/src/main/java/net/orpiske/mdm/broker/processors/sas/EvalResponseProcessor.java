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

import net.orpiske.exchange.sas.eval.v1.ResponseType;
import net.orpiske.sas.commons.xml.XmlParserUtils;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.log4j.Logger;

import javax.xml.bind.JAXBException;
import java.io.StringReader;
import java.util.List;

public class EvalResponseProcessor implements Processor {
    private static final Logger logger =
            Logger.getLogger(EvalResponseProcessor.class);

    private int getIndividualScore(final Exchange exchange) {
        String text = exchange.getIn().getBody(String.class);
        StringReader reader = new StringReader(text);

        try {
            if (logger.isTraceEnabled()) {
                logger.trace("Processing SAS response: " + text);
            }

            ResponseType responseType =
                XmlParserUtils.unmarshal(ResponseType.class, reader);

            return responseType.getEvalResponse().getScore();
        }
        catch (JAXBException e) {
            // TODO: needs better handling
            e.printStackTrace();
            try {
                Thread.currentThread().wait(2000);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
        }

        return 0;
    }


    @Override
    public void process(Exchange exchange) throws Exception {
        List<Exchange> grouped = exchange.getProperty(Exchange.GROUPED_EXCHANGE, List.class);
        int total = 0;


        for (Exchange individualExchange : grouped) {
            total += getIndividualScore(individualExchange);
        }

        float average = total / grouped.size();
        Float ret = Float.valueOf(average);

        String cspName = exchange.getProperty("CSP.NAME", String.class);
        logger.info("Processed " + grouped.size() + " exchanges for " +
                cspName + " with a final score of " + total);


        exchange.getOut().setBody(ret);
    }
}
