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
package net.orpiske.tcs.service.core.domain;

import net.orpiske.tcs.service.core.exception.ReferenceConversionException;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.IOException;
import java.util.Date;

/**
 * This is used to create new reference data
 */
public class ReferenceCreateData {
    private Csp csp;
    private Text text;

    public Csp getCsp() {
        return csp;
    }

    public void setCsp(Csp csp) {
        this.csp = csp;
    }

    public Text getText() {
        return text;
    }

    public void setText(Text text) {
        this.text = text;
    }

    public Reference toReference() throws ReferenceConversionException {
        Reference reference = new Reference();

        reference.setReferenceDate(new Date());
        reference.setInclusionDate(new Date());
        reference.setDomain(csp.getDomain());

        try {
            String decompressed = text.getDecompressedText();

            reference.setHash(DigestUtils.sha256Hex(decompressed));
            reference.setReferenceText(decompressed);
        }
        catch (IOException e) {
            throw new ReferenceConversionException(e);
        }

        return reference;
    }
}
