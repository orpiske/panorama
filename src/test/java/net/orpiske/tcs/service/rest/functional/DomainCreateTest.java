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

package net.orpiske.tcs.service.rest.functional;

import net.orpiske.tcs.service.core.domain.Domain;
import net.orpiske.tcs.service.rest.controller.fixtures.RestDataFixtures;
import org.apache.commons.codec.binary.Base64;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.Assert.*;

import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Ignore
public class DomainCreateTest {

    private static final String USERNAME = "admin";
    private static final String GOOD_PASSWORD = "admin";
    private static final String BAD_PASSWORD = "invalid";

    private HttpHeaders getHeaders(String auth) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        byte[] encodedAuthorisation = Base64.encodeBase64(auth.getBytes());
        headers.add("Authorization", "Basic " + new String(encodedAuthorisation));

        return headers;
    }

    @Test
    public void testDomainCreation() {
        HttpEntity<Domain> requestEntity = new HttpEntity<Domain>(
                RestDataFixtures.customCsp(),
                getHeaders(USERNAME + ":" + GOOD_PASSWORD));

        RestTemplate template = new RestTemplate();
        ResponseEntity<Domain> responseEntity = template.postForEntity(
                "http://localhost:8080/tcs/domain/terra.com.br", requestEntity, Domain.class);

        String path = responseEntity.getHeaders().getLocation().getPath();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertTrue("The returned path (" + path + ") does not match the expected one",
                path.startsWith("/tcs/domain/terra.com.br"));
    }


    @Test
    public void testAuthenticationError() {
        HttpEntity<Domain> requestEntity = new HttpEntity<Domain>(
                RestDataFixtures.customCsp(),
                getHeaders(USERNAME + ":" + BAD_PASSWORD));

        RestTemplate template = new RestTemplate();

        try {
            ResponseEntity<Domain> responseEntity = template.postForEntity(
                    "http://localhost:8080/tcs/domain/terra.com.br", requestEntity, Domain.class);

            fail("Request Passed incorrectly with status " +
                    responseEntity.getStatusCode());
        }
        catch (HttpClientErrorException e) {
            assertEquals(HttpStatus.UNAUTHORIZED, e.getStatusCode());
        }
    }
}
