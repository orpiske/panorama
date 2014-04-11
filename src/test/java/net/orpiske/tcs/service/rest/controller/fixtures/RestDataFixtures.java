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
package net.orpiske.tcs.service.rest.controller.fixtures;

import net.orpiske.tcs.service.core.domain.*;
import net.orpiske.tcs.utils.compression.Compressor;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class RestDataFixtures {

    public static CspList customCspList() {
        CspList cspList = new CspList();

        cspList.add("GVT", "www.gvt.com.br");
        cspList.add("NET", "www.netvirtua.com.br");
        cspList.add("Oi", "www.oi.com.br");

        return cspList;
    }

	private static Tag tag(final String csp, final String word) {
		Tag tag = new Tag()
				.setDomain(csp)
				.setHash(DigestUtils.sha1Hex(word))
				.setWord(word);

		return tag;
	}



    public static TagCloud customCspTagCloud() {
		final String domain = "HomeMadeISP";

		Tag ruim = tag(domain, "ruim")
					.setOccurrences(5);


		Tag instavel = tag(domain, "instavel")
					.setOccurrences(3);

		Tag caiu = tag(domain, "caiu")
				.setOccurrences(10);

		Tag lento = tag(domain, "lento")
				.setOccurrences(40);

		Tag bom = tag(domain, "bom")
				.setOccurrences(7);

		Tag rapido = tag(domain, "rapido")
				.setOccurrences(6);

		List<Tag> list = Arrays.asList(ruim, instavel, caiu, lento, bom, rapido);

       	return new TagCloud(list);
    }


    public static TagCloud customSmallCspTagCloud() {
		final String domain = "HomeMadeISP";

		Tag ruim = tag(domain, "ruim")
				.setOccurrences(5);

		Tag bom = tag(domain, "bom")
				.setOccurrences(7);

		Tag rapido = tag(domain, "rapido")
				.setOccurrences(6);

		List<Tag> list = Arrays.asList(ruim, bom, rapido);

        return new TagCloud(list);
    }


    public static Csp customCsp() {
        return new Csp("Terra", "www.terra.com.br");
    }

    public static Text customText() {
        String message = "... And I will go on ahead, free; "
            + "There's a light yet to be found ...";

        try {
            return Text.fromString(message);
        }
        catch (Exception e) {
            e.printStackTrace();

            return null;
        }
    }


    public static ReferenceCreateData customReferenceRequestData() {
       try {
            Text text = Text.fromString("Bad, bad ISP");

            Csp csp = new Csp("HomeMadeCSP", "www.home.com.br");

            ReferenceCreateData data = new ReferenceCreateData()
						.setCsp(csp)
						.setText(text);

            return data;

        }
        catch (Exception e) {
            e.printStackTrace();

            return null;
        }
    }
}
