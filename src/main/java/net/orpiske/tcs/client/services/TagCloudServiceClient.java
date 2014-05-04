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

package net.orpiske.tcs.client.services;

import net.orpiske.tcs.client.base.EndPoint;
import net.orpiske.tcs.client.base.ServiceClient;
import net.orpiske.tcs.client.exceptions.InvalidCredentialsException;
import net.orpiske.tcs.service.core.domain.Domain;
import net.orpiske.tcs.service.core.domain.DomainList;
import net.orpiske.tcs.service.core.domain.TagCloud;
import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

// import org.apache.http.client.HttpClient;

public class TagCloudServiceClient implements ServiceClient {
    private static final Logger logger = Logger.getLogger(TagCloudServiceClient.class);

    private EndPoint endPoint;
    private RestTemplate restTemplate;

    public TagCloudServiceClient(EndPoint endPoint) {
        this.endPoint = endPoint;

        restTemplate = new RestTemplate();

        List<HttpMessageConverter<?>> objects = restTemplate.getMessageConverters();
        objects.add(new MappingJackson2HttpMessageConverter());

        restTemplate.setMessageConverters(objects);
    }


    private HttpHeaders getHeaders() {
        String user = System.getenv("TCS_USER");
        String password = System.getenv("TCS_PASSWORD");

        if (user == null || user.isEmpty()) {
            logger.fatal("The backend system username is not provided (please set " +
                    "the TCS_USER environment variable)");

            throw new InvalidCredentialsException("The backend system username is not provided");
        }

        if (password == null || password.isEmpty()) {
            logger.fatal("The backend system password is not provided (please set " +
                    "the TCS_PASSWORD environment variable)");

            throw new InvalidCredentialsException("The backend system password is not provided");
        }

        String auth = user + ":" + password;

        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        byte[] encodedAuth = Base64.encodeBase64(auth.getBytes());
        headers.add("Authorization", "Basic " + new String(encodedAuth));

        return headers;
    }


    @Override
    public TagCloud requestTagCloud() {

        ResponseEntity<TagCloud> responseEntity =
                restTemplate.exchange(
                        endPoint.getTagCloudServiceLocation(),
                        HttpMethod.GET,
                        new HttpEntity<Object>(getHeaders()),
                        TagCloud.class);

        TagCloud tagCloud = responseEntity.getBody();

        return tagCloud;
    }


    @Override
    public TagCloud requestTagCloud(final Domain domain) {
        if (logger.isDebugEnabled()) {
            logger.debug("Trying to request data for domain " + domain.getDomain());
            logger.debug(" with name " + domain.getName());
            logger.debug(" at location: "
                    + endPoint.getTagCloudServiceLocationForDomain());
        }


        return restTemplate.postForObject(
                    endPoint.getTagCloudServiceLocationForDomain(),
                domain,
                TagCloud.class);
    }

    @Override
    public DomainList requestDomainList() {
        ResponseEntity<DomainList> responseEntity =
                restTemplate.exchange(
                        endPoint.getDomainListLocation(),
                        HttpMethod.GET,
                        new HttpEntity<Object>(getHeaders()),
                        DomainList.class);

        return responseEntity.getBody();
    }
}
