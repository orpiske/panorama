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
package net.orpiske.sas.service.main.actions.runner;

import net.orpiske.sas.service.routes.EvalServiceRoute;
import net.orpiske.sas.service.utils.ConfigurationWrapper;
import org.apache.activemq.camel.component.ActiveMQComponent;
import org.apache.activemq.camel.component.ActiveMQConfiguration;
import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.log4j.Logger;

public class ServiceRunner {
    private static final Logger logger = Logger.getLogger(ServiceRunner.class);
    private static final PropertiesConfiguration config =
            ConfigurationWrapper.getConfig();

    /**
     * Creates a new ActiveMQConfiguration object by reading
     * some values from the configuration file.
     * @return
     */
    private ActiveMQConfiguration getActiveMQConfiguration() {
        ActiveMQConfiguration configuration = new ActiveMQConfiguration();
        String userName = config.getString("activemq.server.username");
        String password = config.getString("activemq.server.password");
        String url = config.getString("activemq.server.url",
                "tcp://broker:61616");

        configuration.setUserName(userName);
        configuration.setPassword(password);
        configuration.setBrokerURL(url);

        return configuration;
    }

    private void initializeCamel() throws Exception {
        /**
         * Creates a new Camel Context - a set of routing rules
         */
        logger.debug("Initializing broker engine");
        CamelContext camelContext = new DefaultCamelContext();

        /**
         * The active MQ component is not provided by the default Camel
         * distribution, therefore we need to setup it's own component
         * for that.
         */
        logger.debug("Setting up ActiveMQ component");
        ActiveMQConfiguration activeMQConfiguration = getActiveMQConfiguration();

        /**
         * Creates the component using the ActiveMQConfiguration settings and
         * adds it to the Camel context
         */
        ActiveMQComponent component = new ActiveMQComponent(activeMQConfiguration);
        camelContext.addComponent("activemq", component);

        /**
         * Adding type converters. We don't use it here, but we could if we want
         * to do things like exchange.getIn().getBody(RequestType.class) without
         * relying on a data format. We do want to rely on a data format,
         * because it's simpler.
         *
         */
        //camelContext.getTypeConverterRegistry().addTypeConverter(RequestType.class,
        //        String.class, new RequestTypeConverter());


        /**
         * All defined routes must be added to the context as well. For SAS
         * we only have a single route.
         */
        logger.debug("Adding routes");
        camelContext.addRoutes(new EvalServiceRoute(""));

        logger.debug("Starting Apache Camel");
        camelContext.start();
        Thread.currentThread().join();
    }


    public int run() {
        try {
            initializeCamel();
            return 0;
        }
        catch (Exception e) {
            logger.error("Error: " + e.getMessage(), e);
            if (e.getCause() != null) {
                logger.error("Cause: " + e.getCause().getMessage(), e.getCause());
            }
            return -1;
        }
    }

}
