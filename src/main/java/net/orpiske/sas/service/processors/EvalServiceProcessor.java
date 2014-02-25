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
import net.orpiske.sas.commons.xml.XmlParserUtils;
import net.orpiske.sas.commons.xml.XmlWriterUtils;
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
    private static final Logger logger =
            Logger.getLogger(EvalServiceProcessor.class);

    /**
     * Parses the request message
     * @param message the request message as a String
     * @return
     * @throws JAXBException
     */
    private RequestType parseMessage(final String message) throws JAXBException {
        StringReader reader = new StringReader(message);

        return XmlParserUtils.unmarshal(RequestType.class, reader);
    }


    /**
     * Serializes the response message
     * @param responseType
     * @return
     */
    private String serializeResponse(ResponseType responseType) {
        StringWriter writer = new StringWriter();

        String ns = "http://www.orpiske.net/exchange/sas/eval/v1";
        JAXBElement<ResponseType> element = XmlWriterUtils.newJAXBElement(ns,
                "response", ResponseType.class, responseType);

        try {
            XmlWriterUtils.marshal(element, responseType, writer);
        } catch (JAXBException e) {
            logger.error("Unable to serialize response message: " +
                    e.getMessage(), e);
        }

        return writer.toString();
    }


    /**
     * This is the method that actually works with the messages.
     * @param exchange The in/out exchange messages
     * @throws Exception
     */
    @Override
    public void process(Exchange exchange) throws Exception {
        /*
         * Access to the message is usually done by obtaining the message
         * body as a String object, which can be parsed.
         */
        String message = exchange.getIn().getBody().toString();
        logger.info("Message received: " + message);


        /**
         * This adds the exchange ID, when available, to the logs
         * which makes it easier to correlate request, processing
         * and response.
         */
        String id = exchange.getExchangeId();
        MDC.put("id", id);

        EvalServiceBean bean = new EvalServiceBean();
        ResponseType responseType;
        try {
            /*
             * Either handles the request ...
             */
            RequestType requestType = parseMessage(message);

            responseType = bean.eval(requestType);
        }
        catch (JAXBException e) {
            /**
             * ... or any error that might occur
             */
            logger.error("Unable to process the request: " + e.getMessage(), e);

            responseType = bean.createError();
        }

        /**
         * In any case, try to always send a message - unless it fails to
         * serialize it.
         */
        String response = serializeResponse(responseType);
        exchange.getOut().setBody(response, String.class);
    }
}
