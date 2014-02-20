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

import net.orpiske.mdm.broker.utils.ConfigurationWrapper;
import net.orpiske.mdn.broker.processors.DummyProcessor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.commons.configuration.PropertiesConfiguration;

public class LoadServiceRoute extends RouteBuilder {

    private static final PropertiesConfiguration config =
            ConfigurationWrapper.getConfig();

    private String name;

    public LoadServiceRoute(final String name) {
        this.name = name;
    }

    private String getListenAddress() {
        String protocol = config.getString("broker.ws.protocol", "http");
        String hostname = config.getString("broker.ws.host", "localhost");
        String port = config.getString("broker.ws.port", "9001");

        return protocol + "://" + hostname + ":" + port;
    }

    private String getServiceClass() {
        return "net.orpiske.exchange." + name.toLowerCase() + ".v1." + name;
    }

    private String getWSDLUrl() {
        return "/wsdl/load/v1/" + name.toLowerCase() + ".wsdl";
    }

    private String getServiceName() {
        return "{http://www.orpiske.net/exchange/" + name.toLowerCase() + "/v1}" + "loadService";
    }


    private String getPortName() {
        return "{http://www.orpiske.net/exchange/" + name.toLowerCase() + "/v1}" + "loadServiceSOAP";
    }

    @Override
    public void configure() throws Exception {
        String address = getListenAddress();
        String serviceClass = getServiceClass();
        String wsdlURL = getWSDLUrl();
        String serviceName = getServiceName();
        String portName = getPortName();

        from("cxf://" + address + "/mdm/broker/loadservice?" +
                "serviceClass=" + serviceClass + "&" +
                "wsdlURL=" + wsdlURL + "&" +
                "serviceName=" + serviceName + "&" +
                "portName=" + portName + "&" +
                "dataFormat=POJO&" +
                "loggingFeatureEnabled=true")
            .process(new DummyProcessor());
    }
}
