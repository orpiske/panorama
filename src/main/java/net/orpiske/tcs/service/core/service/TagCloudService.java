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
package net.orpiske.tcs.service.core.service;

import net.orpiske.tcs.service.core.events.request.RequestCreateDomainEvent;
import net.orpiske.tcs.service.core.events.request.RequestCreateReference;
import net.orpiske.tcs.service.core.events.request.RequestDomainListEvent;
import net.orpiske.tcs.service.core.events.request.RequestTagCloudEvent;
import net.orpiske.tcs.service.core.events.response.DomainCreateEvent;
import net.orpiske.tcs.service.core.events.response.DomainListEvent;
import net.orpiske.tcs.service.core.events.response.ReferenceCreateEvent;
import net.orpiske.tcs.service.core.events.response.TagCloudEvent;

public interface TagCloudService {

    public DomainListEvent requestDomainList(RequestDomainListEvent requestDomainListEvent);

    public DomainCreateEvent createDomain(RequestCreateDomainEvent requestCreateDomainEvent);

    public TagCloudEvent requestTagCloud(RequestTagCloudEvent requestTagCloudEvent);

    public ReferenceCreateEvent createReference(RequestCreateReference requestCreateReference);
}
