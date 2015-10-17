package com.mstt.qa.servicevirtualization.soapservice.utils;

import java.io.ByteArrayInputStream;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPMessage;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public final class SoapUtil {

  /**
   * private constructor
   */
  private SoapUtil() {

  }

  /**
   * Constructs the string SOAP fault message.
   * 
   * @param code fault code.
   * @param description fault description.
   * 
   * @return string representing the SOAP fault.
   */
  public static String getSoapFault(final String code, final String description) {
    StringBuffer buf = new StringBuffer();
    buf.append("<env:Envelope xmlns:env=\"http://schemas.xmlsoap.org/soap/envelope/\"><env:Body><env:Fault><faultcode>");
    buf.append(code);
    buf.append("</faultcode><faultstring>");
    buf.append(description);
    buf.append("</faultstring></env:Fault></env:Body></env:Envelope>");
    return buf.toString();
  }

  /**
   * Finds the operation name from the input SOAP request string message.
   * 
   * @param soapRequest string SOAP message.
   * 
   * @return operation name.
   */
  public static String getOperationName(final String soapRequest) {

    try {
      MessageFactory messageFactory;
      if (soapRequest.contains(javax.xml.soap.SOAPConstants.URI_NS_SOAP_1_2_ENVELOPE)) {
        messageFactory = MessageFactory.newInstance(javax.xml.soap.SOAPConstants.SOAP_1_2_PROTOCOL);
      } else {
        messageFactory = MessageFactory.newInstance();
      }
      SOAPMessage msg =
          messageFactory.createMessage(null, new ByteArrayInputStream(soapRequest.getBytes()));
      Node n = msg.getSOAPBody();

      NodeList cnList = n.getChildNodes();

      for (int i = 0; i < cnList.getLength(); i++) {
        if (cnList.item(i).getLocalName() != null) {
          return cnList.item(i).getLocalName();
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
      throw new MockException("Unable to find operation name from request");
    }

    return null;
  }

  /**
   * Gets the element value from the input XML for the provided element name.
   * 
   * @param xml XML string from which the element value needs to be derived.
   * @param element XML element name.
   * 
   * @return a string value of XML element.
   */
  public static String getXmlElementValue(final String xml, final String element) {

    // Search for the element with name space
    int startInd = xml.indexOf(":" + element + ">");
    if (startInd == -1) {
      // Element with name space not found
      startInd = xml.indexOf("<" + element + ">");

      // Element not found
      if (startInd == -1) {
        return null;
      }
    }
    // Search for the end for the element. This is because of the name space
    startInd = xml.indexOf('>', startInd);
    if (startInd == -1) {
      // Element end not found
      return null;
    }

    // Find the closing element
    int endInd = xml.indexOf("</", startInd);
    if (endInd == -1 || endInd <= startInd) {
      // This could be an empty element
      return null;
    }

    // Element found. Return its value
    return xml.substring(startInd + 1, endInd);

  }

  /**
   * Gets the element value (trimmed) from the input XML for the provided element name.
   * 
   * @param xml XML string from which the element value needs to be derived.
   * @param element XML element name.
   * 
   * @return a string value of XML element.
   */
  public static String getXmlElementTrimmedValue(final String xml, final String element) {

    String dirtyValue = getXmlElementValue(xml, element);

    if (dirtyValue == null) {
      return dirtyValue;
    }

    String cleanValue = dirtyValue.replaceAll("\\n", "");
    cleanValue = cleanValue.replaceAll("\\r", "");
    cleanValue = cleanValue.replaceAll("\\t", "");

    return cleanValue.trim();
  }

  /**
   * Gets the element value from the input XML for the provided element name.
   * 
   * @param xml XML string from which the element value needs to be derived.
   * @param element XML element name.
   * @param wrapperElement wrapper element.
   * 
   * @return a string value of XML element.
   */
  public static String getXmlElementValue(final String xml, final String element,
      final String wrapperElement) {

    // Get the content under wrapper element
    String intXml = getXmlElementValue(xml, wrapperElement);

    if (intXml == null) {

      return intXml;
    }

    return getXmlElementValue(intXml, element);

  }

  /**
   * Gets the element value (trimmed) from the input XML for the provided element name.
   * 
   * @param xml XML string from which the element value needs to be derived.
   * @param element XML element name.
   * @param wrapperElement wrapper element.
   * 
   * @return a string value of XML element.
   */
  public static String getXmlElementTrimmedValue(final String xml, final String element,
      final String wrapperElement) {

    // Get the content under wrapper element
    String intXml = getXmlElementValue(xml, wrapperElement);

    if (intXml == null) {

      return intXml;
    }

    return getXmlElementTrimmedValue(intXml, element);
  }
}
