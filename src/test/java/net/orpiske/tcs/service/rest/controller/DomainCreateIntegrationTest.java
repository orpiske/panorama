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
package net.orpiske.tcs.service.rest.controller;

import net.orpiske.tcs.service.core.events.request.RequestCreateDomainEvent;
import net.orpiske.tcs.service.core.service.TagCloudService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;

import static net.orpiske.tcs.service.rest.controller.fixtures.RestEventFixtures.cspCreateEvent;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

public class DomainCreateIntegrationTest {
    MockMvc mockMvc;

    @InjectMocks
    DomainCommandsController domainCommandsController;

    @Mock
    TagCloudService tagCloudService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        this.mockMvc = standaloneSetup(domainCommandsController,
                new MappingJackson2HttpMessageConverter())
                .build();
    }


    /**
     * Tests the ability to add a new CSP to the system
     * @throws Exception
     */
    @Test
    public void testCspTagCloudCreateNewCSP() throws Exception {
        when(tagCloudService.createDomain(any(RequestCreateDomainEvent.class)))
                .thenReturn(cspCreateEvent());


        this.mockMvc.perform(
                post("/domain/{domain}", "terra.com.br")
                        .content("{ \"name\": \"Terra\", \"domain\": \"terra.com.br\" } ")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
