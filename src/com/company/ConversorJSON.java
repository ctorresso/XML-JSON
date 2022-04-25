package com.company;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.json.JSONObject;
import org.json.XML;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class ConversorJSON {

    public String convertir(String file) throws ParserConfigurationException, SAXException, IOException, TransformerException, TransformerConfigurationException {
        File archivo = new File(file);
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();

        // Esto es para agilizar la lectura de archivos grandes
        documentBuilderFactory.setNamespaceAware(false);
        documentBuilderFactory.setValidating(false);
        documentBuilderFactory.setFeature("http://xml.org/sax/features/namespaces", false);
        documentBuilderFactory.setFeature("http://xml.org/sax/features/validation", false);
        documentBuilderFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-dtd-grammar", false);
        documentBuilderFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);

        // constructor de objetos XML
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();

        Document documento = documentBuilder.parse(archivo);

        // Esto ayuda al procesamiento
        documento.getDocumentElement().normalize();

        StringWriter writer = new StringWriter();
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer();
        transformer.transform(new DOMSource(documento), new StreamResult(writer));

        String xmlString = writer.getBuffer().toString();

        JSONObject jsonObj = XML.toJSONObject(xmlString);
        String json = jsonObj.toString(4);

        return json;
    }
}
