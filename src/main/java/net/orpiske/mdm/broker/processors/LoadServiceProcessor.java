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
package net.orpiske.mdm.broker.processors;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.cxf.message.MessageContentsList;

import javax.xml.ws.Holder;

public class LoadServiceProcessor implements Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
        System.out.println("Message received");

        Object object = exchange.getIn().getBody();

        if (object instanceof MessageContentsList) {
            MessageContentsList messageContentsList = (MessageContentsList) object;

            Object input = messageContentsList.get(0);

            System.out.println("Type: " + input.getClass());


            Holder<Integer> code = (Holder<Integer>) messageContentsList.get(4);
            Holder<String> message = (Holder<String>) messageContentsList.get(5);

            code.value = 0;
            message.value = "Success";

            System.out.println("Setting up reply message");

            exchange.getOut().setBody(new Object[] { code, message });
        }


    }
}
