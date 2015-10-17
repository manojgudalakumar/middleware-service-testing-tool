package com.mstt.qa.servicevirtualization.uicomponent.actions;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;

public final class KeyStrokes {
  // Prevent instantiation
  private KeyStrokes() {}

  public static final KeyCombination ESC = new KeyCodeCombination(KeyCode.ESCAPE);
  public static final KeyCombination OPEN = new KeyCodeCombination(KeyCode.O,
      KeyCombination.CONTROL_DOWN);
  public static final KeyCombination EXIT = new KeyCodeCombination(KeyCode.Q,
      KeyCombination.CONTROL_DOWN);
  public static final KeyCombination SAVE_AS = new KeyCodeCombination(KeyCode.S,
      KeyCombination.CONTROL_DOWN, KeyCombination.ALT_DOWN);
  public static final KeyCombination REMOVE = new KeyCodeCombination(KeyCode.DELETE);
  public static final KeyCombination HELP = new KeyCodeCombination(KeyCode.H,
      KeyCombination.ALT_DOWN);
  public static final KeyCombination SAVE = new KeyCodeCombination(KeyCode.S,
      KeyCombination.CONTROL_DOWN);
  public static final KeyCombination CLOSE = new KeyCodeCombination(KeyCode.L,
      KeyCombination.CONTROL_DOWN);
  public static final KeyCombination NEWPROJECT = new KeyCodeCombination(KeyCode.N,
      KeyCombination.CONTROL_DOWN);
  public static final KeyCombination ADDSOAPSV = new KeyCodeCombination(KeyCode.P,
      KeyCombination.CONTROL_DOWN);
  public static final KeyCombination ADDRESTSV = new KeyCodeCombination(KeyCode.R,
      KeyCombination.CONTROL_DOWN);
  public static final KeyCombination ADDSERVICETEST = new KeyCodeCombination(KeyCode.T,
      KeyCombination.CONTROL_DOWN);

}
