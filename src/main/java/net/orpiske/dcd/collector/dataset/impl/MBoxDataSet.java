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
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import javax.mail.*;

/**
 * An implementation of a MBox-based data set. It is capable of iterating through messages
 * in a mbox file
 */
public class MBoxDataSet implements DataSet {
    private static final Logger logger = Logger.getLogger(MBoxDataSet.class);

    private Session session;
    private Store store;
    private Folder inbox;

    private int messageCount;
    private int currentMessage = 1;


    /**
     * Constructor
     * @param file A File pointer to the MBox file
     * @throws MessagingException if unable to read or process the file
     */
    public MBoxDataSet(final File file) throws MessagingException {
        logger.debug("Creating a new data set from file " + file.getPath());

        Properties properties = new Properties();

        properties.setProperty("mail.store.protocol", "mstor");
        properties.setProperty("mstor.mbox.metadataStrategy", "none");
        properties.setProperty("mstor.mbox.cacheBuffers", "disabled");
        properties.setProperty("mstor.mbox.bufferStrategy", "mapped");
        properties.setProperty("mstor.metadata", "disabled");
        properties.setProperty("mstor.mozillaCompatibility", "enabled");

        session = Session.getDefaultInstance(properties);

        store = session.getStore(new URLName("mstor:" + file.getPath()));

        store.connect();

        loadMessages();
    }


    /**
     * Loads the messages
     * @throws MessagingException if unable to open the folder
     */
    private void loadMessages() throws MessagingException {
        logger.debug("Loading message and folder information");
        inbox = store.getDefaultFolder();

        logger.debug("Opened folder");
        inbox.open(Folder.READ_ONLY);

        messageCount = inbox.getMessageCount();
        logger.info("MBox file is loaded and contains " + messageCount
                + " to be processed");
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
        Message message = null;

        try {
            message = inbox.getMessage(currentMessage);

            if (logger.isDebugEnabled()) {
                logger.debug("Processing message " + currentMessage + " of "
                        + messageCount + "");

                if (logger.isTraceEnabled()) {
                    logger.trace("------- MESSAGE START -------");
                    logger.trace(message.getContent().toString());
                    logger.trace("-------  MESSAGE END  -------");
                }
            }

            mBoxData = new MBoxData(message.getContent().toString());
        }
        catch (MessagingException e) {
            logger.error("Unable to process message " + currentMessage +
                    ": " + e.getMessage(), e);
        }
        catch (IOException e) {
            logger.error("I/O error while processing the message " + currentMessage +
                        ": " + e.getMessage(), e);
        }

        currentMessage++;
        return mBoxData;
    }
}
