package com.mstt.qa.servicevirtualization.uicontrols;

import javafx.scene.control.TextArea;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;

public class MsstTextArea extends TextArea {

  public MsstTextArea(final String textField) {
    super(textField);
    initialize();
  }

  public MsstTextArea(final int rowCount, final int coloumnCount) {
    setPrefRowCount(rowCount);
    setPrefColumnCount(coloumnCount);
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
    setWrapText(true);
    setPrefWidth(150);
  }
}
