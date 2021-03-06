package net.orpiske.dcd.main;

import net.orpiske.dcd.actions.ParseAction;
import net.orpiske.dcd.utils.ConfigurationWrapper;
import net.orpiske.dcd.utils.Constants;
import net.orpiske.dcd.utils.DomainConfigurationWrapper;
import org.apache.commons.configuration.ConfigurationException;

import java.io.FileNotFoundException;
import java.util.Arrays;

/**
 * Copyright 2014 Otavio Rodolfo Piske
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
public class Main {

    private static void initDomainConfiguration() {
        try {
            DomainConfigurationWrapper.initConfiguration(Constants.DCD_CONFIG_DIR,
                    "domain.properties");
        } catch (ConfigurationException e) {
            System.err.println("Unable to load configuration file " +
                    "'domain.properties': " + e.getMessage());

            System.exit(-1);
        }
        catch (FileNotFoundException e) {
            System.err.println("Unable to find configuration file " +
                    "'domain.properties': " + e.getMessage());

            System.exit(-1);
        }
    }

    private static void initConfiguration() {
        try {
            ConfigurationWrapper.initConfiguration(Constants.DCD_CONFIG_DIR,
                    Constants.CONFIG_FILE_NAME);
        } catch (ConfigurationException e) {
            System.err.println("Unable to load configuration file " +
                    "'dcd.properties': " + e.getMessage());

            System.exit(-1);
        }
        catch (FileNotFoundException e) {
            System.err.println("Unable to find configuration file " +
                    "'dcd.properties': " + e.getMessage());

            System.exit(-1);
        }
    }

    private static void help(int code) {
        System.out.println("Usage: dcd <action>\n");

        System.out.println("Actions:");
        System.out.println(" fetch");
        System.out.println(" parse");
        System.out.println(" dispatch");
        System.out.println("----------");
        System.out.println(" help");
        System.out.println(" --version");

        System.exit(code);
    }


    /**
     * @param args
     */
    public static void main(String[] args) {
        int ret = 0;

        if (args.length == 0) {
            help(1);
        }

        String first = args[0];
        String[] newArgs = Arrays.copyOfRange(args, 1, args.length);


        initConfiguration();
        initDomainConfiguration();

        // TODO: add a fetching action
        if (first.equals("fetch")) {

        }

        if (first.equals("parse")) {
            ParseAction parseAction = new ParseAction(newArgs);

            ret = parseAction.run();
            System.exit(ret);
        }

        if (first.equals("dispatch")) {

        }

    }


}
