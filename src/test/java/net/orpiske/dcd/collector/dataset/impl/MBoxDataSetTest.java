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

package net.orpiske.dcd.collector.dataset.impl;

import net.orpiske.dcd.collector.dataset.Data;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class MBoxDataSetTest {

    private MBoxDataSet dataSet;
    private List<Data> dataList = new ArrayList<Data>();

    @Before
    public void setup() throws Exception {
        String path = getClass().getResource("FakeMail.txt").getFile();
        File file = new File(path);

        dataSet = new MBoxDataSet(file);
        while (dataSet.hasNext()) {
            Data data = dataSet.next();
            dataList.add(data);
        }
    }


    @Test
    public void testCount() {
        int count = dataList.size();

        assertEquals("The expected message count is incorrect", 2, count);
    }


    @Test
    public void testDate() {
        Data data = dataList.get(0);

        Date date = data.getDate();

        assertEquals("The year is incorrect", 2013, date.getYear());
        assertEquals("The month is incorrect", 5, date.getMonth());
        assertEquals("The day is incorrect", 1, date.getDay());
    }
}
