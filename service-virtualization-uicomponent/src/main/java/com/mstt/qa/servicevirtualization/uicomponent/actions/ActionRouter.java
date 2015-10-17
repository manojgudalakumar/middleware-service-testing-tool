package com.mstt.qa.servicevirtualization.uicomponent.actions;

import java.lang.reflect.InvocationTargetException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;

public class ActionRouter implements EventHandler<ActionEvent> {

  public static EventHandler<ActionEvent> getInstance() {
    return new ActionRouter();
  }

  @Override
  public void handle(final ActionEvent arg0) {
    String actionCommand = null;
    if (arg0.getSource() instanceof MenuItem) {
      actionCommand = ((MenuItem) arg0.getSource()).getId();
    }
    if (arg0.getSource() instanceof Button) {
      actionCommand = ((Button) arg0.getSource()).getId();
    }
    MenuActions mnuAction = new MenuActions();
    if (actionCommand == null) {
      System.out.println("set action command");
      return;
    }
    try {
      System.out.println("The action command is " + actionCommand);
      mnuAction.getClass().getMethod(actionCommand).invoke(mnuAction);
    } catch (NoSuchMethodException | SecurityException | IllegalAccessException
        | IllegalArgumentException | InvocationTargetException e1) {
      e1.printStackTrace();
    }
  }
}
