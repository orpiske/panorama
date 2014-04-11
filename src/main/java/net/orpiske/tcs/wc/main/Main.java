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
package net.orpiske.tcs.wc.main;

import net.orpiske.tcs.wc.io.OccurrenceWritable;
import net.orpiske.tcs.wc.map.WordMapper;
import net.orpiske.tcs.wc.reduce.CountReducerTable;
import org.apache.cassandra.hadoop.ColumnFamilyInputFormat;
import org.apache.cassandra.hadoop.ColumnFamilyOutputFormat;
import org.apache.cassandra.hadoop.ConfigHelper;
import org.apache.cassandra.hadoop.cql3.CqlConfigHelper;
import org.apache.cassandra.thrift.SlicePredicate;
import org.apache.cassandra.utils.ByteBufferUtil;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

;

public class Main extends Configured implements Tool {

    /**
     * Setup the M/R job to read from the references table from Cassandra
     * @param configuration
     */
    private void inputConfiguration(Configuration configuration) {
        ConfigHelper.setInputRpcPort(configuration, "9160");
        ConfigHelper.setInputInitialAddress(configuration, "localhost");
        ConfigHelper.setInputPartitioner(configuration,
                "org.apache.cassandra.dht.Murmur3Partitioner");

        ConfigHelper.setInputColumnFamily(configuration, "tcs", "references");

        List<ByteBuffer> columns = Arrays.asList(
                ByteBufferUtil.bytes("reference_text"),
                ByteBufferUtil.bytes("domain"));

        SlicePredicate predicate = new SlicePredicate()
                .setColumn_names(columns);

        ConfigHelper.setInputSlicePredicate(configuration, predicate);
    }

    /**
     * Setup the output to dump the M/R result to word_cloud table on
     * Cassandra
     * @param configuration
     */
    private void outputConfiguration(Configuration configuration) {
        ConfigHelper.setOutputInitialAddress(configuration, "localhost");
        ConfigHelper.setOutputColumnFamily(configuration, "tcs", "word_cloud");
        ConfigHelper.setOutputPartitioner(configuration,
                "org.apache.cassandra.dht.Murmur3Partitioner");

        String query = "UPDATE tcs.word_cloud SET hash = ?, domain = ?, word = ?, occurrences = ?, reference_date = ? ";
        // String query = " insert into tcs.word_cloud(hash,domain,word,occurrences,reference_date) values(?, ?, ?, ?, ?)";
        CqlConfigHelper.setOutputCql(configuration, query);
    }


    private Job getCSPWordJob(String[] args) throws IOException {
        Job job = new Job(getConf(), "tagcloud");

        job.setJarByClass(Main.class);

        /**
         * This is related to what comes _out_ of the map process
         */
        job.setMapperClass(WordMapper.class);
        job.setReducerClass(CountReducerTable.class);

        job.setInputFormatClass(ColumnFamilyInputFormat.class);

        job.setMapOutputKeyClass(OccurrenceWritable.class);
        job.setMapOutputValueClass(IntWritable.class);

        job.setOutputKeyClass(Map.class);
        job.setOutputValueClass(ArrayList.class);

        job.setOutputFormatClass(ColumnFamilyOutputFormat.class);

        Configuration configuration = job.getConfiguration();

        inputConfiguration(configuration);
        outputConfiguration(configuration);

        return job;
    }

    @Override
    public int run(String[] args) throws Exception {

        try {
            Job job = getCSPWordJob(args);

            job.waitForCompletion(true);

            return 0;
        }
        catch (IOException e) {
            System.err.println("Unable to run job due to input/output error: " + e.getMessage());
            e.printStackTrace();
            return 1;
        }
        catch (Throwable t) {
            System.err.println("Unable to run job due to an unhandled error: " + t.getMessage());

            if (t.getCause() != null) {
                System.err.println("Cause: " + t.getCause().getMessage());
                t.getCause().printStackTrace();
            }
            t.printStackTrace();
            return 1;
        }
    }

    public static void main(String[] args) {
        try {
            ToolRunner.run(new Configuration(), new Main(), args);
            System.exit(0);
        }
        catch (Exception e) {
            System.err.println("Unable to run job: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }

    }
}
