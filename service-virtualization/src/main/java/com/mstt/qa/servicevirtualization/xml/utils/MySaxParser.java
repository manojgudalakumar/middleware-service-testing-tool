package com.mstt.qa.servicevirtualization.xml.utils;

import java.io.FileInputStream;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

public class MySaxParser {

  public static void main(final String[] args) throws Exception {
    String str =
        "<soap:Envelope xmlns:soap=\"http://www.w3.org/2003/05/soap-envelope\" xmlns:web=\"http://www.w3schools.com/webservices/\">"
            + "<soap:Header/>   <soap:Body>      <web:CelsiusToFahrenheit>         <!--Optional:-->         <web:Celsius>37</web:Celsius>"
            + "      </web:CelsiusToFahrenheit>   </soap:Body> </soap:Envelope>";
    System.out.println(getElementsFromFile("E:\\logs\\xmlfile.txt"));
    System.out.println(getElementsFromString(str));
  }

  public static Map<String, String> getElementsFromFile(final String filePath) throws Exception {
    SAXParserFactory spf = SAXParserFactory.newInstance();
    SAXParser sp = spf.newSAXParser();
    XMLReader xr = sp.getXMLReader();
    Map<String, String> elements = new HashMap<String, String>();
    FragmentContentHandler fch = new FragmentContentHandler(xr, elements);
    xr.setContentHandler(fch);
    xr.parse(new InputSource(new FileInputStream(filePath)));
    return fch.elements;

  }

  public static Map<String, String> getElementsFromString(final String xmlString) throws Exception {
    Map<String, String> elements = getRawElementsFromString(xmlString);
    Iterator<Map.Entry<String, String>> iter = elements.entrySet().iterator();
    while (iter.hasNext()) {
      Map.Entry<String, String> entry = iter.next();
      if (entry.getValue().startsWith("http")) {
        iter.remove();
      }
    }
    return elements;
  }

  public static Map<String, String> getRawElementsFromString(final String xmlString)
      throws Exception {
    SAXParserFactory spf = SAXParserFactory.newInstance();
    SAXParser sp = spf.newSAXParser();
    XMLReader xr = sp.getXMLReader();
    Map<String, String> elements = new HashMap<String, String>();
    FragmentContentHandler fch = new FragmentContentHandler(xr, elements);
    xr.setContentHandler(fch);
    xr.parse(new InputSource(new StringReader(xmlString)));
    return fch.elements;

  }
}
