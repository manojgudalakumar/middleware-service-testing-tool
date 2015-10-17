package com.mstt.qa.servicevirtualization.uicontrols;

import javafx.scene.control.MenuItem;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;

public class MsstMenuItem extends MenuItem {

  public MsstMenuItem(String lableName) {
    super(lableName);
    initialize();
    setMnemonicParsing(true);
  }

  DropShadow shadow = new DropShadow();

  private void initialize() {
    this.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent mouseEnteredEvent) -> {
    });

    // Removing the shadow when the mouse cursor is off
    this.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent mouseExitedEvent) -> {
    });
  }
}
