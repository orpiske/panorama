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

import net.orpiske.mdm.broker.processors.sas.EvalRequestConversor;
import net.orpiske.mdm.broker.processors.tcs.TcsEndpointResolver;
import net.orpiske.mdm.broker.processors.tcs.TcsRequestConversor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;

public class TcsRoute extends RouteBuilder {

    private String name;

    public TcsRoute(String name) {
        this.name = name;
    }

    @Override
    public void configure() throws Exception {
        from("direct:tcs.prepare")
                .split()
                .method(TcsRequestConversor.class, "split")
                .marshal().json(JsonLibrary.Jackson)
                .to("log:net.orpiske.mdm.broker.exchanges.evalservice?level=INFO")
                .process(new TcsEndpointResolver())
                .throttle(500).timePeriodMillis(5000)
                .dynamicRouter(header("TCS.ENDPOINT"));
    }
}
