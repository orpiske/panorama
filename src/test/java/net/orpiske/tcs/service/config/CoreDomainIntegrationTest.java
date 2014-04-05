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

import net.orpiske.tcs.service.core.domain.Csp;
import net.orpiske.tcs.service.core.domain.CspList;
import net.orpiske.tcs.service.core.events.request.RequestCreateReference;
import net.orpiske.tcs.service.core.events.response.CspListEvent;
import net.orpiske.tcs.service.core.events.request.RequestCreateCspEvent;
import net.orpiske.tcs.service.core.events.request.RequestCspListEvent;
import net.orpiske.tcs.service.core.events.response.ReferenceCreateEvent;
import net.orpiske.tcs.service.core.service.TagCloudService;

import net.orpiske.tcs.service.utils.LogConfigurator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import static net.orpiske.tcs.service.rest.controller.fixtures.RestDataFixtures.customReferenceRequestData;

import java.io.IOException;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {CoreConfig.class})
public class CoreDomainIntegrationTest {

    @Autowired
    TagCloudService tagCloudService;

    static {
        LogConfigurator.trace();
    }

    @Before
    public void setup() {

    }

    @Test
    public void testAddCspToTheSystem() {
        RequestCreateCspEvent requestCreateCspEvent =
                new RequestCreateCspEvent(new Csp("HomeMadeCSP", "www.home.com.br"));

        tagCloudService.createCsp(requestCreateCspEvent);

        RequestCspListEvent requestCspListEvent = new RequestCspListEvent();

        CspListEvent cspListEvent = tagCloudService.requestCspList(
                requestCspListEvent);

        CspList cspList = cspListEvent.getCspList();
        assertNotNull("Returned list is null", cspList);

        assertTrue("Record wasn't added successfully", (cspList.getCspList().size() >= 1));
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
