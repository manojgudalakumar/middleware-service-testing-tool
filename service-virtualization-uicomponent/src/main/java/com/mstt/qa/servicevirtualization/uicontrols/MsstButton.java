package com.mstt.qa.servicevirtualization.uicontrols;

import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;

public class MsstButton extends Button {

  public MsstButton(String lableName) {
    super(lableName);
    initialize();
  }

  public MsstButton() {
    super();
  }

  DropShadow shadow = new DropShadow();

  private void initialize() {
    this.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent mouseEnteredEvent) -> {
      this.setEffect(shadow);
    });

    // Removing the shadow when the mouse cursor is off
    this.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent mouseExitedEvent) -> {
      this.setEffect(null);
    });
  }
}
