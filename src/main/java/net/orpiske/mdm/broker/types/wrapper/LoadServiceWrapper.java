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
package net.orpiske.mdm.broker.types.wrapper;

import net.orpiske.exchange.header.v1.HeaderType;
import net.orpiske.exchange.loadservice.v1.CspType;
import net.orpiske.exchange.loadservice.v1.SourceType;
import org.apache.log4j.Logger;

import javax.xml.datatype.XMLGregorianCalendar;

public class LoadServiceWrapper {
    private static final Logger logger = Logger.getLogger(LoadServiceWrapper.class);

    private HeaderType headerType;
    private SourceType sourceType;
    private CspType cspType;
    private XMLGregorianCalendar reportDate;

    public void set(final Object object) {
        if (object instanceof HeaderType) {
            setHeaderType((HeaderType) object);
        }
        else {
            if (object instanceof SourceType) {
                setSourceType((SourceType) object);
            }
            else {
                if (object instanceof CspType) {
                    setCspType((CspType) object);
                }
                else {
                    if (object instanceof XMLGregorianCalendar) {
                        setReportDate((XMLGregorianCalendar) object);
                    }
                    else {
                        logger.error("Unhandled type conversion: " +
                                (object == null ? "null" : object.getClass()) +
                                " cannot be wrapped in this class");
                    }
                }
            }
        }
     }

    public HeaderType getHeaderType() {
        return headerType;
    }

    public void setHeaderType(HeaderType headerType) {
        this.headerType = headerType;
    }

    public SourceType getSourceType() {
        return sourceType;
    }

    public void setSourceType(SourceType sourceType) {
        this.sourceType = sourceType;
    }

    public CspType getCspType() {
        return cspType;
    }

    public void setCspType(CspType cspType) {
        this.cspType = cspType;
    }

    public XMLGregorianCalendar getReportDate() {
        return reportDate;
    }

    public void setReportDate(XMLGregorianCalendar xmlGregorianCalendar) {
        this.reportDate = xmlGregorianCalendar;
    }
}
