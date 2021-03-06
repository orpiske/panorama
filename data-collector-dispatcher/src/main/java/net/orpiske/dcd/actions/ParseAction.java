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

package net.orpiske.dcd.actions;


import net.orpiske.dcd.actions.runner.ParserRunner;
import net.orpiske.dcd.dispatcher.impl.SimpleDispatcher;
import net.orpiske.dcd.dispatcher.impl.WebServicesDispatcher;
import org.apache.commons.cli.*;

public class ParseAction extends AbstractAction {
    private CommandLine cmdLine;
    private Options options;

    private boolean isHelp;
    private ParserRunner runner;

    public ParseAction(final String[] args) {
        runner = new ParserRunner();

        processCommand(args);
    }

    @Override
    protected void processCommand(String[] args) {
        CommandLineParser parser = new PosixParser();

        options = new Options();

        options.addOption("h", "help", false, "prints the help");
        options.addOption("f", "file", true, "the file to parse");
        options.addOption(null, "dry-run", false, "does not dispatch the data");
        options.addOption(null, "log-level", true,
                "sets log level (should be one of [ 'trace', 'debug', 'verbose'])");

        try {
            cmdLine = parser.parse(options, args);
        } catch (ParseException e) {
            help(options, -1);
        }

        isHelp = cmdLine.hasOption("help");

        String fileName = cmdLine.getOptionValue('f');
        if (fileName == null) {
            help(options, -1);
        }
        runner.setFile(fileName);

        boolean isDryRun = cmdLine.hasOption("dry-run");
        if (isDryRun) {
            runner.setDispatcher(new SimpleDispatcher());
        }
        else {
            runner.setDispatcher(new WebServicesDispatcher());
        }


        configureOutput(cmdLine.getOptionValue("log-level"));
    }

    @Override
    public int run() {
        try {
            if (isHelp) {
                help(options, 0);
            }

            runner.run();

            return 0;
        }
        catch (Exception e) {
            return -1;
        }
    }
}
