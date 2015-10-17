package com.mstt.qa.servicevirtualization.uicomponent.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.mstt.qa.servicevirtualization.soapservice.utils.WebServiceutil;
import com.mstt.qa.servicevirtualization.uicomponent.utils.VirtualizationUiOptions;
import com.mstt.qa.servicevirtualization.xml.utils.FormatXml;

public class CallSoapButtonListener implements ActionListener {
  private final JTextField wsdlUrl;
  private final JTextField username;
  private final JTextField password;
  private final JTextArea requestTextArea;
  private final JTextArea responseTextArea;
  private final VirtualizationUiOptions wireMockOptions;

  public CallSoapButtonListener(final JTextField wsdlUrl, final JTextArea requestTextArea,
      final JTextArea responseTextArea, final JTextField username, final JTextField password,
      final VirtualizationUiOptions wireMockOptions) {
    this.wsdlUrl = wsdlUrl;
    this.requestTextArea = requestTextArea;
    this.responseTextArea = responseTextArea;
    this.username = username;
    this.password = password;
    this.wireMockOptions = wireMockOptions;
  }

  @Override
  public void actionPerformed(final ActionEvent paramActionEvent) {
    String str =
        WebServiceutil.callWebService(requestTextArea.getText(), wsdlUrl.getText(),
            username.getText(), password.getText(), wireMockOptions);
    // String str =
    // WebServiceutil.callWebServiceWithProxy(requestTextArea.getText(),
    // wsdlUrl.getText(), username.getText(), password.getText(),
    // "proxysg.swg.apps.uprr.com",
    // "8080");
    System.out.println("The responese is " + str);
    try {
      str = FormatXml.format(str);
    } catch (RuntimeException e) {
      System.out.println(" there is runtimeexception occured" + e);
    } finally {
      responseTextArea.setText(str);
    }
  }
}
