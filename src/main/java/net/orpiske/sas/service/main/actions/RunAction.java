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
package net.orpiske.sas.service.main.actions;

import net.orpiske.sas.service.main.actions.runner.ServiceRunner;
import org.apache.commons.cli.*;
import org.apache.log4j.Logger;

public class RunAction extends AbstractAction {
    private static final Logger logger = Logger.getLogger(RunAction.class);

    private CommandLine cmdLine;
    private Options options;

    private boolean isHelp;
    private ServiceRunner serviceRunner;

    public RunAction(String[] args) {
        serviceRunner = new ServiceRunner();

        processCommand(args);
    }

    @Override
    protected void processCommand(String[] args) {
        CommandLineParser parser = new PosixParser();

        options = new Options();

        options.addOption("h", "help", false, "prints the help");
        options.addOption(null, "log-level", true,
                "sets log level (should be one of [ 'trace', 'debug', 'verbose'])");

        try {
            cmdLine = parser.parse(options, args);
        } catch (ParseException e) {
            help(options, -1);
        }

        isHelp = cmdLine.hasOption("help");
        configureOutput(cmdLine.getOptionValue("log-level"));
    }

    @Override
    public int run() {
        try {
            if (isHelp) {
                help(options, 0);
            }

            serviceRunner.run();

            return 0;
        }
        catch (Exception e) {
            logger.fatal(e.getMessage(), e);
            return -1;
        }
    }
}
