package com.sng.bpel.util;

import org.oasis_open.docs.wsbpel._2_0.process.executable.ObjectFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;

/**
 * User: mikola
 * Date: 26.10.12
 * Time: 17:01
 */
public class BpelFactory {
    private JAXBContext context;

    private BpelFactory(){}
    public static BpelFactory getDefault() {
        return new BpelFactory();
    }


    public Object createProcess(File bpelFile) throws JAXBException {
        JAXBContext ctx = safeInitContext();
        Unmarshaller unMarshaller = ctx.createUnmarshaller();
        Object object = unMarshaller.unmarshal(bpelFile);
        return object;
    }

    private JAXBContext safeInitContext() throws JAXBException {
        if (context == null) {
            context = JAXBContext.newInstance(ObjectFactory.class);
        }
        return context;
    }
}
