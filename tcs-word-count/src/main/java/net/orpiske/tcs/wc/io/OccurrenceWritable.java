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
package net.orpiske.tcs.wc.io;


import com.google.common.base.Splitter;
import com.google.common.collect.ComparisonChain;
import org.apache.hadoop.io.WritableComparable;
import org.apache.log4j.Logger;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Iterator;


/**
 * A pretty boring composite key for the mapper
 */
public class OccurrenceWritable implements WritableComparable {
    private static final Logger logger = Logger.getLogger(OccurrenceWritable.class);

    private String domain;
    private String text;

    public OccurrenceWritable() {

    }

    public OccurrenceWritable(String domain, String text) {
        this.domain = domain;
        this.text = text;
    }


    public String getText() {
        return text;
    }

    public String getDomain() {
        return domain;
    }

    @Override
    public void write(DataOutput dataOutput) throws IOException {
        dataOutput.writeChars(domain);
        // ... without this, it saves the text as domaintext, which
        // makes it impossible to parse o.O
        dataOutput.writeChars(" ");
        dataOutput.writeChars(text);
    }

    @Override
    public void readFields(DataInput dataInput) throws IOException {
        String line = dataInput.readLine();

        Iterable<String> iterable = Splitter.on(" ").split(line);
        Iterator<String> it = iterable.iterator();

        domain = it.next();
        text = it.next();

    }

    @Override
    public int compareTo(Object o) {
        if (o == null) {
            return 1;
        }

        return compareTo((OccurrenceWritable) o);
    }

    private int compareTo(OccurrenceWritable o) {
        if (logger.isTraceEnabled()) {

            if (o == null) {
                logger.trace("Other object is null. Printing this only: " + toString());
            }
            else {
                logger.trace("This data: " + toString());
                logger.trace("Other data: " + o.toString());
            }
        }

        return ComparisonChain.start().compare(text, o.text)
                .compare(domain, o.domain)
                .result();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OccurrenceWritable that = (OccurrenceWritable) o;

        if (domain != null ? !domain.equals(that.domain) : that.domain != null) return false;
        if (text != null ? !text.equals(that.text) : that.text != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = text != null ? text.hashCode() : 0;
        result = 31 * result + (domain != null ? domain.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return domain + " " + text;
    }
}
