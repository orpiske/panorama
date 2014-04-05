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

import java.io.IOException;

public class RestDataFixtures {

    public static CspList customCspList() {
        CspList cspList = new CspList();

        cspList.add("GVT", "www.gvt.com.br");
        cspList.add("NET", "www.netvirtua.com.br");
        cspList.add("Oi", "www.oi.com.br");

        return cspList;
    }

    public static TagCloud customCspTagCloud() {
        TagCloud cspTagCloud = new TagCloud("CSP");

        cspTagCloud.add("ruim", 5);
        cspTagCloud.add("instavel", 3);
        cspTagCloud.add("caiu", 10);
        cspTagCloud.add("lento", 40);
        cspTagCloud.add("bom", 7);
        cspTagCloud.add("rapido", 6);

        return cspTagCloud;
    }


    public static TagCloud customSmallCspTagCloud() {
        TagCloud cspTagCloud = new TagCloud("CSP");

        cspTagCloud.add("ruim", 5);
        cspTagCloud.add("bom", 7);
        cspTagCloud.add("rapido", 6);

        return cspTagCloud;
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

            ReferenceCreateData data = new ReferenceCreateData();

            data.setCsp(csp);
            data.setText(text);

            return data;

        }
        catch (Exception e) {
            e.printStackTrace();

            return null;
        }
    }
}
