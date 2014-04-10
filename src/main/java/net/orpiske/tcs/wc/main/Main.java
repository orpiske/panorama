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

import net.orpiske.tcs.wc.map.WordMapper;
import net.orpiske.tcs.wc.reduce.CountReducer;
import net.orpiske.tcs.wc.io.OccurrenceWritable;
import org.apache.cassandra.hadoop.ColumnFamilyInputFormat;
import org.apache.cassandra.hadoop.ConfigHelper;
import org.apache.cassandra.thrift.SlicePredicate;
import org.apache.cassandra.thrift.SliceRange;
import org.apache.cassandra.utils.ByteBufferUtil;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Job;
;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import java.io.IOException;

public class Main extends Configured implements Tool {


    private Job getCSPWordJob(String[] args) throws IOException {
        Job job = new Job(getConf(), "tagcloud");

        job.setJarByClass(Main.class);

        job.setMapperClass(WordMapper.class);
        job.setReducerClass(CountReducer.class);

        job.setOutputKeyClass(OccurrenceWritable.class);
        job.setOutputValueClass(IntWritable.class);

        job.setInputFormatClass(ColumnFamilyInputFormat.class);

        Configuration configuration = job.getConfiguration();

        ConfigHelper.setInputRpcPort(configuration, "9160");
        ConfigHelper.setInputInitialAddress(configuration, "localhost");
        ConfigHelper.setInputPartitioner(configuration,
                "org.apache.cassandra.dht.Murmur3Partitioner");
        ConfigHelper.setInputColumnFamily(configuration, "tcs", "references");

        SlicePredicate predicate = new SlicePredicate().setSlice_range(
                new SliceRange().
                        setStart(ByteBufferUtil.EMPTY_BYTE_BUFFER).
                        setFinish(ByteBufferUtil.EMPTY_BYTE_BUFFER).
                        setCount(100));
        ConfigHelper.setInputSlicePredicate(configuration, predicate);

        job.setOutputFormatClass(TextOutputFormat.class);

        FileOutputFormat.setOutputPath(job, new Path(args[0]));

        return job;
    }

    @Override
    public int run(String[] args) throws Exception {

        try {
            Job job = getCSPWordJob(args);

            job.waitForCompletion(true);

            // org.apache.hadoop.mapreduce.lib.chain.ChainMapper.ad
            //ChainMapper.addMapper();
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
