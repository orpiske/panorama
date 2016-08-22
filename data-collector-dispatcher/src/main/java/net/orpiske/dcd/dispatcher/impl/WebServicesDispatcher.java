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
package net.orpiske.dcd.dispatcher.impl;

import net.orpiske.dcd.collector.metadata.MetaData;
import net.orpiske.dcd.dispatcher.Dispatcher;
import net.orpiske.dcd.utils.ConfigurationWrapper;
import net.orpiske.exchange.header.v1.HeaderType;
import net.orpiske.exchange.loadservice.v1.*;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.log4j.Logger;

import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;
import javax.xml.ws.Holder;
import java.util.Set;

import static net.orpiske.dcd.dispatcher.impl.WebServicesConversor.*;

public class WebServicesDispatcher implements Dispatcher {
    private static final Logger logger = Logger.getLogger(WebServicesDispatcher.class);
    private static final PropertiesConfiguration config =
            ConfigurationWrapper.getConfig();

    private JaxWsProxyFactoryBean factory;
    private LoadService servicePort;

    public WebServicesDispatcher() {
        factory = new JaxWsProxyFactoryBean();

		/*
		 * Setup logging goods
		 */
        factory.getInInterceptors().add(new LoggingInInterceptor());
        factory.getOutInterceptors().add(new LoggingOutInterceptor());

		/*
		 * Sets the service class
		 */
        factory.setServiceClass(LoadService.class);

		/*
		 * Target URL
		 */
        String endpoint = config.getString("broker.ws.endpoint", "http://localhost:8001/mdm/broker/loadservice");

        factory.setAddress(endpoint);
        factory.setWsdlURL("classpath:/wsdl/load/v1/loadservice.wsdl");
        factory.setServiceName(new QName("http://www.orpiske.net/exchange/loadservice/v1",
                "loadService"));

        servicePort = (LoadService) factory.create();
    }


    private void callService(final MetaData metaData) {
        HeaderType headerType = newHeader(metaData);
        SourceType sourceType = newSource(metaData);
        CspType cspType = newCsp(metaData);
        XMLGregorianCalendar reportDate = newReportDate(metaData);

        Holder<Integer> code = new Holder<Integer>();
        Holder<String> message = new Holder<String>();

        servicePort.loadService(headerType, sourceType, cspType, reportDate,
                code, message);
    }


    @Override
    public void dispatch(final MetaData metaData) throws Exception {
        callService(metaData);
    }

    @Override
    public void dispatch(Set<MetaData> metaDataSet) throws Exception {
        for (MetaData metaData : metaDataSet) {
            callService(metaData);
        }
    }
}
