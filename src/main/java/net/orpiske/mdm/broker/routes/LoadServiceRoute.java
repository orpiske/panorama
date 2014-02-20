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

import net.orpiske.mdn.broker.processors.DummyProcessor;
import org.apache.camel.builder.RouteBuilder;

public class LoadServiceRoute extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from("cxf://http://localhost:9001/mdm/broker/loadservice?" +
                "serviceClass=net.orpiske.exchange.loadservice.v1.LoadService&" +
                "wsdlURL=/wsdl/load/v1/loadservice.wsdl&" +
                "serviceName={http://www.orpiske.net/exchange/loadservice/v1}loadService&" +
                "portName={http://www.orpiske.net/exchange/loadservice/v1}loadServiceSOAP&" +
                "dataFormat=POJO&" +
                "loggingFeatureEnabled=true")
            .process(new DummyProcessor());
    }
}
