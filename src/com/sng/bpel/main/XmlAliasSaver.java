package com.sng.bpel.main;

import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.logging.Logger;

/**
 * User: mikola
 * Date: 02.11.15
 * Time: 14:26
 */
public class XmlAliasSaver {

    private final File sourceXml;
    private final File targetXml;
    private final DocumentBuilder db;
    private final Transformer transformer;

    public XmlAliasSaver(File sourceXml, File targetXml) {
        this.sourceXml = sourceXml;
        this.targetXml = targetXml;
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            db = dbf.newDocumentBuilder();
            transformer = TransformerFactory.newInstance().newTransformer();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    public void patch() {
        try {
            Document targetDoc = db.parse(targetXml);
            Element processElement = db.parse(sourceXml).getDocumentElement();
            Element targetProcess = targetDoc.getDocumentElement();
            NamedNodeMap sourceAttrs = processElement.getAttributes();
            NamedNodeMap targetAttrs = targetProcess.getAttributes();

            for (int i = 0; i < sourceAttrs.getLength(); i++) {
                Node srcAttr = sourceAttrs.item(i);
                String attribute = targetProcess.getAttribute(srcAttr.getNodeName());
                if (attribute == null || attribute.isEmpty()) {
                    Attr addedAttr = targetDoc.createAttribute(srcAttr.getNodeName());
                    addedAttr.setValue(srcAttr.getNodeValue());
                    targetAttrs.setNamedItem(addedAttr);
                    Logger.getLogger(XmlAliasSaver.class.getSimpleName()).info("Saved attr: " + srcAttr);
                }
            }

            saveFile(targetDoc, targetXml);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    private void saveFile(Document targetDoc, File targetXml) {
        Result output = new StreamResult(targetXml);
        Source input = new DOMSource(targetDoc);
        try {
            transformer.transform(input, output);
        } catch (TransformerException e) {
            throw new IllegalStateException(e);
        }
    }
}
