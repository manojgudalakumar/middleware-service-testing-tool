package com.mstt.qa.servicevirtualization.uicontrols;

import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;

public class MsstTextField extends TextField {

  public MsstTextField(final String textField) {
    super(textField);
    initialize();
  }

  public MsstTextField() {
    initialize();
  }

  DropShadow shadow = new DropShadow();

  private void initialize() {
    this.addEventHandler(MouseEvent.MOUSE_ENTERED, (final MouseEvent mouseEnteredEvent) -> {
      setEffect(shadow);
    });

    // Removing the shadow when the mouse cursor is off
    this.addEventHandler(MouseEvent.MOUSE_EXITED, (final MouseEvent mouseExitedEvent) -> {
      setEffect(null);
    });
  }
}
