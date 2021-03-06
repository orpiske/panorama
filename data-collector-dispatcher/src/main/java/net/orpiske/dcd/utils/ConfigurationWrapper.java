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
package net.orpiske.dcd.utils;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

import java.io.File;
import java.io.FileNotFoundException;


/**
 * Wraps the configuration object
 * @author Otavio R. Piske <angusyoung@gmail.com>
 *
 */
public class ConfigurationWrapper {
	private static PropertiesConfiguration config;
	
	/**
	 * Restricted constructor
	 */
	private ConfigurationWrapper() {}

	/**
	 * Initializes the configuration object
	 * 
	 * @param configDir
	 * 			  The configuration directory containing the configuration file
	 * @param fileName
	 *            The name of the configuration file
	 * @throws java.io.FileNotFoundException
	 * @throws ConfigurationException
	 */
	public static void initConfiguration(final String configDir,
			final String fileName) throws FileNotFoundException,
			ConfigurationException {
		if (configDir == null) {
			throw new FileNotFoundException(
					"The configuration dir was not found");
		}
		
		config = new PropertiesConfiguration(configDir + File.separator
				+ fileName);
	}

	/**
	 * Gets the configuration object
	 * 
	 * @return the instance of the configuration object
	 */
	public static PropertiesConfiguration getConfig() {
		return config;
	}

}
