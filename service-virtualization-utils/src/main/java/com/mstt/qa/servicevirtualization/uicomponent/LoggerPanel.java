package com.mstt.qa.servicevirtualization.uicomponent;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.GridPane;

import com.mstt.qa.servicevirtualization.servicevirtualizationutils.utils.ConstantsUtils;

public class LoggerPanel extends GridPane {
  private final TextArea textArea;

  public LoggerPanel() {
    super();
    textArea = new TextArea();
    attachContextMenuToTextArea();
    textArea.setEditable(false);
    textArea.setPrefHeight(REMAINING);
    textArea.setPrefWidth(REMAINING);
    addRow(1, textArea);
    setVisible(true);
  }

  public void showInfo(final String data) {
    textArea.appendText(data);
  }

  public void nullify() {
    textArea.setText(ConstantsUtils.NULL);
  }

  EventHandler<ActionEvent> copyText = new EventHandler<ActionEvent>() {
    @Override
    public void handle(final ActionEvent event) {
      Clipboard clipboard = Clipboard.getSystemClipboard();
      ClipboardContent text = new ClipboardContent();
      text.putString(textArea.getText());
      clipboard.setContent(text);
    }
  };

  private void attachContextMenuToTextArea() {
    ContextMenu m = new ContextMenu();
    MenuItem clear = new MenuItem("clear");
    MenuItem copy = new MenuItem("copy");
    clear.setOnAction(clearText);
    copy.setOnAction(copyText);
    m.getItems().addAll(clear, copy);
    textArea.setContextMenu(m);
  }

  EventHandler<ActionEvent> clearText = new EventHandler<ActionEvent>() {
    @Override
    public void handle(final ActionEvent event) {
      textArea.clear();
    }
  };
}
