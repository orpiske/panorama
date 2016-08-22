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
package net.orpiske.sas.commons.types.converters;

import net.orpiske.exchange.sas.eval.v1.RequestType;
import net.orpiske.sas.commons.xml.XmlParserUtils;
import org.apache.camel.Exchange;
import org.apache.camel.TypeConversionException;
import org.apache.camel.support.TypeConverterSupport;

import javax.xml.bind.JAXBException;
import java.io.StringReader;

/**
 * Sample converter. This code is currently unused and just shows how to implement a
 * type converter.
 */

public class RequestTypeConverter extends TypeConverterSupport {
    @Override
    public <T> T convertTo(Class<T> tClass, Exchange exchange, Object o) throws TypeConversionException {
         /*
         * Access to the message is usually done by obtaining the message
         * body as a String object, which can be parsed.
         */
        String message = exchange.getIn().getBody().toString();
        StringReader reader = new StringReader(message);
        RequestType requestType;

        try {
            requestType = XmlParserUtils.unmarshal(RequestType.class, reader);
        }
        catch (JAXBException e) {
            throw new TypeConversionException(o, tClass, e);
        }

        return (T) requestType;
    }
}
