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

import net.orpiske.tcs.client.base.DefaultEndPoint;
import net.orpiske.tcs.service.core.domain.Domain;
import net.orpiske.tcs.service.core.domain.DomainList;
import net.orpiske.tcs.service.core.domain.Tag;
import net.orpiske.tcs.service.core.domain.TagCloud;
import org.junit.Before;
import org.junit.Test;

import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;

import static org.junit.Assert.*;

public class TagCloudIntegrationTest {


    private TagCloudServiceClient client;

    @Before
    public void setup() throws Exception {
        DefaultEndPoint defaultEndPoint = new DefaultEndPoint("http://localhost:8080");

        client = new TagCloudServiceClient(defaultEndPoint);
    }

    @Test
    public void testService() {
        TagCloud tc = client.requestTagCloud();
        assertNotNull(tc);
    }


    @Test
    public void testServiceForDomain() {
        TagCloud tc = client.requestTagCloud(new Domain().setDomain("gvt.com.br").setName("GVT"));

        assertNotNull(tc);
        for (Tag tag : tc.getTagCloud()) {
            System.out.println("Tag for domain: " + tag.getDomain() +
                    "/word " + tag.getWord());
        }
    }


    @Test
    public void testDomainList() {
        DomainList domainList = client.requestDomainList();

        assertNotNull(domainList);
        for (Domain domain : domainList.getDomainList()) {
            System.out.println("Domain: " + domain.getDomain() + "/name "
                    + domain.getName());
        }
    }


    @Test
    public void testAllDomainsList() {
        DomainList domainList = client.requestDomainList();

        assertNotNull(domainList);
        for (Domain domain : domainList.getDomainList()) {
            System.out.println("1. Domain: " + domain.getDomain() + "/name "
                    + domain.getName());

            TagCloud tc = client.requestTagCloud(domain);

            for (Tag tag : tc.getTagCloud()) {
                System.out.println("2. Tag for domain: " + tag.getDomain() +
                        "/word " + tag.getWord());
            }


        }
    }
}
