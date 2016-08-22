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
package net.orpiske.dcd.collector.vocabulary.contexts;

import net.orpiske.dcd.collector.vocabulary.Context;
import org.apache.log4j.Logger;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A context that checks if the word validates against a regex
 */
public class RegexContext implements Context {
    private static final Logger logger = Logger.getLogger(RegexContext.class);

    private Pattern pattern;
    private Matcher matcher;

    private String regex;

    public RegexContext(final String regex) {
        this.regex = regex;
    }

    @Override
    public String getName() {
        return "Regex matching";
    }

    @Override
    public boolean isValid(String word, String textData) {
        pattern = Pattern.compile(regex);
        matcher = pattern.matcher(textData);

        boolean ret = matcher.find();

        if (logger.isTraceEnabled()) {
            logger.trace(((ret == true) ? "Matched: " : "Not matched")
                    + textData);
        }

        return ret;
    }
}
