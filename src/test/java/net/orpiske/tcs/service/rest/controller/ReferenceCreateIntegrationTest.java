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

import net.orpiske.tcs.service.core.domain.Text;
import net.orpiske.tcs.service.core.events.request.RequestCreateReference;
import net.orpiske.tcs.service.core.service.TagCloudService;
import net.orpiske.tcs.service.utils.LogConfigurator;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;

import static net.orpiske.tcs.service.rest.controller.fixtures.RestEventFixtures.tagCloudUpdateEvent;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ReferenceCreateIntegrationTest {
    private static final Logger logger = Logger.getLogger(ReferenceCreateIntegrationTest.class);

    MockMvc mockMvc;

    @InjectMocks
    ReferencesCommandsController tagCloudCommandsController;

    @Mock
    TagCloudService tagCloudService;


    private static Text text;
    private static String REQUEST_JSON;

    static {
        try {
            text = Text.fromString("this is a sample string");
        }
        catch (Exception e) {
            fail("Unable to create a Text object: " + e.getMessage());
        }

        REQUEST_JSON = "{ \"csp\" : { \"name\" : \"HomeMadeISP\"," +
                "\"domain\" : \"www.hmi.com.br\" }, \"text\" : { \"encodedText\" : \""
                + text.getEncodedText() + "\"} }";
    }

    /**
     * Initialization / setup
     */
    @Before
    public void setup() {
        LogConfigurator.trace();
        MockitoAnnotations.initMocks(this);

        this.mockMvc = standaloneSetup(tagCloudCommandsController,
                new MappingJackson2HttpMessageConverter())
                .build();
    }


    /**
     * Tests the ability to return a cloud for all CSPs
     * @throws Exception
     */
    @Test
    public void testTagCloud() throws Exception {
        when(tagCloudService.createReference(
                any(RequestCreateReference.class)))
                .thenReturn(tagCloudUpdateEvent());

        logger.debug("Sending JSON = " + REQUEST_JSON);

        mockMvc.perform(post("/references/")
                    .content(REQUEST_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
