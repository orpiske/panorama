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

import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import java.io.StringReader;
import java.io.StringWriter;

public class EvalServiceProcessor implements Processor {
    private static final Logger logger =
            Logger.getLogger(EvalServiceProcessor.class);

    private RequestType parseMessage(final String message) throws JAXBException {
        StringReader reader = new StringReader(message);

        return XmlParserUtils.unmarshal(RequestType.class, reader);
    }


    private String serializeResponse(ResponseType responseType) {
        StringWriter writer = new StringWriter();

        String ns = "http://www.orpiske.net/exchange/sas/eval/v1/";
        JAXBElement<ResponseType> element = XmlWriterUtils.newJAXBElement(ns,
                "response", ResponseType.class, responseType);

        try {
            XmlWriterUtils.marshal(element, responseType, writer);
        } catch (JAXBException e) {
            // TODO: needs better handling
            e.printStackTrace();
        }

        return writer.toString();
    }

    @Override
    public void process(Exchange exchange) throws Exception {
        String message = exchange.getIn().getBody().toString();
        logger.info("Message received: " + message);

        try {
            RequestType requestType = parseMessage(message);

            EvalServiceBean bean = new EvalServiceBean();
            ResponseType responseType = bean.eval(requestType);

            String response = serializeResponse(responseType);
            exchange.getOut().setBody(response,
                    String.class);
        }
        catch (JAXBException e) {
            e.printStackTrace();

            // TODO: handle error
        }
    }
}
