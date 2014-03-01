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
package net.orpiske.mdm.broker.main.actions.runner;

import net.orpiske.mdm.broker.routes.InternalRoute;
import net.orpiske.mdm.broker.routes.LoadServiceRoute;
import net.orpiske.mdm.broker.routes.sas.EvalServiceRoute;
import net.orpiske.mdm.broker.utils.ConfigurationWrapper;
import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.log4j.Logger;

import org.apache.activemq.camel.component.ActiveMQConfiguration;
import org.apache.activemq.camel.component.ActiveMQComponent;

public class BrokerRunner {
    private static final Logger logger = Logger.getLogger(BrokerRunner.class);
    private static final PropertiesConfiguration config = ConfigurationWrapper.getConfig();

    /**
     * Creates a new ActiveMQConfiguration object by reading
     * some values from the configuration file.
     * @return A properly configured ActiveMQConfiguration object
     */
    private ActiveMQConfiguration getActiveMQConfiguration() {
        ActiveMQConfiguration configuration = new ActiveMQConfiguration();
        String userName = config.getString("sas.server.username");
        String password = config.getString("sas.server.password");
        String url = config.getString("sas.server.url",
                "tcp://broker:61616");


        configuration.setUserName(userName);
        configuration.setPassword(password);
        configuration.setBrokerURL(url);

        return configuration;
    }

    /**
     * Initializes Camel
     * @throws Exception
     */
    private void initializeCamel() throws Exception {
        logger.debug("Initializing broker engine");
        CamelContext camelContext = new DefaultCamelContext();

        // Sets up the Active MQ component
        logger.debug("Setting up ActiveMQ component");
        ActiveMQConfiguration activeMQConfiguration = getActiveMQConfiguration();
        ActiveMQComponent component = new ActiveMQComponent(activeMQConfiguration);
        camelContext.addComponent("activemq", component);


        // Adds new routes
        logger.debug("Adding routes");
        camelContext.addRoutes(new LoadServiceRoute("LoadService"));
        camelContext.addRoutes(new InternalRoute("InternalRoute"));
        camelContext.addRoutes(new EvalServiceRoute("EvalService"));

        // Starts Apache Camel
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
