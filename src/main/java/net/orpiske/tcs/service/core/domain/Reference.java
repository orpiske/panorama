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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "references")
public class Reference {

    @Id
    @Column(name = "hash")
    private String hash;

    @Column(name = "domain")
    private String domain;

    @Column(name = "reference_date")
    private Date referenceDate;

    @Column(name = "inclusion_date")
    private Date inclusionDate;

    @Column(name = "reference_text")
    private String referenceText;

    public String getHash() {
        return hash;
    }

    public Reference setHash(String hash) {
        this.hash = hash;

		return this;
    }

    public String getDomain() {
        return domain;
    }

    public Reference setDomain(String domain) {
        this.domain = domain;

		return this;
    }

    public Date getReferenceDate() {
        return referenceDate;
    }

    public Reference setReferenceDate(Date referenceDate) {
        this.referenceDate = referenceDate;

		return this;
    }

    public Date getInclusionDate() {
        return inclusionDate;
    }

    public Reference setInclusionDate(Date inclusionDate) {
        this.inclusionDate = inclusionDate;

		return this;
    }

    public String getReferenceText() {
        return referenceText;
    }

    public Reference setReferenceText(String referenceText) {
        this.referenceText = referenceText;

		return this;
    }
}
