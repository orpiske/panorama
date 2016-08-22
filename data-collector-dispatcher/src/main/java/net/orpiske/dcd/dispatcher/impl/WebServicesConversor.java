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
import net.orpiske.dcd.collector.metadata.Occurrence;
import net.orpiske.dcd.utils.ConfigurationWrapper;
import net.orpiske.dcd.utils.DomainConfigurationWrapper;
import net.orpiske.exchange.header.v1.ApiType;
import net.orpiske.exchange.header.v1.CallerType;
import net.orpiske.exchange.header.v1.HeaderType;
import net.orpiske.exchange.loadservice.v1.CspType;
import net.orpiske.exchange.loadservice.v1.EmailListType;
import net.orpiske.exchange.loadservice.v1.EmailType;
import net.orpiske.exchange.loadservice.v1.SourceType;
import net.orpiske.tcs.utils.compression.Compressor;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.log4j.Logger;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.io.IOException;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Converts the metadata into the types
 */
public class WebServicesConversor {
    private static final Logger logger = Logger.getLogger(WebServicesConversor.class);

    private static final PropertiesConfiguration config =
            ConfigurationWrapper.getConfig();

    private static final PropertiesConfiguration domainConfig =
            DomainConfigurationWrapper.getConfig();

    public static HeaderType newHeader(final MetaData metaData) {
        HeaderType headerType = new HeaderType();
        ApiType apiType = new ApiType();

        apiType.setVersion("1.0.0");

        headerType.setApi(apiType);

        CallerType callerType = new CallerType();
        callerType.setVersion("1.0.0-SNAPSHOT");

        headerType.setCaller(callerType);

        return headerType;
    }

    private static XMLGregorianCalendar newXmlGregorianCalendar(final Date date) {
        GregorianCalendar reportDate = new GregorianCalendar();
        reportDate.setTime(date);

        XMLGregorianCalendar ret = null;

        try {
            ret = DatatypeFactory.newInstance().newXMLGregorianCalendar(reportDate);
        } catch (DatatypeConfigurationException e) {
            logger.error("Unable to properly transform a date for XML data transfer: " +
                e.getMessage(), e);
        }

        return ret;
    }


    public static SourceType newSource(final MetaData metaData) {
        SourceType sourceType = new SourceType();

        EmailListType emailListType = new EmailListType();
        List<EmailType> list = emailListType.getEmail();

        for (Occurrence occurrence : metaData.getOccurrenceList()) {
            EmailType emailType = new EmailType();

            String body = occurrence.getBody();

            boolean compress = config.getBoolean("request.data.compress", true);
            if (compress) {
                try {
                    setCompressedBody(emailType, body);
                }
                catch (IOException e) {
                    logger.error("Unable to compress data (the data will be sent " +
                            "uncompressed: " + e.getMessage(), e);

                    emailType.setCompressed(false);
                    emailType.setBody(body);
                }
            }
            else {
                emailType.setBody(body);
            }

            emailType.setHeader(occurrence.getOriginator());
            emailType.setName("Caiu");


            XMLGregorianCalendar date = newXmlGregorianCalendar(occurrence.getDate());
            emailType.setDate(date);

            list.add(emailType);
        }

        sourceType.setEmailList(emailListType);

        return sourceType;
    }


    public static void setCompressedBody(EmailType emailType, String body) throws IOException {
        emailType.setCompressed(true);

        byte[] compressedBytes = Compressor.compress(body);
        String encoded = Base64.encodeBase64String(compressedBytes);

        emailType.setBody(encoded);
    }


    public static CspType newCsp(final MetaData metaData) {
        CspType cspType = new CspType();

        String name = metaData.getWord().getWord();
        cspType.setName(name);
        String domain = domainConfig.getString(name, name.toLowerCase() + ".csp");
        cspType.setDomain(domain);

        cspType.setOccurrences(metaData.getOccurrenceCount());

        return cspType;
    }

    public static XMLGregorianCalendar newReportDate(final MetaData metaData) {
        return newXmlGregorianCalendar(new Date());
    }
}
