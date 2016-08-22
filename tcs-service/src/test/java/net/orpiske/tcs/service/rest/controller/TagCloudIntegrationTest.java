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

import net.orpiske.tcs.service.core.events.request.RequestTagCloudEvent;
import net.orpiske.tcs.service.core.service.TagCloudService;
import net.orpiske.tcs.service.utils.LogConfigurator;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import static net.orpiske.tcs.service.rest.controller.fixtures.RestEventFixtures.*;

public class TagCloudIntegrationTest {
    MockMvc mockMvc;

    @InjectMocks
    TagCloudQueriesController tagCloudQueriesController;

    @Mock
    TagCloudService tagCloudService;


    /**
     * Initialization / setup
     */
    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        this.mockMvc = standaloneSetup(tagCloudQueriesController,
                new MappingJackson2HttpMessageConverter())
                .build();
    }


    /**
     * Tests the ability to return a cloud for all CSPs
     * @throws Exception
     */
    @Test
    public void testTagCloud() throws Exception {
        when(tagCloudService.requestTagCloud(any(RequestTagCloudEvent.class)))
                .thenReturn(tagCloudEvent());


        this.mockMvc.perform(
                get("/tagcloud")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.tagCloud[0].word").value("ruim"))
                .andExpect(jsonPath("$.tagCloud[0].occurrences").value(5))
                .andExpect(jsonPath("$.tagCloud[1].word").value("instavel"))
                .andExpect(jsonPath("$.tagCloud[1].occurrences").value(3))
                .andExpect(jsonPath("$.tagCloud[2].word").value("caiu"))
                .andExpect(jsonPath("$.tagCloud[2].occurrences").value(10))
                .andExpect(jsonPath("$.tagCloud[3].word").value("lento"))
                .andExpect(jsonPath("$.tagCloud[3].occurrences").value(40))
                .andExpect(jsonPath("$.tagCloud[4].word").value("bom"))
                .andExpect(jsonPath("$.tagCloud[4].occurrences").value(7))
                .andExpect(jsonPath("$.tagCloud[5].word").value("rapido"))
                .andExpect(jsonPath("$.tagCloud[5].occurrences").value(6))
                .andExpect(status().isOk());
    }


    /**
     * Tests the ability to return a cloud for a given CSP
     * @throws Exception
     */
    @Test
    public void testCspTagCloud() throws Exception {
        when(tagCloudService.requestTagCloud(any(RequestTagCloudEvent.class)))
                .thenReturn(tagCloudCspEvent());

        this.mockMvc.perform(
                post("/tagcloud/domain")
                        .content("{ \"name\" : \"GVT\" , \"domain\" : \"gvt.com.br\" }")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(jsonPath("$.tagCloud[0].word").value("ruim"))
                .andExpect(jsonPath("$.tagCloud[0].occurrences").value(5))
                .andExpect(jsonPath("$.tagCloud[1].word").value("bom"))
                .andExpect(jsonPath("$.tagCloud[1].occurrences").value(7))
                .andExpect(jsonPath("$.tagCloud[2].word").value("rapido"))
                .andExpect(jsonPath("$.tagCloud[2].occurrences").value(6))
                .andExpect(status().isOk());
    }


    /**
     * Tests whether the code throws a 404 if the resource is not found
     * @throws Exception
     */
    @Test
    public void testCspTagCloudUnknownCSP() throws Exception {
        when(tagCloudService.requestTagCloud(any(RequestTagCloudEvent.class)))
                .thenReturn(notFoundCspEvent());


        this.mockMvc.perform(
                post("/tagcloud/domain")
                        .content("{ \"name\" : \"HomeMadeISP\" , \"domain\" : \"hmi.com.br\" }")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}
