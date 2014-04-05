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

import net.orpiske.tcs.service.core.domain.Text;
import net.orpiske.tcs.service.core.events.request.RequestCreateReference;
import net.orpiske.tcs.service.core.events.response.ReferenceCreateEvent;
import net.orpiske.tcs.service.utils.LogConfigurator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static net.orpiske.tcs.service.rest.controller.fixtures.RestDataFixtures.customReferenceRequestData;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {CoreConfig.class, MVCConfig.class})
public class RestDomainIntegrationTest {
    @Autowired
    WebApplicationContext webApplicationContext;


    private MockMvc mockMvc;

    private static Text text;
    private static String REQUEST_JSON;

    static {
        LogConfigurator.trace();

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

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    public void addNewReferenceToTheSystem() throws Exception {
        mockMvc.perform(post("/references/")
                .content(REQUEST_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
