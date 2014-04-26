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
package net.orpiske.panorama

import net.orpiske.tcs.client.base.DefaultEndPoint
import net.orpiske.tcs.client.base.EndPoint
import net.orpiske.tcs.client.services.TagCloudServiceClient
import net.orpiske.tcs.service.core.domain.DomainList

import javax.servlet.ServletContext

class DomainsController {
    private static final EndPoint endpoint = new DefaultEndPoint("http://localhost:8080");
    private TagCloudServiceClient client;

    public DomainsController() {
        client = new TagCloudServiceClient(endpoint);
    }

    def index() {
        DomainList domainList = client.requestDomainList();

        [ domainList : domainList.getDomainList() ];
    }
}
