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
package net.orpiske.sas.service.main.actions;

import net.orpiske.sas.service.utils.LogConfigurator;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;

/**
 * Implements app specific main.actions
 * @author Otavio R. Piske <angusyoung@gmail.com>
 *
 */
public abstract class AbstractAction {

	
	/**
	 * Prints the help for the action and exit
	 * @param options the options object
	 * @param code the exit code
	 */
	protected void help(final Options options, int code) {
		HelpFormatter formatter = new HelpFormatter();

		formatter.printHelp("sas-service", options);
		System.exit(code);
	}


    /**
     * Configures the logging level
     * @param level the logging level
     */
    protected void configureOutput(final String level) {
        if (level == null || level.isEmpty()) {
            LogConfigurator.silent();

            return;
        }

        if (level.equals("verbose")) {
            LogConfigurator.verbose();
        }
        else {
            if (level.equals("debug")) {
                LogConfigurator.debug();
            }
            else {
                if (level.equals("trace")) {
                    LogConfigurator.trace();
                }
                else {
                    LogConfigurator.silent();
                }
            }
        }
    }

	
	/**
	 * Process the command line arguments
	 * @param args the command line arguments
	 */
	protected abstract void processCommand(String[] args);
	
	/**
	 * Runs the action
	 * @return the exit code
	 */
	public abstract int run();
	

}
