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

import net.orpiske.tcs.service.core.repository.DomainRepository;
import net.orpiske.tcs.service.core.repository.ReferenceRepository;
import net.orpiske.tcs.service.core.repository.cassandra.*;
import net.orpiske.tcs.service.core.repository.TagRepository;
import net.orpiske.tcs.service.core.service.TagCloudEventHandler;
import net.orpiske.tcs.service.core.service.TagCloudService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CoreConfig {


    @Bean
    public TagCloudService createTagCloudService() {
        return new TagCloudEventHandler();
    }

    @Bean
    public DomainRepository createDomainRepository() {
        return new DomainRepositoryCassandra();
    }

    @Bean
    public TagRepository createTagRepository() {
        return new TagRepositoryCassandra();
    }

    @Bean
    public ReferenceRepository createReferenceRepository() {
        return new ReferenceRepositoryCassandra();
    }

    @Bean
    public DomainDao createCspDao() {
       return new DomainDao(new DefaultPersistenceProperties());
    }

    @Bean
    public ReferencesDao createReferencesDao() {
        return new ReferencesDao(new DefaultPersistenceProperties());
    }

	@Bean
	public TagCloudDao createTagCloudDao() {
		return new TagCloudDao(new DefaultPersistenceProperties());
	}
}
