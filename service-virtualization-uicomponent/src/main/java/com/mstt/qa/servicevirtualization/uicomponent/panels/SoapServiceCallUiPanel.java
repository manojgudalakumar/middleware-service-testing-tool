package com.mstt.qa.servicevirtualization.uicomponent.panels;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Panel;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.mstt.qa.servicevirtualization.uicomponent.listeners.AuthneticationMouseListener;
import com.mstt.qa.servicevirtualization.uicomponent.listeners.CallSoapButtonListener;
import com.mstt.qa.servicevirtualization.uicomponent.utils.VirtualizationUiOptions;

public class SoapServiceCallUiPanel {
  private final JTextField wsdlUrl; // Declare component TextField
  private final JTextField username; // Declare component TextField
  private final JTextField password; // Declare component TextField
  private final JButton callSoapButton; // Declare component TextField
  private final JTextArea requestTextArea;
  private final JTextArea responseTextArea;
  private final JPanel pl;
  private final JCheckBox authenitication;
  private final VirtualizationUiOptions wireMockOptions;

  public SoapServiceCallUiPanel(final VirtualizationUiOptions wireMockOption) {
    this.wireMockOptions = wireMockOption;
    wsdlUrl = new JTextField("", 50);
    username = new JTextField("", 5);
    password = new JTextField("df", 5);
    callSoapButton = new JButton("Call Soap Service");
    requestTextArea = new JTextArea(5, 10);
    responseTextArea = new JTextArea(5, 10);
    pl = new JPanel(new FlowLayout());
    // pl = new Panel(new BorderLayout());
    JScrollPane requestTextAreaScrolPane =
        new JScrollPane(requestTextArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
            JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
    JScrollPane responseTextAreaScrolPane =
        new JScrollPane(responseTextArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
            JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
    JPanel pk = new JPanel(new FlowLayout());
    authenitication = new JCheckBox("do we have autorization");
    pk.add(wsdlUrl);
    pk.add(authenitication);
    pk.add(callSoapButton);
    Panel panel1 = new Panel(new FlowLayout());
    panel1.add(requestTextAreaScrolPane);
    panel1.add(responseTextAreaScrolPane);
    pk.add(username);
    pk.add(password);
    username.setVisible(false);
    password.setVisible(false);
    authenitication.addActionListener(new AuthneticationMouseListener(username, password, pk));
    callSoapButton.addActionListener(new CallSoapButtonListener(wsdlUrl, requestTextArea,
        responseTextArea, username, password, wireMockOptions));
    pl.add(panel1);
    pl.add(pk, BorderLayout.PAGE_START);
  }

  public JPanel getSoapServiceCallUiPanel() {
    return pl;
  }
}
