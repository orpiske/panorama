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
package net.orpiske.mdm.broker.processors.tcs;

import net.orpiske.mdm.broker.utils.ConfigurationWrapper;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.restlet.data.MediaType;

public class TcsEndpointResolver implements Processor {
    private static final PropertiesConfiguration config =
            ConfigurationWrapper.getConfig();

    @Override
    public void process(Exchange exchange) throws Exception {
        String url = config.getString("tcs.service.url");
        String csp = exchange.getProperty("CSP.NAME", String.class);


        String tcsEndpoint = "restlet:" + url + "/references/"
                + "?restletMethod=post";

        exchange.getOut().setBody(exchange.getIn().getBody());
        exchange.getOut().setHeader(Exchange.CONTENT_TYPE,
                MediaType.APPLICATION_JSON);

        exchange.setProperty("TCS.ENDPOINT", tcsEndpoint);
    }


}
