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


/**
 * A Tag represents a single entry in the tag cloud database
 */
@Table(name = "tag_cloud", schema = "tcs@cassandra_pu")
@Entity
public class Tag {
	@Id
	@Column(name = "hash")
	private String hash;

	@Column(name = "domain")
	private String domain;

	@Column(name = "occurrences")
	private int occurrences;

	@Column(name = "reference_date")
	private Date referenceDate;

	@Column(name = "word")
	private String word;

    public Tag() {}

	public String getHash() {
		return hash;
	}

	public Tag setHash(String hash) {
		this.hash = hash;

		return this;
	}

	public String getDomain() {
		return domain;
	}

	public Tag setDomain(String domain) {
		this.domain = domain;

		return this;
	}

	public int getOccurrences() {
		return occurrences;
	}

	public Tag setOccurrences(int occurrences) {
		this.occurrences = occurrences;

		return this;
	}

	public Date getReferenceDate() {
		return referenceDate;
	}

	public Tag setReferenceDate(Date referenceDate) {
		this.referenceDate = referenceDate;

		return this;
	}

	public String getWord() {
        return word;
    }

    public Tag setWord(String word) {
        this.word = word;

		return this;
    }
}
