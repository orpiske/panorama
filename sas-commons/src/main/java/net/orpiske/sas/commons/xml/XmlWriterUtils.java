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
package net.orpiske.sas.commons.xml;


import java.io.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.namespace.QName;

import org.apache.commons.io.IOUtils;

/**
 * XML writing utilities
 *
 * @author Otavio R. Piske <angusyoung@gmail.com>
 */
public class XmlWriterUtils {

    /**
     * Restricted constructor
     */
    private XmlWriterUtils() {}


    /**
     * Utility method that creates a new JAXBElement, which is used to marshal
     * the object
     * @param ns the name space
     * @param elementName the name element
     * @param clazz the class of the object to marshal
     * @param obj the object to marshal
     * @param <T> the type of the object
     * @return A JAXBElement that can be used to marshal the object
     */
    public static <T> JAXBElement<T> newJAXBElement(String ns, String elementName, Class<T> clazz, T obj) {
        QName qName = new QName(ns, elementName);

        return new JAXBElement<T>(qName, clazz, obj);
    }


    /**
     * Marshals an object into a formatted XML document
     * @param element the JAXB element object that represents an 'object' of
     * type T
     * @param object the object to be transformed into XML
     * @param writer the writer object
     * @throws JAXBException if unable to transform the object to XML
     */
    public static <T> void marshal(JAXBElement<T> element, T object, Writer writer) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(
                object.getClass().getPackage().getName());

        Marshaller m = context.createMarshaller();

        m.setProperty( Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE );
        m.marshal(element, writer );
    }


    /**
     * Marshals an object into a formatted XML document
     * @param element the JAXB element object that represents an 'object' of
     * type T
     * @param object the object to be transformed into XML
     * @param stream the output stream
     * @throws JAXBException if unable to transform the object to XML
     */
    public static <T> void marshal(JAXBElement<T> element, T object, OutputStream stream) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(
                object.getClass().getPackage().getName());

        Marshaller m = context.createMarshaller();

        m.setProperty( Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE );
        m.marshal(element, stream );
    }


    /**
     * Marshals an object into a formatted XML document
     * @param element the JAXB element object that represents an 'object' of
     * type T
     * @param object the object to be transformed into XML
     * @param file the output file
     * @throws JAXBException if unable to transform the object to XML
     */
    public static <T> void marshal(JAXBElement<T> element, T object, File file) throws JAXBException, IOException {
        OutputStream stream = new FileOutputStream(file);

        try {
            marshal(element, object, stream);

            stream.flush();
            stream.close();
        }
        catch (JAXBException e) {
            IOUtils.closeQuietly(stream);

            throw e;
        } catch (IOException e) {
            IOUtils.closeQuietly(stream);

            throw e;
        }
    }

}