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
package net.orpiske.tcs.wc.map;

import net.orpiske.sfs.filter.dictionary.DictionaryFilter;
import net.orpiske.sfs.filter.runner.DefaultFilterRunner;
import net.orpiske.sfs.filter.runner.FilterRunner;
import net.orpiske.sfs.filter.simple.CharacterFilter;
import net.orpiske.sfs.filter.Filter;
import net.orpiske.sfs.filter.simple.StringSizeFilter;
import net.orpiske.tcs.wc.io.OccurrenceWritable;
import org.apache.cassandra.db.Column;
import org.apache.cassandra.utils.ByteBufferUtil;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.SortedMap;
import java.util.StringTokenizer;

public class WordMapper extends Mapper<ByteBuffer, SortedMap<ByteBuffer, Column>, OccurrenceWritable, IntWritable> {
    private static final Logger logger = Logger.getLogger(WordMapper.class);

    private final static IntWritable one = new IntWritable(1);
    private Text word = new Text();
	private static final FilterRunner runner;

	static {
		ArrayList<Filter> filters = new ArrayList<Filter>();

		filters.add(new CharacterFilter());
		filters.add(new StringSizeFilter(2));
		filters.add(new DictionaryFilter());

		runner = new DefaultFilterRunner(filters);
	}

	public WordMapper() {
		super();
	}

    private String getColumnValue(SortedMap<ByteBuffer, Column> columns, final String name) throws IOException, InterruptedException  {
        ByteBuffer byteBuffer = Text.encode(name);

        Column column = columns.get(byteBuffer);

        String ret = ByteBufferUtil.string(column.value());

        return ret;
    }

	public void map(ByteBuffer key, SortedMap<ByteBuffer, Column> columns, Context context) throws IOException, InterruptedException {
        String referenceText = getColumnValue(columns, "reference_text");

        if (logger.isDebugEnabled()) {
            String keyText = ByteBufferUtil.string(key);

            logger.debug("read " + keyText + "->reference_text from cassandra");
        }

        String domainText = getColumnValue(columns, "domain");

        StringTokenizer tokenizer = new StringTokenizer(referenceText);
        while (tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken();
			String word = runner.run(token);

			if (!word.isEmpty()) {
                OccurrenceWritable occurrenceWritable =
                        new OccurrenceWritable(domainText, word);

				context.write(occurrenceWritable, one);
			}
        }
    }

}
