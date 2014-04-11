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

import javax.persistence.*;


/**
 * Abstracts a CSP
 */
@Table(name = "csp", schema = "tcs@cassandra_pu")
@Entity
public class Csp {

    @Id
    @Column(name = "domain")
    private String domain;

    @Column(name = "name")
    private String name;

    public Csp() {}

    public Csp(final String name, final String domain) {
        this.name = name;
        this.domain = domain;
    }

    public String getDomain() {
        return domain;
    }

    public Csp setDomain(String domain) {
        this.domain = domain;

		return this;
    }

    public String getName() {
        return name;
    }

    public Csp setName(String name) {
        this.name = name;

		return this;
    }
}
