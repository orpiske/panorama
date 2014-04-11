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

import com.google.common.base.CharMatcher;
import net.orpiske.tcs.wc.io.OccurrenceWritable;
import org.apache.cassandra.thrift.ColumnOrSuperColumn;
import org.apache.cassandra.thrift.Mutation;
import org.apache.cassandra.utils.ByteBufferUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

/**
 * This reducer dumps the result to a table on Cassandra
 */
public class CountReducerTable extends Reducer<OccurrenceWritable, IntWritable, ByteBuffer,
        ArrayList<Mutation>> {
    private static final Logger logger = Logger.getLogger(CountReducerTable.class);


    /**
     * Gets a mutation ...
     * @param name The name of the table
     * @param time A long that represents an epoch time
     * @return A mutation object
     */
    private static Mutation getMutation(String name, long time) {
        org.apache.cassandra.thrift.Column c = new org.apache.cassandra.thrift.Column();

        c.setName(ByteBufferUtil.bytes(name));
        c.setValue(ByteBufferUtil.bytes(time));
        c.setTimestamp(System.currentTimeMillis());

        Mutation m = new Mutation();

        m.setColumn_or_supercolumn(new ColumnOrSuperColumn());
        m.column_or_supercolumn.setColumn(c);

        return m;
    }


    /**
     * Gets a mutation ...
     * @param name The name of the table
     * @param sum The sum of occurrences
     * @return A mutation object
     */
    private static Mutation getMutation(String name, int sum) {
        org.apache.cassandra.thrift.Column c = new org.apache.cassandra.thrift.Column();

        c.setName(ByteBufferUtil.bytes(name));
        c.setValue(ByteBufferUtil.bytes(sum));
        c.setTimestamp(System.currentTimeMillis());

        Mutation m = new Mutation();

        m.setColumn_or_supercolumn(new ColumnOrSuperColumn());
        m.column_or_supercolumn.setColumn(c);

        return m;
    }


    /**
     * Gets a mutation ...
     * @param name The name of the table
     * @param obj A string object
     * @return A mutation object
     */
    private static Mutation getMutation(String name, String obj) {
        org.apache.cassandra.thrift.Column c = new org.apache.cassandra.thrift.Column();

        // We really, really need to filter this, otherwise we save the
        // data with lots of invisible chars in the DB
        CharMatcher legalChars = CharMatcher.INVISIBLE;
        String filtered = legalChars.removeFrom(obj);

        c.setName(ByteBufferUtil.bytes(name));
        c.setValue(ByteBufferUtil.bytes(filtered));
        c.setTimestamp(System.currentTimeMillis());

        Mutation m = new Mutation();

        m.setColumn_or_supercolumn(new ColumnOrSuperColumn());
        m.column_or_supercolumn.setColumn(c);

        return m;
    }

    @Override
    protected void reduce(OccurrenceWritable key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
        int sum = 0;

        // First, iterate over the KV to calculate the count of references
        Iterator<IntWritable> it = values.iterator();
        while (it.hasNext()) {
            sum += it.next().get();

            String word = key.getText();

            if (logger.isTraceEnabled()) {
                logger.trace("Total occurrences of " + word + " for CSP " + key + " so far: " + sum);
            }
        }

        logger.info("Iterated over " + sum + " records for domain '" + key.toString() + "'");

        // Then, create the mutation objects so that we can save then on the DB
        Mutation domain = getMutation("domain", key.getDomain());
        Mutation word = getMutation("word", key.getText());
        Mutation occurrences = getMutation("occurrences", sum);
        Mutation date = getMutation("reference_date", (new Date()).getTime());


        // ... and put them in an array
        ArrayList<Mutation> list = new ArrayList<Mutation>();
        list.add(domain);
        list.add(word);
        list.add(occurrences);
        list.add(date);

        // ... since we cannot use composite keys very easily, calculates the
        // a SHA-1 hash to serve as the index. At the moment there's no need to
        // be fancy here, so we use a simple SHA-1 hash (index)
        String hashKey = DigestUtils.shaHex(key.getDomain() + key.getText());
        ByteBuffer b = ByteBufferUtil.bytes(hashKey);

        // ... and, finally, put it in the context
        context.write(b, list);
    }
}
