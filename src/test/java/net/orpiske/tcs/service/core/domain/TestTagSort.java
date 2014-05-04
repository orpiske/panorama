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

import net.orpiske.tcs.service.core.fixtures.DomainFixtures;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class TestTagSort {

    private static List<Tag> tagList;

    @Before
    public void setup() {
        tagList = DomainFixtures.unsortedTagList();
        Collections.sort(tagList);
    }

    private void verify(int position, final String expectedDomain,
                        final String expectedWord, int expectedOccurrences)
    {
        Tag tag = tagList.get(position);

        assertEquals("The order is incorrect", expectedWord, tag.getWord());
        assertEquals("The domain is not the one expected", expectedDomain,
                tag.getDomain());
        assertEquals("The occurrence count is incorrect", expectedOccurrences,
                tag.getOccurrences());
    }

    @Test
    public void testComparable() {
        verify(0, "domain1.com.br", "test4", 3);
        verify(1, "domain1.com.br", "test1", 5);
        verify(2, "domain1.com.br", "test2", 5);
        verify(3, "domain1.com.br", "test3", 6);

        verify(4, "domain2.com.br", "test1", 2);
        verify(5, "domain2.com.br", "test8", 2);
        verify(6, "domain2.com.br", "test2", 8);
        verify(7, "domain3.com.br", "test1", 11);
    }
}
