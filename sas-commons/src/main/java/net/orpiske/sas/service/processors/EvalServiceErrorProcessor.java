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

import net.orpiske.exchange.sas.eval.v1.ResponseType;
import net.orpiske.sas.service.processors.bean.EvalServiceBean;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.log4j.Logger;
import org.apache.log4j.MDC;

/**
 * This is a processor for service errors.
 */
public class EvalServiceErrorProcessor implements Processor {
    private static final Logger logger =
            Logger.getLogger(EvalServiceErrorProcessor.class);

    @Override
    public void process(Exchange exchange) throws Exception {
        String id = exchange.getExchangeId();
        MDC.put("id", id);

        Exception e = exchange.getException();
        if (e != null) {
            logger.error("Unable to process the request: " + e.getMessage(), e);
        }

        EvalServiceBean bean = new EvalServiceBean();
        ResponseType responseType = bean.createError();

        exchange.getOut().setBody(responseType);
    }
}
