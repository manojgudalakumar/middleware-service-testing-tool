package com.mstt.qa.servicevirtualization.xml.utils;

import java.util.HashMap;
import java.util.Map;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

public class FragmentContentHandler implements ContentHandler {

  private String xPath = "/";
  private final XMLReader xmlReader;
  private FragmentContentHandler parent;
  private final StringBuilder characters = new StringBuilder();
  private final Map<String, Integer> elementNameCount = new HashMap<String, Integer>();
  Map<String, String> elements = null;

  public FragmentContentHandler(final XMLReader xmlReader, final Map<String, String> elements) {
    this.xmlReader = xmlReader;
    this.elements = elements;
  }

  private FragmentContentHandler(final String xPath, final XMLReader xmlReader,
      final FragmentContentHandler parent) {
    this(xmlReader, parent.elements);
    this.xPath = xPath;
    this.parent = parent;
  }

  public void startElement(final String uri, final String localName, final String qName,
      final Attributes atts) throws SAXException {
    Integer count = elementNameCount.get(qName);
    if (null == count) {
      count = 1;
    } else {
      count++;
    }
    elementNameCount.put(qName, count);
    String childXPath = xPath + "/" + qName + "[" + count + "]";

    int attsLength = atts.getLength();
    for (int x = 0; x < attsLength; x++) {
      elements.put(childXPath + "[@" + atts.getQName(x) + ']', atts.getValue(x));
    }
    FragmentContentHandler child = new FragmentContentHandler(childXPath, xmlReader, this);
    xmlReader.setContentHandler(child);
  }

  public void endElement(final String uri, final String localName, final String qName)
      throws SAXException {
    String value = characters.toString().trim();
    if (value.length() > 0) {
      // System.out.println(xPath + "='" + characters.toString() + "'");
      elements.put(xPath, characters.toString());
    }
    xmlReader.setContentHandler(parent);
  }

  public void characters(final char[] ch, final int start, final int length) throws SAXException {
    characters.append(ch, start, length);
  }

  public void setDocumentLocator(final Locator locator) {
    // TODO Auto-generated method stub

  }

  public void startDocument() throws SAXException {
    // TODO Auto-generated method stub

  }

  public void endDocument() throws SAXException {
    // TODO Auto-generated method stub

  }

  public void startPrefixMapping(final String prefix, final String uri) throws SAXException {
    // TODO Auto-generated method stub

  }

  public void endPrefixMapping(final String prefix) throws SAXException {
    // TODO Auto-generated method stub

  }

  public void ignorableWhitespace(final char[] ch, final int start, final int length)
      throws SAXException {
    // TODO Auto-generated method stub

  }

  public void processingInstruction(final String target, final String data) throws SAXException {
    // TODO Auto-generated method stub

  }

  public void skippedEntity(final String name) throws SAXException {
    // TODO Auto-generated method stub

  }
}
