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
package net.orpiske.tcs.wc.reduce;

import net.orpiske.tcs.wc.io.OccurrenceWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.Iterator;

/**
 * This reducer saves the output to the FS. It's available for testing purposes only
 */
@Deprecated
public class CountReducerFile extends Reducer<OccurrenceWritable, IntWritable, OccurrenceWritable, IntWritable> {
    private static final Logger logger = Logger.getLogger(CountReducerFile.class);


    @Override
    protected void reduce(OccurrenceWritable key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
        int sum = 0;

        Iterator<IntWritable> it = values.iterator();
        while (it.hasNext()) {
            sum += it.next().get();

            String word = key.getText();

            if (logger.isTraceEnabled()) {
                logger.trace("Total occurrences of " + word + " for CSP " + key + " so far: " + sum);
            }
        }

        logger.info("Iterated over " + sum + " records for domain '" + key.toString() + "");

        context.write(key, new IntWritable(sum));
    }
}
