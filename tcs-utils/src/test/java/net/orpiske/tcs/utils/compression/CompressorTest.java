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


import org.apache.commons.codec.binary.BinaryCodec;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static org.apache.commons.codec.binary.Hex.encodeHexString;

import java.io.IOException;

public class CompressorTest {
    private static final String text = "hello";
    private static final String encodedCompressedHex =
            "1f8b0800000000000000cb48cdc9c9070086a6103605000000";

    @Test
    public void testCompression() throws IOException {
        byte[] compressed = Compressor.compress(text);

        String str = encodeHexString(compressed);

        assertEquals(encodedCompressedHex, str);
    }
}
