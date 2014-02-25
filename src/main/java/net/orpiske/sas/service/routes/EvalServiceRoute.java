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
package net.orpiske.sas.service.routes;

import net.orpiske.sas.service.processors.EvalServiceProcessor;
import org.apache.camel.builder.RouteBuilder;

/**
 * A route declares a routing rule, tying 1 or more endpoints together
 * and making it possible to define the routing and processing for the
 * exchanges passing through the route
 */
public class EvalServiceRoute extends RouteBuilder {


    private String name;

    public EvalServiceRoute(final String name) {
        this.name = name;
    }


    @Override
    public void configure() throws Exception {
        from("activemq:queue:sas.request?" +
                "concurrentConsumers=2&" +
                "maxConcurrentConsumers=4")
                .process(new EvalServiceProcessor());
    }
}
