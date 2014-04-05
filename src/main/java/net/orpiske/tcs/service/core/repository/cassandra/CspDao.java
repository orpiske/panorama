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

import com.netflix.astyanax.entitystore.EntityManager;
import net.orpiske.tcs.service.core.domain.Csp;
import net.orpiske.tcs.service.persistence.utils.EntityManagerWrapper;
import net.orpiske.tcs.service.persistence.utils.PersistenceProperties;
import org.apache.log4j.Logger;

import java.util.List;

public class CspDao extends AbstractDao {
    private static final Logger logger = Logger.getLogger(CspDao.class);


    private EntityManager<Csp, String> em;

    public CspDao(PersistenceProperties persistenceProperties) {
        super(persistenceProperties);

        EntityManagerWrapper wrapper = getWrapper();

        em = wrapper.<Csp, String>getPersistenceObj(Csp.class, "csp");
    }

    public Csp save(Csp csp) {
        if (logger.isDebugEnabled()) {
            logger.debug("Saving CSP into Cassandra instance: " + csp);
        }

        em.put(csp);

        return csp;
    }


    public Csp findByName(String name) {
        if (logger.isDebugEnabled()) {
            logger.debug("Looking for a CSP named " + name);
        }

        Csp ret = em.get(name);

        if (logger.isDebugEnabled()) {
            logger.debug("Found CSP: " + ret);
        }

        return ret;
    }

    public List<Csp> findAll() {
        List<Csp> ret = em.getAll();
        return ret;
    }
}
