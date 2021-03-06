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
package net.orpiske.mdm.broker.routes.sas;

import net.orpiske.mdm.broker.processors.sas.EvalRequestConversor;
import net.orpiske.mdm.broker.processors.sas.EvalResponseProcessor;
import net.orpiske.mdm.broker.utils.ConfigurationWrapper;
import org.apache.camel.builder.RouteBuilder;
import org.apache.commons.configuration.PropertiesConfiguration;

public class EvalServiceRoute extends RouteBuilder {
    private static final PropertiesConfiguration config =
            ConfigurationWrapper.getConfig();
    private String name;

    public EvalServiceRoute(final String name) {
        this.name = name;
    }


    @Override
    public void configure() throws Exception {
        String sasRequestQueue = config.getString("sas.request.queue");
        String sasResponseQueue = config.getString("sas.response.queue");

        from("direct:sas.prepare")
                .routeId(name)
                .split()
                    .method(EvalRequestConversor.class, "split")
                .to("log:net.orpiske.mdm.broker.exchanges.evalservice?level=INFO")
                .to("activemq:queue:" + sasRequestQueue + "?replyTo=" + sasResponseQueue)
                .to("log:net.orpiske.mdm.broker.exchanges.evalservice?level=INFO")
                    .aggregate(property("CSP.NAME"))
                    .ignoreInvalidCorrelationKeys()
                    .completionSize(property("CSP.REFERENCE_COUNT"))
                    .groupExchanges()
                    .process(new EvalResponseProcessor())
                    .to("mock:sas.processed");

    }
}
