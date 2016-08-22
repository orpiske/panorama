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
package net.orpiske.mdm.broker.routes;

import net.orpiske.mdm.broker.processors.InternalProcessor;
import net.orpiske.mdm.broker.utils.ConfigurationWrapper;
import org.apache.camel.builder.RouteBuilder;
import org.apache.commons.configuration.PropertiesConfiguration;

import java.util.Arrays;

public class InternalRoute extends RouteBuilder {
    private static final PropertiesConfiguration config =
            ConfigurationWrapper.getConfig();
    private String name;

    public InternalRoute(final String name) {
        this.name = name;
    }


    private String[] destinationRoutes() {
        String[] ret;
        boolean enableSas = config.getBoolean("sas.service.enable", false);

        if (enableSas) {
            ret = new String[]{ "direct:sas.prepare", "direct:tcs.prepare" };
        }
        else {
            ret = new String[]{ "direct:tcs.prepare" };
        }

        return ret;
    }

    @Override
    public void configure() throws Exception {
         from("seda://BROKER.INTERNAL")
                    .routeId(name)
                    .process(new InternalProcessor())
                    .multicast()
                    .to(destinationRoutes());
    }
}
