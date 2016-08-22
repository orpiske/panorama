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

import net.orpiske.tcs.service.core.domain.*;
import net.orpiske.tcs.service.core.events.request.RequestCreateDomainEvent;
import net.orpiske.tcs.service.core.events.request.RequestCreateReference;
import net.orpiske.tcs.service.core.events.request.RequestDomainListEvent;
import net.orpiske.tcs.service.core.events.request.RequestTagCloudEvent;
import net.orpiske.tcs.service.core.events.response.DomainCreateEvent;
import net.orpiske.tcs.service.core.events.response.DomainListEvent;
import net.orpiske.tcs.service.core.events.response.ReferenceCreateEvent;
import net.orpiske.tcs.service.core.events.response.TagCloudEvent;
import net.orpiske.tcs.service.core.repository.DomainRepository;
import net.orpiske.tcs.service.core.repository.ReferenceRepository;
import net.orpiske.tcs.service.core.repository.TagRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class TagCloudEventHandler implements TagCloudService {
    private static final Logger logger = Logger.getLogger(TagCloudEventHandler.class);

    @Autowired
    private DomainRepository domainRepository;

    @Autowired
    private ReferenceRepository refRepository;

    @Autowired
    private TagRepository tagRepository;


    @Override
    public DomainListEvent requestDomainList(RequestDomainListEvent requestDomainListEvent) {
        List<Domain> list = domainRepository.findAll();
        DomainListEvent event = new DomainListEvent(list);

        return event;
    }

    @Override
    public DomainCreateEvent createDomain(RequestCreateDomainEvent requestCreateDomainEvent) {
        Domain domain = requestCreateDomainEvent.getDomain();

        domainRepository.save(domain);

        DomainCreateEvent event = new DomainCreateEvent(domain);

        return event;
    }

    @Override
    public TagCloudEvent requestTagCloud(RequestTagCloudEvent requestTagCloudEvent) {
        Domain domain = requestTagCloudEvent.getDomain();
        TagCloud tagCloud;

		if (domain != null) {
			tagCloud = tagRepository.findByCsp(domain);
		}
		else {
			tagCloud = tagRepository.findAll();
		}

        TagCloudEvent tagCloudEvent = new TagCloudEvent(tagCloud);
        return tagCloudEvent;
    }

    @Override
    public ReferenceCreateEvent createReference(RequestCreateReference requestCreateReference) {
        ReferenceCreateData data = requestCreateReference.getData();

        ReferenceCreateEvent event = new ReferenceCreateEvent();

        try {
            Domain domain = data.getDomain();
            domainRepository.save(domain);

            Reference reference = data.toReference();
            refRepository.add(reference);
        }
        catch (Exception e) {
            logger.error("Unable to convert reference request to a reference "
                    + " object", e);

            event.setUpdated(false);
        }

        return event;
    }
}
