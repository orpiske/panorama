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
package net.orpiske.dcd.collector.dataset.impl;

import net.orpiske.dcd.collector.dataset.Data;
import net.orpiske.dcd.collector.dataset.DataSet;

import java.io.File;
import java.util.Properties;

import javax.mail.*;

public class MBoxDataSet implements DataSet {

    private File file;
    private Session session;
    private Store store;
    private Folder folder;

    private int messageCount;
    private int currentMessage;

    public MBoxDataSet(final File file) throws MessagingException {
        this.file = file;

        Properties properties = new Properties();
        session = Session.getDefaultInstance(properties);

        store = session.getStore(new URLName("mstor:" + file.getPath()));
        store.connect();

        loadMessages();
    }


    private void loadMessages() throws MessagingException {
        folder = store.getDefaultFolder();
        messageCount = folder.getMessageCount();

    }

    @Override
    public boolean hasNext() {
        if (currentMessage < messageCount) {
            return true;
        }

        return false;
    }

    @Override
    public Data next() {
        MBoxData mBoxData = null;

        try {
            Message message = folder.getMessage(currentMessage);

            mBoxData = new MBoxData(message.toString());
        }
        catch (MessagingException e) {
            e.printStackTrace();
        }

        return mBoxData;
    }
}
