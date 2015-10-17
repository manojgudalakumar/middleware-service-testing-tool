package com.mstt.qa.servicevirtualization.uicontrols;

import java.util.Map;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.Callback;

public class ConstructMappedTable extends TableView<Map.Entry<String, String>> {
  TableColumn<Map.Entry<String, String>, String> keyColoumn;
  TableColumn<Map.Entry<String, String>, String> valueColoumn;

  @SuppressWarnings("unchecked")
  public ConstructMappedTable() {
    keyColoumn = new TableColumn<>("Key");
    keyColoumn
        .setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Map.Entry<String, String>, String>, ObservableValue<String>>() {
          @Override
          public ObservableValue<String> call(
              final TableColumn.CellDataFeatures<Map.Entry<String, String>, String> p) {
            return new SimpleStringProperty(p.getValue().getKey());
          }
        });
    valueColoumn = new TableColumn<>("Value");
    valueColoumn.setMinWidth(100);
    valueColoumn
        .setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Map.Entry<String, String>, String>, ObservableValue<String>>() {
          @Override
          public ObservableValue<String> call(
              final TableColumn.CellDataFeatures<Map.Entry<String, String>, String> p) {
            return new SimpleStringProperty(p.getValue().getValue());
          }
        });
    valueColoumn.setCellFactory(TextFieldTableCell.forTableColumn());
    valueColoumn.setOnEditCommit((
        final TableColumn.CellEditEvent<Map.Entry<String, String>, String> t) -> {
      t.getTableView().getItems().get(t.getTablePosition().getRow()).setValue(t.getNewValue());
    });
    setRowFactory(new Callback<TableView<Map.Entry<String, String>>, TableRow<Map.Entry<String, String>>>() {
      @Override
      public TableRow<Map.Entry<String, String>> call(
          final TableView<Map.Entry<String, String>> tableView) {
        final TableRow<Map.Entry<String, String>> row = new TableRow<>();
        final ContextMenu rowMenu = new ContextMenu();
        MenuItem editItem = new MenuItem("Edit");
        // editItem.setOnAction(...);
        MenuItem removeItem = new MenuItem("Delete");
        removeItem.setOnAction(new EventHandler<ActionEvent>() {
          @Override
          public void handle(final ActionEvent event) {
            tableView.getItems().remove(row.getItem());
          }
        });
        rowMenu.getItems().addAll(editItem, removeItem);

        // only display context menu for non-null items:
        row.contextMenuProperty().bind(
            Bindings.when(Bindings.isNotNull(row.itemProperty())).then(rowMenu)
                .otherwise((ContextMenu) null));
        return row;
      }
    });
    setEditable(true);
    getColumns().addAll(keyColoumn, valueColoumn);
    setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
  }
}
