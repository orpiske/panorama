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
package net.orpiske.tcs.service.core.events.response;

import net.orpiske.tcs.service.core.domain.TagCloud;
import net.orpiske.tcs.service.core.events.ReadEvent;

public class TagCloudEvent extends ReadEvent {

    private TagCloud cspTagCloud;

    private TagCloudEvent() {

    }

    public TagCloudEvent(TagCloud cspTagCloud) {
        this.cspTagCloud = cspTagCloud;
    }


    public static TagCloudEvent notFound() {
        TagCloudEvent tagCloudEvent = new TagCloudEvent();
        tagCloudEvent.entityFound = false;

        return tagCloudEvent;
    }


    public TagCloud getCspTagCloud() {
        return cspTagCloud;
    }
}
