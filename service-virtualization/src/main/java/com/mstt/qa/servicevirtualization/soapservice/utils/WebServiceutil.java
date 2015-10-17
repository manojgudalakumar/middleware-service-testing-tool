package com.mstt.qa.servicevirtualization.soapservice.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPMessage;

import org.apache.commons.codec.binary.Base64;

import com.github.tomakehurst.wiremock.core.Options;

public class WebServiceutil {

  /**
   * Invokes the web service using SAAJ API.
   * 
   * @param request soap request string.
   * @param url end point URL.
   * @param user user name for authentication.
   * @param password password for authentication.
   * @param wireMockOptions
   * 
   * @return response soap string.
   */
  public static String callWebService(String request, final String url, final String user,
      final String password, final Options wireMockOptions) {
    if (wireMockOptions.browserProxyingEnabled()) {
      System.setProperty("http.proxyHost", wireMockOptions.proxyHostHeader());
      System.setProperty("http.proxyPort", "" + wireMockOptions.portNumber());
    }
    if (request == null)
      request = "";
    try {
      SOAPConnection conn = SOAPConnectionFactory.newInstance().createConnection();

      MimeHeaders hd = new MimeHeaders();
      hd.addHeader("Content-Type", "text/xml");
      if (StringUtil.isValid(user) && StringUtil.isValid(password)) {
        String authorization = new String(Base64.encodeBase64((user + ":" + password).getBytes()));
        hd.addHeader("Authorization", "Basic " + authorization);
      }

      SOAPMessage msg =
          MessageFactory.newInstance().createMessage(hd,
              new ByteArrayInputStream(request.getBytes()));
      SOAPMessage resp = conn.call(msg, url);
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      resp.writeTo(baos);
      return new String(baos.toByteArray());
    } catch (Exception e) {
      StringWriter sw = new StringWriter();
      e.printStackTrace(new PrintWriter(sw));
      return sw.toString();
    }
  }

  public static String callWebServiceWithProxy(String request, final String url, final String user,
      final String password, final String httpProxyIp, final String proxyPort) {
    if (request == null)
      request = "";
    try {
      SOAPConnection conn = SOAPConnectionFactory.newInstance().createConnection();

      MimeHeaders hd = new MimeHeaders();
      hd.addHeader("Content-Type", "text/xml");
      if (StringUtil.isValid(user) && StringUtil.isValid(password)) {
        String authorization = new String(Base64.encodeBase64((user + ":" + password).getBytes()));
        hd.addHeader("Authorization", "Basic " + authorization);
      }

      SOAPMessage msg =
          MessageFactory.newInstance().createMessage(hd,
              new ByteArrayInputStream(request.getBytes()));
      SOAPMessage resp = conn.call(msg, url);
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      resp.writeTo(baos);
      return new String(baos.toByteArray());
    } catch (Exception e) {
      StringWriter sw = new StringWriter();
      e.printStackTrace(new PrintWriter(sw));
      return sw.toString();
    }
  }
}
