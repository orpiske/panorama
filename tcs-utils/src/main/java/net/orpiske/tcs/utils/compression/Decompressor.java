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
package net.orpiske.tcs.utils.compression;

import org.apache.commons.io.IOUtils;

import java.io.*;
import java.nio.charset.Charset;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * Decompressor
 */
public class Decompressor {

    /**
     * Decompress an array of bytes
     * @param bytes the array to decompress
     * @return a String object with the text
     * @throws IOException if unable to decompress it for any reason
     */
    public static String decompress(final byte[] bytes) throws IOException {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
        InputStream gzipInputStream = new GZIPInputStream(inputStream);

        /**
         * Ok, this should be "smarter". Will fix this, eventually ...
         */
        Reader reader = new InputStreamReader(gzipInputStream,
                Charset.forName("UTF-8"));

        BufferedReader bufferedReader = new BufferedReader(reader);
        StringBuilder builder = new StringBuilder();

        try {
            char[] buffer = new char[1];

            while (bufferedReader.read(buffer) > 0) {
                builder.append(buffer);
            }

            return builder.toString();
        }
        finally {
            IOUtils.closeQuietly(gzipInputStream);
            IOUtils.closeQuietly(inputStream);
        }
    }
}
