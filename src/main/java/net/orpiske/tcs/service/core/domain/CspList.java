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

import java.util.ArrayList;
import java.util.List;

/**
 * Abstracts a list of CSPs
 */
public class CspList {

    private List<Csp> cspList;

    public CspList() {
        cspList = new ArrayList<Csp>();
    }


    public CspList(List<Csp> list) {
        this.cspList = new ArrayList<Csp>(list);
    }

    public boolean add(String s, String domain) {
        return cspList.add(new Csp(s, domain));
    }

    public Csp get(int index) {
        return cspList.get(index);
    }

    public List<Csp> getCspList() {
        return cspList;
    }
}
