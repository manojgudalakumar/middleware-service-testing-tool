package com.mstt.qa.servicevirtualization.commoncomponents;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.TableCell;


public class TableCellComboBox<T> extends TableCell<T, String> {

  private ComboBox<String> comboBox;
  private final ObservableList<String> typData;

  public TableCellComboBox(final String[] comboBoxList) {
    typData = FXCollections.observableArrayList(comboBoxList);
  }

  @Override
  public void startEdit() {
    if (!isEmpty()) {
      super.startEdit();
      createComboBox();
      setText(null);
      setGraphic(comboBox);
    }
  }

  @Override
  public void cancelEdit() {
    super.cancelEdit();
    setText(getString());
    setGraphic(null);
  }

  @Override
  public void updateItem(final String item, final boolean empty) {
    super.updateItem(item, empty);

    if (empty) {
      setText(null);
      setGraphic(null);
    } else {
      if (isEditing()) {
        if (comboBox != null) {
          comboBox.setValue(getString());
        }
        setText(getString());
        setGraphic(comboBox);
      } else {
        setText(getString());
        setGraphic(null);
      }
    }
  }

  private void createComboBox() {
    comboBox = new ComboBox<>(typData);
    comboBoxConverter(comboBox);
    comboBox.valueProperty().set(getString());
    comboBox.setMinWidth(getWidth() - getGraphicTextGap() * 2);
    comboBox.setOnAction((e) -> {
      System.out.println("Committed: " + comboBox.getSelectionModel().getSelectedItem());
      commitEdit(comboBox.getSelectionModel().getSelectedItem());
    });
    // comboBox.focusedProperty().addListener((ObservableValue<? extends Boolean> observable,
    // Boolean oldValue, Boolean newValue) -> {
    // if (!newValue) {
    // commitEdit(comboBox.getSelectionModel().getSelectedItem());
    // }
    // });
  }

  private void comboBoxConverter(final ComboBox<String> comboBox) {
    // Define rendering of the list of values in ComboBox drop down.
    comboBox.setCellFactory((c) -> {
      return new ListCell<String>() {
        @Override
        protected void updateItem(final String item, final boolean empty) {
          super.updateItem(item, empty);

          if (item == null || empty) {
            setText(null);
          } else {
            setText(item);
          }
        }
      };
    });
  }

  private String getString() {
    return getItem() == null ? "" : getItem();
  }
}
