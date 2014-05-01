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

package net.orpiske.tcs.client.base;

import java.net.MalformedURLException;
import java.net.URL;

public class DefaultEndPoint implements EndPoint {
    private String location;
    private static final String ROOT = "/tcs";

    public DefaultEndPoint(final String location) throws MalformedURLException {
        this.location = location;
    }

    @Override
    public String getLocation() {
        return location;
    }

    @Override
    public String getTagCloudServiceLocation() {
        return location + ROOT + "/tagcloud";
    }

    @Override
    public String getTagCloudServiceLocationForDomain() {
        return location + ROOT + "/tagcloud/domain";
    }

    @Override
    public String getDomainListLocation() {
        return location + ROOT + "/domain";
    }
}
