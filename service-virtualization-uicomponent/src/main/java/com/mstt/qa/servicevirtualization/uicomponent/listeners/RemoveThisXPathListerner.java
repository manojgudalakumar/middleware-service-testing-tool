package com.mstt.qa.servicevirtualization.uicomponent.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

public class RemoveThisXPathListerner implements ActionListener {
  private final JPanel parentPanel;
  private final JPanel childJPanel;

  public RemoveThisXPathListerner(final JPanel parentPanel, final JPanel childPanel) {
    this.childJPanel = childPanel;
    this.parentPanel = parentPanel;
  }

  public void actionPerformed(final ActionEvent paramActionEvent) {
    parentPanel.remove(childJPanel);
    parentPanel.validate();
    parentPanel.repaint();
  }

}
