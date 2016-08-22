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
import net.orpiske.tcs.service.core.domain.Tag;
import net.orpiske.tcs.service.core.domain.TagCloud;
import net.orpiske.tcs.service.persistence.utils.EntityManagerWrapper;
import net.orpiske.tcs.service.persistence.utils.PersistenceProperties;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class TagCloudDao extends AbstractDao {
	private static final Logger logger = Logger.getLogger(TagCloud.class);

	private EntityManager<Tag, String> em;

	public TagCloudDao(PersistenceProperties persistenceProperties) {
		super(persistenceProperties);

		EntityManagerWrapper wrapper = getWrapper();
		em = wrapper.<Tag, String>getPersistenceObj(Tag.class, "tag_cloud");
	}

	public TagCloud findAll() {
		List<Tag> list = em.getAll();

        /*
         * Clustering order probably would solve our problem, however we can't use it
         * for now since we already use compact storage (and if there's a simpler way
         * I don't about it ... yet!).
         */
        Collections.sort(list);
        return new TagCloud(list);
	}


	public TagCloud findByCsp(final Domain domain) {
        List<Tag> tagList = new ArrayList<Tag>();

        if (logger.isDebugEnabled()) {
            logger.debug("Looking for a domain named " + domain);
        }

        /*
        I wanted to use the simpler/cleaner solution that uses native queries, as used in
         Astyanax test cases, however the code below throws
         'java.lang.UnsupportedOperationException' and I don't want to/cannot research
         further now

        TODO: check

        Collection<Tag> domainCollection = em.createNativeQuery()
                    .whereColumn("domain")
                    .equal(domain.getDomain()).getResultSet();
        */
        tagList = em.find("SELECT * FROM tag_cloud WHERE domain = '"
                + domain.getDomain() + "'");
        if (logger.isDebugEnabled()) {
            logger.debug("Found " + tagList.size() + " entries for "
                    + domain.getDomain());
        }

        return new TagCloud(tagList);
	}
}
