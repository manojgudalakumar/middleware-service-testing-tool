package com.mstt.qa.servicevirtualization.uicomponent.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

import javax.swing.AbstractButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class AuthneticationMouseListener implements ActionListener {
  private final JTextField username;
  private final JTextField password;
  private final JPanel pk;

  public AuthneticationMouseListener(final JTextField username, final JTextField password, JPanel pk) {
    this.username = username;
    this.password = password;
    this.pk = pk;

  }

  public void mouseClicked(final MouseEvent paramMouseEvent) {

  }

  public void mousePressed(final MouseEvent paramMouseEvent) {
    // TODO Auto-generated method stub

  }

  public void mouseReleased(final MouseEvent paramMouseEvent) {
    // TODO Auto-generated method stub

  }

  public void mouseEntered(final MouseEvent paramMouseEvent) {
    // TODO Auto-generated method stub

  }

  public void mouseExited(final MouseEvent paramMouseEvent) {
    // TODO Auto-generated method stub

  }

  public void actionPerformed(ActionEvent actionEvent) {
    AbstractButton abstractButton = (AbstractButton) actionEvent.getSource();
    boolean selected = abstractButton.getModel().isSelected();
    if (selected) {
      username.setVisible(true);
      password.setVisible(true);
      pk.validate();
      pk.repaint();
    }
    if (!selected) {
      username.setVisible(false);
      password.setVisible(false);
      pk.validate();
      pk.repaint();
    }
  }

}
