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
package net.orpiske.sas.service.utils;

import java.io.File;

/**
 * Application constants
 * @author Otavio R. Piske <angusyoung@gmail.com>
 *
 */
public final class Constants {

	public static final String VERSION = "1.0.0-SNAPSHOT";

	/**
	 * Restricted constructor
	 */
	private Constants() {}

	/**
	 * This property is used to set the configuration directory
	 */
	public static final String HOME_PROPERTY = "net.orpiske.sas.service.home";

    public static final String HOME_DIR;

	public static final String SAS_SERVICE_CONFIG_DIR;

	static {
        HOME_DIR = System.getProperty(HOME_PROPERTY);

        SAS_SERVICE_CONFIG_DIR = HOME_DIR + File.separator + "config";
	}

	/**
	 * This constant holds the configuration file name for the backend
	 */
	public static final String CONFIG_FILE_NAME = "sas-service.properties";
}
