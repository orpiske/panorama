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
package net.orpiske.tcs.service.config;

import net.orpiske.tcs.service.persistence.utils.PersistenceProperties;

class DefaultPersistenceProperties implements PersistenceProperties {
    @Override
    public String getHostName() {
        return "localhost";
    }

    @Override
    public String getClusterName() {
        return "TCS_Cluster";
    }

    @Override
    public String getPortAsString() {
        return "9160";
    }

    @Override
    public String getKeyspace() {
        return "tcs";
    }

    public String toString() {
        return getHostName() + ":" + getPortAsString() + "/" + getKeyspace() + "@"
                + getClusterName();
    }
}
