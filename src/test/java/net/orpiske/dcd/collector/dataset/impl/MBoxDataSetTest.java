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
import org.apache.commons.lang3.time.DateUtils;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

import java.io.File;
import java.util.*;

public class MBoxDataSetTest {

    private MBoxDataSet dataSet;
    private static List<Data> dataList = null;

    @Before
    public void setup() throws Exception {
        if (dataList == null) {
            dataList = new ArrayList<Data>();

            String path = getClass().getResource("FakeMail.txt").getFile();
            File file = new File(path);

            dataSet = new MBoxDataSet(file);
            while (dataSet.hasNext()) {
                Data data = dataSet.next();
                dataList.add(data);
            }
        }
    }


    @Test
    public void testCount() {
        int count = dataList.size();

        assertEquals("The expected message count is incorrect", 5, count);
    }


    // Date: Wed, 2 May 2013 01:12:02 -0300 (BRT)
    @Test
    public void testOneDigitDateWithTZ() {
        Data data = dataList.get(0);

        Date date = data.getDate();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        assertEquals("The year is incorrect", 2013, cal.get(Calendar.YEAR));
        assertEquals("The month is incorrect", Calendar.MAY, cal.get(Calendar.MONTH));
        assertEquals("The day is incorrect", 2, cal.get(Calendar.DAY_OF_MONTH));
    }

    // Date: Wed, 20 May 2013 01:20:02 -0300 (BRT)
    @Test
    public void testTwoDigitDateWithTZ() {
        Data data = dataList.get(1);

        Date date = data.getDate();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        assertEquals("The year is incorrect", 2013, cal.get(Calendar.YEAR));
        assertEquals("The month is incorrect", Calendar.MAY, cal.get(Calendar.MONTH));
        assertEquals("The day is incorrect", 20, cal.get(Calendar.DAY_OF_MONTH));
    }


    // Date: Wed, 1 May 2013 01:12:02 -0300
    @Test
    public void testOneDigitDateWithoutTZ() {
        Data data = dataList.get(2);

        Date date = data.getDate();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        assertEquals("The year is incorrect", 2013, cal.get(Calendar.YEAR));
        assertEquals("The month is incorrect", Calendar.MAY, cal.get(Calendar.MONTH));
        assertEquals("The day is incorrect", 1, cal.get(Calendar.DAY_OF_MONTH));
    }

    // Date: Wed, 10 May 2013 01:12:02 -0300
    @Test
    public void testTwoDigitDateWithoutTZ() {
        Data data = dataList.get(3);

        Date date = data.getDate();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        assertEquals("The year is incorrect", 2013, cal.get(Calendar.YEAR));
        assertEquals("The month is incorrect", Calendar.MAY, cal.get(Calendar.MONTH));
        assertEquals("The day is incorrect", 10, cal.get(Calendar.DAY_OF_MONTH));
    }
}
