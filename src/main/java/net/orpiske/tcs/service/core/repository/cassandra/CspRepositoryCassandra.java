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
package net.orpiske.tcs.service.core.repository.cassandra;

import net.orpiske.tcs.service.core.domain.Csp;
import net.orpiske.tcs.service.core.repository.CspRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class CspRepositoryCassandra implements CspRepository {
    @Autowired
    private CspDao dao;

    @Override
    public Csp save(Csp csp) {
        return dao.save(csp);
    }

    @Override
    public Csp findByName(String name) {
        return dao.findByName(name);
    }

    @Override
    public List<Csp> findAll() {
        return dao.findAll();
    }
}
