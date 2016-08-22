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

package net.orpiske.tcs.client.base;

import net.orpiske.tcs.service.core.domain.Domain;
import net.orpiske.tcs.service.core.domain.DomainList;
import net.orpiske.tcs.service.core.domain.Tag;
import net.orpiske.tcs.service.core.domain.TagCloud;

public interface ServiceClient {

    /**
     * Gets the whole tag cloud (ie: for all CSPs)
     * @return the tag cloud object
     */
    TagCloud requestTagCloud();

    /**
     * Gets the tag cloud for a single CSP
     * @param domain the CSP domain to look for
     * @return the tag cloud object
     */
    TagCloud requestTagCloud(final Domain domain);


    /**
     * Gets the list of domains
     * @return the domain list object
     */
    DomainList requestDomainList();
}
