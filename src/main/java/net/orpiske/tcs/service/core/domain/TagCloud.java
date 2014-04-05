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
package net.orpiske.tcs.service.core.domain;

import java.util.LinkedList;
import java.util.List;


/**
 * A tag cloud
 */
public class TagCloud {
    private String cspName;
    private List<Tag> tagList = new LinkedList<Tag>();


    public TagCloud(final String cspName) {
        this.cspName = cspName;
    }


    public void add(final Tag tag) {
        tagList.add(tag);
    }


    public void add(final String tag, final Integer count) {
        add(new Tag(tag, count));
    }


    public List<Tag> getTagCloud() {
        return tagList;
    }
}
