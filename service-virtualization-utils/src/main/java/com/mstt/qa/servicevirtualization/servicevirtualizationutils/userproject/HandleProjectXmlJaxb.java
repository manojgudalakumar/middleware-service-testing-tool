package com.mstt.qa.servicevirtualization.servicevirtualizationutils.userproject;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.charset.Charset;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchema;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;

import com.mstt.qa.servicevirtualization.MiddleWareTestingTool;

public class HandleProjectXmlJaxb {
  private static final String DEFAULT_NAMESPACE_URI = "##default";

  public static MiddleWareTestingTool getJavaObjectFromXmlFile(final String filePath)
      throws JAXBException {
    JAXBContext jaxbContext = JAXBContext.newInstance("com.mstt.qa.servicevirtualization");
    Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
    @SuppressWarnings("unchecked")
    JAXBElement<MiddleWareTestingTool> msttObject =
        (JAXBElement<MiddleWareTestingTool>) jaxbUnmarshaller.unmarshal(new File(filePath));
    return msttObject.getValue();
  }

  protected static MiddleWareTestingTool getJavaObjectFromXmlFile(final File file)
      throws JAXBException {
    JAXBContext jaxbContext = JAXBContext.newInstance("com.mstt.qa.servicevirtualization");
    Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
    @SuppressWarnings("unchecked")
    JAXBElement<MiddleWareTestingTool> msttObject =
        (JAXBElement<MiddleWareTestingTool>) jaxbUnmarshaller.unmarshal(file);
    return msttObject.getValue();
  }

  public static MiddleWareTestingTool getJavaObjectFromXmlString(final String xmlContent)
      throws JAXBException {
    JAXBContext jaxbContext = JAXBContext.newInstance(MiddleWareTestingTool.class);
    Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
    InputStream inputStream =
        new ByteArrayInputStream(xmlContent.getBytes(Charset.forName("UTF-8")));
    @SuppressWarnings("unchecked")
    JAXBElement<MiddleWareTestingTool> msttObject =
        (JAXBElement<MiddleWareTestingTool>) jaxbUnmarshaller.unmarshal(inputStream);
    return msttObject.getValue();
  }

  public static String getXmlFromMsttObject(final MiddleWareTestingTool msttObject)
      throws JAXBException {
    JAXBContext jaxbContext = JAXBContext.newInstance(MiddleWareTestingTool.class);
    return getXmlString(jaxbContext, msttObject);
  }

  @SuppressWarnings("unchecked")
  private static String getXmlString(final JAXBContext jaxbContext,
      final MiddleWareTestingTool marshalObject) throws JAXBException {
    Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
    jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
    StringWriter writer = new StringWriter();

    if (marshalObject.getClass().getAnnotation(XmlRootElement.class) != null) {
      jaxbMarshaller.marshal(marshalObject, writer);
    }

    QName qName = new QName(getNamespace(marshalObject.getClass()), "middlewaretestingtool");
    jaxbMarshaller.marshal(new JAXBElement<MiddleWareTestingTool>(qName,
        (Class<MiddleWareTestingTool>) marshalObject.getClass(), marshalObject), writer);

    return writer.toString();
  }

  protected static String getNamespace(final Class<?> clazz) {
    XmlType xmlTypeAnnotation = clazz.getAnnotation(XmlType.class);
    if (xmlTypeAnnotation != null && !xmlTypeAnnotation.namespace().equals(DEFAULT_NAMESPACE_URI)) {
      return xmlTypeAnnotation.namespace();
    }

    XmlSchema xmlSchemaAnnotation = clazz.getPackage().getAnnotation(XmlSchema.class);
    if (xmlSchemaAnnotation != null
        && !xmlSchemaAnnotation.namespace().equals(DEFAULT_NAMESPACE_URI)) {
      return xmlSchemaAnnotation.namespace();
    }

    return XMLConstants.NULL_NS_URI;
  }
}
