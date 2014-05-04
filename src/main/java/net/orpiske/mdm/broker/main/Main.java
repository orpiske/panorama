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
package net.orpiske.mdm.broker.main;

import net.orpiske.mdm.broker.main.actions.RunAction;
import net.orpiske.mdm.broker.main.actions.runner.BrokerRunner;
import net.orpiske.mdm.broker.utils.ConfigurationWrapper;
import net.orpiske.mdm.broker.utils.Constants;
import org.apache.commons.configuration.ConfigurationException;

import java.io.FileNotFoundException;
import java.util.Arrays;

public class Main {
    private static void help(int code) {
        System.out.println("Usage: mdn <action>\n");

        System.out.println("Actions:");
        System.out.println(" run");
        System.out.println("----------");
        System.out.println(" help");
        System.out.println(" --version");

        System.exit(code);
    }

    public static void main(String[] args) {
        int ret = 0;

        if (args.length == 0) {
            help(1);
        }

        try {
            ConfigurationWrapper.initConfiguration(
                Constants.MDM_BROKER_CONFIG_DIR,
                Constants.CONFIG_FILE_NAME);
        }
        catch (ConfigurationException e) {
            System.err.println("Unable to load configuration file " +
                    "'mdm-broker.properties': " + e.getMessage());

            System.exit(-1);
        }
        catch (FileNotFoundException e) {
            System.err.println("Unable to find configuration file " +
                    "'mdm-broker.properties': " + e.getMessage());

            System.exit(-1);
        }

        String first = args[0];
        String[] newArgs = Arrays.copyOfRange(args, 1, args.length);


        if (first.equals("run")) {
            RunAction runAction = new RunAction(newArgs);

            ret = runAction.run();
            System.exit(ret);
        }

        help(-1);
    }
}
