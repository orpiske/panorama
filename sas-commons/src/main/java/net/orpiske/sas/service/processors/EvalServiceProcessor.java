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
package net.orpiske.sas.service.processors;

import net.orpiske.exchange.sas.eval.v1.RequestType;
import net.orpiske.exchange.sas.eval.v1.ResponseType;
import net.orpiske.sas.service.processors.bean.EvalServiceBean;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.log4j.Logger;
import org.apache.log4j.MDC;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import java.io.StringReader;
import java.io.StringWriter;

/**
 * A processor implementation. This is the class that it is
 * actually responsible for handling or transforming the incoming
 * messages.
 */
public class EvalServiceProcessor implements Processor {
    /**
     * This is the method that actually works with the messages.
     * @param exchange The in/out exchange messages
     * @throws Exception
     */
    @Override
    public void process(Exchange exchange) throws Exception {
        /**
         * This adds the exchange ID, when available, to the logs
         * which makes it easier to correlate request, processing
         * and response.
         */
        String id = exchange.getExchangeId();
        MDC.put("id", id);

        RequestType requestType = exchange.getIn().getBody(RequestType.class);

        EvalServiceBean bean = new EvalServiceBean();
        ResponseType responseType = bean.eval(requestType);

        exchange.getOut().setBody(responseType);
    }
}
