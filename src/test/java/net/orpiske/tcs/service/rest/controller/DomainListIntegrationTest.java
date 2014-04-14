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

import net.orpiske.tcs.service.core.events.request.RequestDomainListEvent;
import net.orpiske.tcs.service.core.service.TagCloudService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import static net.orpiske.tcs.service.rest.controller.fixtures.RestEventFixtures.*;

public class DomainListIntegrationTest {

    MockMvc mockMvc;

    @InjectMocks
    DomainQueriesController domainQueriesController;

    @Mock
    TagCloudService tagCloudService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        this.mockMvc = standaloneSetup(domainQueriesController,
                    new MappingJackson2HttpMessageConverter())
                .build();


    }


    /**
     * Tests the ability to obtain a list of existing CSPs
     * @throws Exception
     */
    @Test
    public void testCspList() throws Exception {
        when(tagCloudService.requestDomainList(any(RequestDomainListEvent.class)))
                .thenReturn(domainListEvent());

        this.mockMvc.perform(
                get("/domain")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.domainList[0].name").value("GVT"))
                .andExpect(jsonPath("$.domainList[1].name").value("NET"))
                .andExpect(jsonPath("$.domainList[2].name").value("Oi"))
                .andExpect(status().isOk());
    }
}
