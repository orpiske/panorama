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
import net.orpiske.tcs.service.core.domain.Domain;
import net.orpiske.tcs.service.persistence.utils.EntityManagerWrapper;
import net.orpiske.tcs.service.persistence.utils.PersistenceProperties;
import org.apache.log4j.Logger;

import java.util.List;

public class DomainDao extends AbstractDao {
    private static final Logger logger = Logger.getLogger(DomainDao.class);


    private EntityManager<Domain, String> em;

    public DomainDao(PersistenceProperties persistenceProperties) {
        super(persistenceProperties);

        EntityManagerWrapper wrapper = getWrapper();

        em = wrapper.<Domain, String>getPersistenceObj(Domain.class, "domain");
    }

    public Domain save(Domain domain) {
        if (logger.isDebugEnabled()) {
            logger.debug("Saving CSP into Cassandra instance: " + domain);
        }

        em.put(domain);

        return domain;
    }


    public Domain findByName(String name) {
        if (logger.isDebugEnabled()) {
            logger.debug("Looking for a domain named " + name);
        }

        Domain ret = em.get(name);

        if (logger.isDebugEnabled()) {
            logger.debug("Found domain: " + ret);
        }

        return ret;
    }

    public List<Domain> findAll() {
        List<Domain> ret = em.getAll();
        return ret;
    }
}
