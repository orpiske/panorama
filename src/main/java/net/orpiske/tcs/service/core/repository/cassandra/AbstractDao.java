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

import net.orpiske.tcs.service.persistence.utils.EntityManagerWrapper;
import net.orpiske.tcs.service.persistence.utils.PersistenceProperties;

public abstract class AbstractDao {

    private static EntityManagerWrapper wrapper;

    protected AbstractDao(PersistenceProperties persistenceProperties) {
        synchronized (this) {
            if (wrapper == null) {
                wrapper = new EntityManagerWrapper(persistenceProperties);
            }
        }
    }

    protected EntityManagerWrapper getWrapper() {
        return wrapper;
    }
}
