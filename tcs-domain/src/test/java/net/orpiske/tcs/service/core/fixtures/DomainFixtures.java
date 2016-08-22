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

package net.orpiske.tcs.service.core.fixtures;

import net.orpiske.tcs.service.core.domain.Tag;

import java.util.ArrayList;
import java.util.List;

public class DomainFixtures {

    public static List<Tag> unsortedTagList() {
        List<Tag> tagList = new ArrayList<Tag>();

        Tag d1Tag1 = (new Tag())
                .setDomain("domain1.com.br")
                .setHash("AAA1")
                .setWord("test1")
                .setOccurrences(5);
        tagList.add(d1Tag1);

        Tag d1Tag2 = (new Tag())
                .setDomain("domain1.com.br")
                .setHash("AAA2")
                .setWord("test2")
                .setOccurrences(5);
        tagList.add(d1Tag2);

        Tag d1Tag3 = (new Tag())
                .setDomain("domain1.com.br")
                .setHash("AAA3")
                .setWord("test3")
                .setOccurrences(6);
        tagList.add(d1Tag3);

        Tag d1Tag4 = (new Tag())
                .setDomain("domain1.com.br")
                .setHash("AAA4")
                .setWord("test4")
                .setOccurrences(3);
        tagList.add(d1Tag4);

        Tag d2Tag1 = (new Tag())
                .setDomain("domain2.com.br")
                .setHash("BBB1")
                .setWord("test1")
                .setOccurrences(2);
        tagList.add(d2Tag1);

        Tag d2Tag2 = (new Tag())
                .setDomain("domain2.com.br")
                .setHash("BBB1")
                .setWord("test2")
                .setOccurrences(8);
        tagList.add(d2Tag2);

        Tag d3Tag1 = (new Tag())
                .setDomain("domain3.com.br")
                .setHash("BBB1")
                .setWord("test1")
                .setOccurrences(11);
        tagList.add(d3Tag1);

        Tag d3Tag2 = (new Tag())
                .setDomain("domain2.com.br")
                .setHash("BBB1")
                .setWord("test8")
                .setOccurrences(2);
        tagList.add(d3Tag2);


        return tagList;
    }
}
