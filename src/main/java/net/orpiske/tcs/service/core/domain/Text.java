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
package net.orpiske.tcs.service.core.domain;

import net.orpiske.tcs.utils.compression.Compressor;
import net.orpiske.tcs.utils.compression.Decompressor;
import org.apache.commons.codec.binary.Base64;

import java.io.IOException;

public class Text {
    private String encodedText;

    public String getEncodedText() {
        return encodedText;
    }

    public void setEncodedText(String encodedText) {
        this.encodedText = encodedText;
    }


    /**
     * Creates a new Text object out of a regular string. The data will be
     * compressed
     * @param string
     * @return
     */
    public static Text fromString(final String string) throws IOException {
        byte[] compressedBytes = Compressor.compress(string);
        String encoded = Base64.encodeBase64String(compressedBytes);

        Text text = new Text();
        text.setEncodedText(encoded);

        return text;
    }


    public String getDecompressedText() throws IOException {
        String encodedText = getEncodedText();
        byte[] encodedBytes = Base64.decodeBase64(encodedText);

        return Decompressor.decompress(encodedBytes);
    }
}
