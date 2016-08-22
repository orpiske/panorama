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


import net.orpiske.exchange.sas.common.error.v1.ReturnType;
import net.orpiske.exchange.sas.eval.v1.EvalResponseType;
import net.orpiske.exchange.sas.eval.v1.RequestType;
import net.orpiske.exchange.sas.eval.v1.ResponseType;

/**
 * Just a simple bean for performing some mock business rules
 */
public class EvalServiceBean {


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


    /**
     * Calculates the sentiment of the phrase based on the existence of
     * certain words in the text
     * @param requestType the request object
     * @return A response object
     */
    public ResponseType eval(RequestType requestType) {
        ResponseType responseType = new ResponseType();
        String phrase = requestType.getEvalRequest().getPhrase();

        int score = getScore(phrase);

        EvalResponseType evalResponseType = new EvalResponseType();
        evalResponseType.setScore(score);
        responseType.setEvalResponse(evalResponseType);

        return responseType;
    }


    /**
     * Setups an error message
     * @return a response type containing an error
     */
    public ResponseType createError() {
        ResponseType responseType = new ResponseType();

        ReturnType returnType = new ReturnType();
        returnType.setCode(-1);
        returnType.setMessage("Unable to process the request");

        responseType.setReturn(returnType);

        return responseType;
    }
}
