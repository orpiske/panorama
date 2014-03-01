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
package net.orpiske.sas.service.type.formats;

import org.apache.camel.converter.jaxb.JaxbDataFormat;

import javax.xml.namespace.QName;

public class FormatBuilder {
    private static String PACKAGE_NAME = "net.orpiske.exchange.sas.eval.v1";
    private static String NAMESPACE = "http://www.orpiske.net/exchange/sas/eval/v1";

    /**
     * Restricted constructor
     */
    private FormatBuilder() {}

    /**
     * Creates a JAXB Data reader format. We will use it to unmarshal the
     * requests.
     * @return a JAXB Data reader format object
     */
    public static JaxbDataFormat getReaderFormat() {
        return new JaxbDataFormat(PACKAGE_NAME);
    }


    /**
     * Creates a JAXB Data writer format. We will use it to marshal the
     * responses.
     * @return a JAXB Data writer format object
     */
    public static JaxbDataFormat getWriterFormat() {
        JaxbDataFormat writerFormat =
                new JaxbDataFormat(PACKAGE_NAME);

        writerFormat.setPartClass(PACKAGE_NAME + ".ResponseType");
        writerFormat.setPartNamespace(new QName(NAMESPACE, "response"));

        return writerFormat;
    }
}
