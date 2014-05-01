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

import com.google.common.base.CharMatcher;
import net.orpiske.tcs.client.base.EndPoint;
import net.orpiske.tcs.client.base.ServiceClient;
import net.orpiske.tcs.service.core.domain.Domain;
import net.orpiske.tcs.service.core.domain.DomainList;
import net.orpiske.tcs.service.core.domain.Tag;
import net.orpiske.tcs.service.core.domain.TagCloud;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class TagCloudServiceClient implements ServiceClient {

    private EndPoint endPoint;
    private RestTemplate restTemplate;

    public TagCloudServiceClient(EndPoint endPoint) {
        this.endPoint = endPoint;

        restTemplate = new RestTemplate();

        List<HttpMessageConverter<?>> objects = restTemplate.getMessageConverters();
        objects.add(new MappingJackson2HttpMessageConverter());
        restTemplate.setMessageConverters(objects);
    }

    @Override
    public TagCloud requestTagCloud() {
        TagCloud tagCloud = restTemplate.getForObject(endPoint.getTagCloudServiceLocation(),
                TagCloud.class);

        return tagCloud;
    }


    @Override
    public TagCloud requestTagCloud(final Domain domain) {
        System.out.println("Trying to request data for domain " + domain.getDomain());
        System.out.println(" with name " + domain.getName());
        System.out.println(" at location: "
                + endPoint.getTagCloudServiceLocationForDomain());


        TagCloud tagCloud = restTemplate.postForObject(
                    endPoint.getTagCloudServiceLocationForDomain(),
                domain,
                TagCloud.class);

        return tagCloud;
    }

    @Override
    public DomainList requestDomainList() {
        return restTemplate.getForObject(endPoint.getDomainListLocation(),
                DomainList.class);
    }
}
