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
import net.orpiske.exchange.header.v1.ApiType;
import net.orpiske.exchange.header.v1.CallerType;
import net.orpiske.exchange.header.v1.HeaderType;
import net.orpiske.exchange.loadservice.v1.CspType;
import net.orpiske.exchange.loadservice.v1.EmailType;
import net.orpiske.exchange.loadservice.v1.LoadService;
import net.orpiske.exchange.loadservice.v1.SourceType;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;
import javax.xml.ws.Holder;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Set;

public class WebServicesDispatcher implements Dispatcher {
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

    private HeaderType newHeader() {
        HeaderType headerType = new HeaderType();
        ApiType apiType = new ApiType();

        apiType.setVersion("1.0.0");

        headerType.setApi(apiType);

        CallerType callerType = new CallerType();
        callerType.setVersion("1.0.0-SNAPSHOT");

        headerType.setCaller(callerType);

        return headerType;
    }

    private SourceType newSource() {
        SourceType sourceType = new SourceType();
        EmailType emailType = new EmailType();

        emailType.setBody("ABCDCDEEFASDASDF");
        emailType.setHeader("From otavio em casa.com.br");
        emailType.setName("Caiu");

        sourceType.setEmail(emailType);

        return sourceType;
    }


    private CspType newCsp() {
        CspType cspType = new CspType();

        cspType.setName("MeuProvedor");
        cspType.setOccurrences(10);

        return cspType;
    }

    private XMLGregorianCalendar newReportDate() {
        GregorianCalendar reportDate = new GregorianCalendar();
        reportDate.setTime(new Date());

        XMLGregorianCalendar ret = null;

        try {
            ret = DatatypeFactory.newInstance().newXMLGregorianCalendar(reportDate);

            return ret;
        } catch (DatatypeConfigurationException e) {
            e.printStackTrace();
        }

        return null;
    }


    private void setup() {
        HeaderType headerType = newHeader();
        SourceType sourceType = newSource();
        CspType cspType = newCsp();
        XMLGregorianCalendar reportDate = newReportDate();

        Holder<Integer> code = new Holder<Integer>();
        Holder<String> message = new Holder<String>();

        servicePort.loadService(headerType, sourceType, cspType, reportDate,
                code, message);

		/*
		 * Call the service and handle the response
		 */
        // XMLGregorianCalendar ret = servicePort.loadService();

        /*
        if (ret != null) {
            System.out.println("Ret = " + ret.toGregorianCalendar().toString());
        }
        else {
            System.out.println("No response from the server");
        }
        */

    }


    @Override
    public void dispatch(MetaData metaData) throws Exception {
        setup();
    }

    @Override
    public void dispatch(Set<MetaData> metaDataSet) throws Exception {
        setup();
    }
}
