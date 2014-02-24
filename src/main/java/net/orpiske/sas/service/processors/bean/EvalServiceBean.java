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
package net.orpiske.sas.service.processors.bean;


import net.orpiske.exchange.sas.eval.v1.EvalResponseType;
import net.orpiske.exchange.sas.eval.v1.RequestType;
import net.orpiske.exchange.sas.eval.v1.ResponseType;

public class EvalServiceBean {

    private static final int NEGATIVE = -1;
    private static final int NEUTRAL = 0;
    private static final int POSITIVE = 1;



    private int getIndividualScore(final WordsWrapper wrapper, final String phrase) {
        return wrapper.getScore(phrase);
    }


    private int getScore(final String phrase) {
        int positiveIndex = getIndividualScore(new PositiveWordsWrapper(),
                phrase);

        int negativeIndex = getIndividualScore(new NegativeWordsWrapper(),
                phrase);


        return positiveIndex + negativeIndex;
    }


    public ResponseType eval(RequestType requestType) {
        ResponseType responseType = new ResponseType();
        String phrase = requestType.getEvalRequest().getPhrase();

        int score = getScore(phrase);

        EvalResponseType evalResponseType = new EvalResponseType();
        evalResponseType.setScore(score);
        responseType.setEvalResponse(evalResponseType);

        return responseType;
    }
}
