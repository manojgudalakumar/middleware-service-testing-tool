package com.mstt.qa.servicevirtualization.commoncomponents;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.mstt.qa.servicevirtualization.servicevirtualizationutils.dto.SoapServiceVirtualizeDto;
import com.mstt.qa.servicevirtualization.uicomponent.MsstGuiPckg;

public class CenterDataPanel extends JPanel {
  private static final long serialVersionUID = 8654602770537773678L;
  JLabel label, labelname, WDSLURL;
  JTextField WSLURLField;
  JComboBox<String> operation;
  String Operationarray[] = {"Add Operation", "Get Operation", "set Operation"};
  JTextArea request, response;
  SoapServiceVirtualizeDto srv;
  MsstGuiPckg pck;

  public CenterDataPanel() {
    setLayout(new GridBagLayout());
    addDefaultComponents();
  }

  public CenterDataPanel(SoapServiceVirtualizeDto srv) {
    this.srv = srv;
  }

  public void addDefaultComponents() {
    label = new JLabel("Name : ");
    GridBagConstraints c = new GridBagConstraints();
    c.weightx = 0.5;
    c.weighty = 1.0;
    c.gridwidth = 1;
    c.gridx = 0;
    c.gridy = 0;
    add(label, c);

    labelname = new JLabel("Some Virtual name");
    c.weightx = 0.5;
    c.weighty = 1.0;
    c.gridwidth = 3;
    c.gridx = 1;
    c.gridy = 0;
    add(labelname, c);

    WDSLURL = new JLabel("WDSLURL : ");
    c.gridx = 0;
    c.gridy = 1;
    c.weightx = 0.5;
    c.weighty = 1.0;
    c.gridwidth = 1;
    add(WDSLURL, c);

    WSLURLField = new JTextField(30);
    c.gridx = 1;
    c.gridy = 1;
    c.weightx = 0.5;
    c.weighty = 1.0;
    c.gridwidth = 3;
    add(WSLURLField, c);

    operation = new JComboBox<String>(Operationarray);
    c.gridx = 1;
    c.gridy = 2;
    c.weightx = 0.5;
    c.weighty = 1.0;
    c.gridwidth = 2;
    add(operation, c);

    operation = new JComboBox<String>(Operationarray);
    c.gridx = 1;
    c.gridy = 2;
    c.weightx = 0.5;
    c.weighty = 1.0;
    c.gridwidth = 2;
    add(operation, c);

    request =
        new JTextArea("<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + "<noteRequest>"
            + "<to>Tove</to>" + "<from>Jani</from>" + "<heading>Reminder</heading>"
            + "<body>Don't forget me this weekend!</body>" + "</note>");
    response =
        new JTextArea("<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + "<noteresponse>"
            + "<to>Tove</to>" + "<from>Jani</from>" + "<heading>Reminder</heading>"
            + "<body>Don't forget me this weekend!</body>" + "</note>");
    request.setLineWrap(true);
    response.setLineWrap(true);
    c.fill = GridBagConstraints.HORIZONTAL;
    c.gridx = 0;
    c.gridy = 3;
    c.weightx = 1.0;
    c.weighty = 1.0;
    c.ipady = 300;
    c.gridwidth = 4;
    c.gridheight = GridBagConstraints.REMAINDER;
    JSplitPane textpane = new JSplitPane();
    textpane.setResizeWeight(0.5);
    textpane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
    textpane.setRightComponent(new JScrollPane(request));
    textpane.setLeftComponent(new JScrollPane(response));
    add(textpane, c);

  }

  /*
   * public void repaintCenterPane() { label.setText("painted"); repaint();
   * pck.setCenterDataPanel(this); }
   * 
   * protected void paintComponent(Graphics g) {
   * System.out.println("On repainting ...insidepaint method"); super.paintComponent(g);
   * addDefaultComponents(); pck.setCenterDataPanel(this); }
   */
}
