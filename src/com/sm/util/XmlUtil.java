package com.sm.util;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;

/**
 * User: mikola
 * Date: 18.11.12
 * Time: 22:52
 */
public class XmlUtil {
    public static <T> T unmarshall(String file, Class<T> desiredClass, Class context) throws Exception {
        JAXBContext ctx = JAXBContext.newInstance(context);
        Unmarshaller unMarshaller = ctx.createUnmarshaller();
        JAXBElement<T> object = (JAXBElement<T>) unMarshaller.unmarshal(new File(file));
        return object.getValue();
    }

    public static <T> void marshall(String file, JAXBElement<T> object, Class context) throws Exception {
        JAXBContext ctx = JAXBContext.newInstance(context);
        Marshaller marshaller = ctx.createMarshaller();
        marshaller.marshal(object, new File(file));
    }
}
