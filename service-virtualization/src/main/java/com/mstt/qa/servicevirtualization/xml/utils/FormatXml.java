package com.mstt.qa.servicevirtualization.xml.utils;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.mstt.qa.servicevirtualization.utils.StaticValues;

public class FormatXml {

  public static String format(final String unformattedXml) {
    try {
      final Document document = parseXmlFile(unformattedXml);

      OutputFormat format = new OutputFormat(document);
      format.setLineWidth(StaticValues.LINE_WIDTH_FORMAT_XML);
      format.setIndenting(true);
      format.setIndent(StaticValues.INDENT_FORMAT_XML);
      Writer out = new StringWriter();
      XMLSerializer serializer = new XMLSerializer(out, format);
      serializer.serialize(document);

      return out.toString();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private static Document parseXmlFile(final String in) {
    try {
      DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
      DocumentBuilder db = dbf.newDocumentBuilder();
      InputSource is = new InputSource(new StringReader(in));
      return db.parse(is);
    } catch (ParserConfigurationException e) {
      throw new RuntimeException(e);
    } catch (SAXException e) {
      throw new RuntimeException(e);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

}
