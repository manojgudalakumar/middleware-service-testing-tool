package com.mstt.qa.servicevirtualization.uicomponent.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.mstt.qa.servicevirtualization.xml.utils.MySaxParser;

public class XpathListActionListerner implements ActionListener {
  private final JTextField xpathValue;
  private final JTextArea requestTextArea;

  public XpathListActionListerner(final JTextField xpathValues, final JTextArea requestTextArea) {
    this.xpathValue = xpathValues;
    this.requestTextArea = requestTextArea;
  }

  public void actionPerformed(final ActionEvent paramActionEvent) {
    @SuppressWarnings("unchecked")
    JComboBox<String> jcombo = (JComboBox<String>) paramActionEvent.getSource();
    String key = (String) jcombo.getSelectedItem();
    System.out.println("the key is " + key);
    try {
      System.out.println("the value is "
          + MySaxParser.getElementsFromString(requestTextArea.getText()).get(key));
      xpathValue.setText(MySaxParser.getElementsFromString(requestTextArea.getText()).get(key));
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

  }
}
