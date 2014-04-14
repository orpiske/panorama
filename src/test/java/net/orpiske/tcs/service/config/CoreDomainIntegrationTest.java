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
package net.orpiske.tcs.service.config;

import net.orpiske.tcs.service.core.domain.Domain;
import net.orpiske.tcs.service.core.domain.DomainList;
import net.orpiske.tcs.service.core.events.request.RequestCreateReference;
import net.orpiske.tcs.service.core.events.request.RequestDomainListEvent;
import net.orpiske.tcs.service.core.events.response.DomainListEvent;
import net.orpiske.tcs.service.core.events.request.RequestCreateDomainEvent;
import net.orpiske.tcs.service.core.events.response.ReferenceCreateEvent;
import net.orpiske.tcs.service.core.service.TagCloudService;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import static net.orpiske.tcs.service.rest.controller.fixtures.RestDataFixtures.customReferenceRequestData;

import java.io.IOException;

import static org.junit.Assert.*;

@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {CoreConfig.class})
public class CoreDomainIntegrationTest {

    @Autowired
    TagCloudService tagCloudService;

    @Before
    public void setup() {

    }

    @Test
    public void testAddCspToTheSystem() {
        RequestCreateDomainEvent requestCreateDomainEvent =
                new RequestCreateDomainEvent(new Domain("HomeMadeCSP", "www.home.com.br"));

        tagCloudService.createDomain(requestCreateDomainEvent);

        RequestDomainListEvent requestDomainListEvent = new RequestDomainListEvent();

        DomainListEvent domainListEvent = tagCloudService.requestDomainList(
                requestDomainListEvent);

        DomainList domainList = domainListEvent.getDomainList();
        assertNotNull("Returned list is null", domainList);

        assertTrue("Record wasn't added successfully", (domainList.getDomainList().size() >= 1));
    }


    @Test
    public void testAddReferenceToTheSystem() throws IOException {
        RequestCreateReference requestEvent =
                new RequestCreateReference(customReferenceRequestData());

        ReferenceCreateEvent tagCloudUpdateEvent = tagCloudService
                .createReference(requestEvent);

        assertTrue(tagCloudUpdateEvent.isUpdated());
    }
}
