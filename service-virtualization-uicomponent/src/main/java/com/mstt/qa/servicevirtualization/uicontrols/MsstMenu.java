package com.mstt.qa.servicevirtualization.uicontrols;

import javafx.scene.control.Menu;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;

public class MsstMenu extends Menu {

  public MsstMenu(String lableName) {
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

  public void addSeparator() {
    SeparatorMenuItem smi = new SeparatorMenuItem();
    getItems().addAll(smi);
  }
}
