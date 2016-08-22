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
package net.orpiske.mdm.broker.processors.sas;

import net.orpiske.exchange.loadservice.v1.EmailType;
import net.orpiske.exchange.sas.common.header.v1.ApiType;
import net.orpiske.exchange.sas.common.header.v1.CallerType;
import net.orpiske.exchange.sas.common.header.v1.HeaderType;
import net.orpiske.exchange.sas.eval.v1.EvalRequestType;
import net.orpiske.exchange.sas.eval.v1.RequestType;
import net.orpiske.mdm.broker.types.wrapper.LoadServiceWrapper;
import net.orpiske.sas.commons.xml.XmlWriterUtils;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

public class EvalRequestConversor {

    private HeaderType newHeader() {
        HeaderType headerType = new HeaderType();
        ApiType apiType = new ApiType();

        apiType.setVersion("1.0.0");
        headerType.setApi(apiType);

        CallerType callerType = new CallerType();
        callerType.setVersion("1.0.0-SNAPSHOT");
        callerType.setName("MDM-Broker");
        headerType.setCaller(callerType);


        return headerType;
    }

    private EvalRequestType getText(EmailType emailType) {
        EvalRequestType evalRequestType = new EvalRequestType();

        Boolean compressed = emailType.isCompressed();
        evalRequestType.setCompressed(compressed);

        String format = emailType.getFormat();
        evalRequestType.setFormat(format);

        String phrase = emailType.getBody();
        evalRequestType.setPhrase(phrase);

        return evalRequestType;
    }

    private List<RequestType> convert(LoadServiceWrapper wrapper) {
        List<RequestType> requestTypeList = new ArrayList<RequestType>();

        int occurrences = wrapper.getCspType().getOccurrences();

        for (int i = 0; i < occurrences; i++) {
            RequestType requestType = new RequestType();
            requestType.setHeader(newHeader());

            EmailType emailType = wrapper.getSourceType().getEmailList()
                    .getEmail().get(i);
            EvalRequestType evalRequestType = getText(emailType);

            requestType.setEvalRequest(evalRequestType);

            requestTypeList.add(requestType);
        }

        return requestTypeList;
    }


    public List<String> split(LoadServiceWrapper wrapper) {
        List<String> stringList = new ArrayList<String>();
        List<RequestType> requestTypeList = convert(wrapper);

        for (RequestType requestType : requestTypeList) {
            StringWriter writer = new StringWriter();

            String ns = "http://www.orpiske.net/exchange/sas/eval/v1";
            JAXBElement<RequestType> element = XmlWriterUtils.newJAXBElement(ns, "request", RequestType.class, requestType);

            try {
                XmlWriterUtils.marshal(element, requestType, writer);

                stringList.add(writer.toString());
            } catch (JAXBException e) {
                // TODO: needs better handling
                e.printStackTrace();
            }
        }

        return stringList;
    }
}
