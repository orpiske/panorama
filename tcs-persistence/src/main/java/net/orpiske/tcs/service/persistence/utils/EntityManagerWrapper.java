/**
   Copyright 2012 Otavio Rodolfo Piske

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
package net.orpiske.tcs.service.persistence.utils;


import com.netflix.astyanax.AstyanaxContext;
import com.netflix.astyanax.Keyspace;
import com.netflix.astyanax.connectionpool.NodeDiscoveryType;
import com.netflix.astyanax.connectionpool.impl.ConnectionPoolConfigurationImpl;
import com.netflix.astyanax.connectionpool.impl.ConnectionPoolType;
import com.netflix.astyanax.connectionpool.impl.CountingConnectionPoolMonitor;
import com.netflix.astyanax.entitystore.DefaultEntityManager;
import com.netflix.astyanax.entitystore.EntityManager;
import com.netflix.astyanax.impl.AstyanaxConfigurationImpl;
import com.netflix.astyanax.thrift.ThriftFamilyFactory;
import org.apache.log4j.Logger;


public class EntityManagerWrapper {
    private static final Logger logger = Logger.getLogger(EntityManagerWrapper.class);

    private static Keyspace keyspace;
    private static AstyanaxContext<Keyspace> keyspaceContext;

	public EntityManagerWrapper(final PersistenceProperties persistenceProperties) {
        logger.debug("Connecting to " + persistenceProperties.toString());

        String seeds = persistenceProperties.getHostName() +
                ":" + persistenceProperties.getPortAsString();

        keyspaceContext = new AstyanaxContext.Builder()
                .forCluster(persistenceProperties.getClusterName())
                .forKeyspace(persistenceProperties.getKeyspace())
                .withAstyanaxConfiguration(
                        new AstyanaxConfigurationImpl()
                                .setDiscoveryType(NodeDiscoveryType.NONE)
                                .setConnectionPoolType(ConnectionPoolType.TOKEN_AWARE))
                .withConnectionPoolConfiguration(
                        new ConnectionPoolConfigurationImpl(persistenceProperties.getClusterName()
                                + "_" + persistenceProperties.getKeyspace())
                                .setSocketTimeout(30000)
                                .setMaxTimeoutWhenExhausted(2000)
                                .setMaxConnsPerHost(20)
                                .setInitConnsPerHost(10)
                                .setSeeds(seeds))
                .withConnectionPoolMonitor(new CountingConnectionPoolMonitor())
                .buildKeyspace(ThriftFamilyFactory.getInstance());

        keyspaceContext.start();
        keyspace = keyspaceContext.getEntity();

    }
	
	public <K, V> EntityManager getPersistenceObj(final Class<K> clazz, final String cfName) {
        EntityManager<K, V> entityManager =
                new DefaultEntityManager.Builder<K, V>()
                        .withEntityType(clazz)
                        .withKeyspace(keyspace)
                        .withColumnFamily(cfName)
                        .build();


        return entityManager;
	}

    public void shutdown() {
        keyspaceContext.shutdown();
    }
}
