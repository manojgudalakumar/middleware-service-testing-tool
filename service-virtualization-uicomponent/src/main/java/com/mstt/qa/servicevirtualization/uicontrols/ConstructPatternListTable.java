package com.mstt.qa.servicevirtualization.uicontrols;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.Callback;

import com.mstt.qa.servicevirtualization.commoncomponents.TableCellComboBox;
import com.mstt.qa.servicevirtualization.servicevirtualizationutils.dto.PatternMatcherDto;
import com.mstt.qa.servicevirtualization.servicevirtualizationutils.dto.PatternType;

public class ConstructPatternListTable extends TableView<PatternMatcherDto> {
  TableColumn<PatternMatcherDto, String> keyColoumn;
  TableColumn<PatternMatcherDto, String> valueColoumn;
  TableColumn<PatternMatcherDto, String> patternColoumn;

  @SuppressWarnings("unchecked")
  public ConstructPatternListTable() {
    setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    keyColoumn = new TableColumn<>("Key");
    keyColoumn
        .setCellValueFactory(new Callback<TableColumn.CellDataFeatures<PatternMatcherDto, String>, ObservableValue<String>>() {
          @Override
          public ObservableValue<String> call(
              final TableColumn.CellDataFeatures<PatternMatcherDto, String> p) {
            return new SimpleStringProperty(p.getValue().getKey());
          }
        });
    patternColoumn = new TableColumn<>("Pattern");
    patternColoumn
        .setCellValueFactory(new Callback<TableColumn.CellDataFeatures<PatternMatcherDto, String>, ObservableValue<String>>() {
          @Override
          public ObservableValue<String> call(
              final TableColumn.CellDataFeatures<PatternMatcherDto, String> p) {
            return new SimpleStringProperty(p.getValue().getPattern());
          }
        });
    valueColoumn = new TableColumn<>("Value");
    valueColoumn.setMinWidth(100);
    valueColoumn
        .setCellValueFactory(new Callback<TableColumn.CellDataFeatures<PatternMatcherDto, String>, ObservableValue<String>>() {
          @Override
          public ObservableValue<String> call(
              final TableColumn.CellDataFeatures<PatternMatcherDto, String> p) {
            return new SimpleStringProperty(p.getValue().getValue());
          }
        });
    valueColoumn.setCellFactory(TextFieldTableCell.forTableColumn());
    valueColoumn
        .setOnEditCommit((final TableColumn.CellEditEvent<PatternMatcherDto, String> t) -> {
          t.getTableView().getItems().get(t.getTablePosition().getRow()).setValue(t.getNewValue());
        });
    setEditable(true);
    Callback<TableColumn<PatternMatcherDto, String>, TableCell<PatternMatcherDto, String>> comboBoxCellFactory =
        (final TableColumn<PatternMatcherDto, String> param) -> new TableCellComboBox<PatternMatcherDto>(
            PatternType.equatePatterns());;
    patternColoumn.setCellFactory(comboBoxCellFactory);
    patternColoumn
        .setOnEditCommit((final TableColumn.CellEditEvent<PatternMatcherDto, String> t) -> {
          t.getTableView().getItems().get(t.getTablePosition().getRow())
              .setPattern(t.getNewValue());

        });
    setRowFactory(new Callback<TableView<PatternMatcherDto>, TableRow<PatternMatcherDto>>() {
      @Override
      public TableRow<PatternMatcherDto> call(final TableView<PatternMatcherDto> tableView) {
        final TableRow<PatternMatcherDto> row = new TableRow<>();
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
    getColumns().addAll(keyColoumn, patternColoumn, valueColoumn);
    setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
  }
}
