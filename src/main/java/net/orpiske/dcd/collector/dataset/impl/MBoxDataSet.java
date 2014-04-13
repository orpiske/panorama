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
import org.apache.commons.lang3.time.FastDateFormat;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.lang3.time.FastDateParser;
import org.apache.log4j.Logger;

import javax.mail.*;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.Enumeration;
import java.util.Properties;

/**
 * An implementation of a MBox-based data set. It is capable of iterating through messages
 * in a mbox file
 */
public class MBoxDataSet implements DataSet {
    private static final Logger logger = Logger.getLogger(MBoxDataSet.class);

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
        Session session;

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
        return currentMessage <= messageCount;
    }

    @Override
    public Data next() {
        MBoxData mBoxData = null;
        Message message;

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
            mBoxData.setBody(message.getContent().toString());

            StringBuilder stringBuilder = new StringBuilder();
            Enumeration<Header> enumeration = message.getAllHeaders();
            while (enumeration.hasMoreElements()) {
                Header header = enumeration.nextElement();

                if (header.getName().equalsIgnoreCase("from")) {
                    mBoxData.setOriginator(header.getValue());
                }
                else {
                    stringBuilder.append(header.getValue());
                }

                if (header.getName().equalsIgnoreCase("date")) {
                    String strDate = header.getValue();

                    if (strDate != null) {
                        strDate = strDate.substring(0, strDate.length() - 6);

                        try {
                            Date date = DateUtils.parseDate(strDate,
                                    "EEE, dd MMM yyyy HH:mm:ss Z");

                            mBoxData.setDate(date);
                        } catch (ParseException e) {
                            logger.error("Unable to parse date " + strDate + ": "
                                    + e.getMessage(), e);
                        }
                    }

                }

                stringBuilder.append('\n');
            }

            mBoxData.setHeader(stringBuilder.toString());
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
