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
package net.orpiske.mdm.broker.routes.tcs;

import net.orpiske.mdm.broker.processors.tcs.TcsRequestConversor;
import net.orpiske.mdm.broker.utils.ConfigurationWrapper;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.restlet.RestletConstants;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.restlet.data.MediaType;

public class TcsRoute extends RouteBuilder {
    private static final PropertiesConfiguration config =
            ConfigurationWrapper.getConfig();

    private String name;

    public TcsRoute(String name) {
        this.name = name;
    }

    @Override
    public void configure() throws Exception {
        String username = config.getString("tcs.service.username");
        String password = config.getString("tcs.service.password");

        String url = config.getString("tcs.service.url");
        String tcsEndpoint = "restlet:" + url + "/references/"
                + "?restletMethod=post";

        /**
         * If the messages fail to process, I'd like to have them logged, with the body
         * exchange id and all that so I can find out the problem
         * a
         */
        onException()
                .handled(true)
                .to("log:net.orpiske.mdm.broker.exchanges.tcsservice.deadletter?level=ERROR&showOut=true&showExchangeId=true");

        from("direct:tcs.prepare")
                .routeId(name)
                .split()
                .method(TcsRequestConversor.class, "split")
                .marshal().json(JsonLibrary.Jackson)
                .to("log:net.orpiske.mdm.broker.exchanges.tcservice?level=INFO&showExchangeId=true")
                    .setHeader(RestletConstants.RESTLET_LOGIN, constant(username))
                    .setHeader(RestletConstants.RESTLET_PASSWORD, constant(password))
                    .setHeader(Exchange.CONTENT_TYPE, constant(MediaType.APPLICATION_JSON))
                    .throttle(500).timePeriodMillis(5000)
                .to(tcsEndpoint);
    }
}
