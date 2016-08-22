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
package net.orpiske.mdm.broker.processors.tcs;

import com.google.common.net.InternetDomainName;
import net.orpiske.exchange.loadservice.v1.EmailType;
import net.orpiske.mdm.broker.types.wrapper.LoadServiceWrapper;
import net.orpiske.tcs.service.core.domain.Domain;
import net.orpiske.tcs.service.core.domain.ReferenceCreateData;
import net.orpiske.tcs.service.core.domain.Text;
import org.apache.log4j.Logger;

import javax.xml.datatype.XMLGregorianCalendar;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TcsRequestConversor {
    private static final Logger logger = Logger.getLogger(TcsRequestConversor.class);
    private static final String INVALID_DOMAIN = ".csp";

    /**
     * Gets a domain object out of data from the request
     * @param wrapper the request wrapper
     * @return a Domain object
     */
    private Domain getCspFromRequest(LoadServiceWrapper wrapper) {
        Domain csp = new Domain();

        String cspName = wrapper.getCspType().getName();
        String domain = wrapper.getCspType().getDomain();

        csp.setName(cspName);
        if (domain == null) {
            logger.warn("The requester did not inform a domain, defaulting to .csp " +
                    " for further processing");

            csp.setDomain(cspName + INVALID_DOMAIN);
        }
        else {
            InternetDomainName d = InternetDomainName.from(domain);

            if (!d.isPublicSuffix()) {
                logger.warn("Input domain " + domain + " is not a public suffix");
            }

            csp.setDomain(domain);
        }
        return csp;
    }

    public List<ReferenceCreateData> split(LoadServiceWrapper wrapper) {
        List<ReferenceCreateData> requestTypeList = new ArrayList<ReferenceCreateData>();

        int occurrences = wrapper.getCspType().getOccurrences();

        for (int i = 0; i < occurrences; i++) {
            EmailType emailType = wrapper.getSourceType().getEmailList()
                    .getEmail().get(i);
            String phrase = emailType.getBody();

            ReferenceCreateData data = new ReferenceCreateData();

            XMLGregorianCalendar cal = emailType.getDate();
            if (cal != null) {
                data.setDate(cal.toGregorianCalendar().getTime());
            }

            try {
                Text text;

                // We only need to compress data if it isn't already compressed
                if (emailType.isCompressed()) {
                    text = new Text();

                    text.setEncodedText(phrase);
                }
                else {
                    text = Text.fromString(phrase);
                }

                data.setText(text);
            }
            catch (IOException e) {
                logger.error("Unable to convert reference", e);
                continue;
            }


            Domain domain = getCspFromRequest(wrapper);
            data.setDomain(domain);

            requestTypeList.add(data);
        }

        return requestTypeList;
    }
}
