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
package net.orpiske.tcs.service.rest.controller.fixtures;

import net.orpiske.tcs.service.core.events.response.CspCreateEvent;
import net.orpiske.tcs.service.core.events.response.CspListEvent;
import net.orpiske.tcs.service.core.events.response.ReferenceCreateEvent;
import net.orpiske.tcs.service.core.events.response.TagCloudEvent;

import static net.orpiske.tcs.service.rest.controller.fixtures.RestDataFixtures.*;

public class RestEventFixtures {

    public static CspListEvent emptyCspListEvent() {
        return new CspListEvent();
    }

    public static CspListEvent cspListEvent() {
        System.out.println("Creating a custom CSP list");

        return new CspListEvent(customCspList());
    }

    public static TagCloudEvent notFoundCspEvent() {
        return TagCloudEvent.notFound();
    }

    public static TagCloudEvent tagCloudEvent() {
        return new TagCloudEvent(customCspTagCloud());
    }

    public static ReferenceCreateEvent tagCloudUpdateEvent() {
        return new ReferenceCreateEvent();
    }

    public static TagCloudEvent tagCloudCspEvent() {
        return new TagCloudEvent(customSmallCspTagCloud());
    }

    public static CspCreateEvent cspCreateEvent() {
        return new CspCreateEvent(customCsp());
    }
}
