package com.mstt.qa.servicevirtualization.commoncomponents;

import java.util.Map;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;

import com.mstt.qa.servicevirtualization.servicevirtualizationutils.dto.RestServiceVirtualizeDto;
import com.mstt.qa.servicevirtualization.servicevirtualizationutils.dto.SoapServiceVirtualizeDto;
import com.mstt.qa.servicevirtualization.servicevirtualizationutils.dto.UserDefinedProjectDetailsDto;
import com.mstt.qa.servicevirtualization.servicevirtualizationutils.utils.CommonMethodsUtils;
import com.mstt.qa.servicevirtualization.servicevirtualizationutils.utils.MyEntrySet;
import com.mstt.qa.servicevirtualization.uicomponent.utils.CommonDataUtils;
import com.mstt.qa.servicevirtualization.uicomponent.utils.CommonUiActions;
import com.mstt.qa.servicevirtualization.uicontrols.ConstructMappedTable;
import com.mstt.qa.servicevirtualization.uicontrols.MsstButton;
import com.mstt.qa.servicevirtualization.uicontrols.MsstTextField;

public class PropertyPanel extends Tab {
  private static final int TEXT_FIELD_SIZE = 225;
  final static Logger logger = CommonMethodsUtils.getLogger(PropertyPanel.class.getName());
  public static final int KEY_COLOUMN_INDEX = 0;
  public static final int VALUE_COLOUMN_INDEX = 1;
  private final ConstructMappedTable propTable;
  private MsstTextField keyTextField;
  private MsstTextField valueTextField;
  private final MsstButton addProp;
  private final ScrollPane properPanel;

  public PropertyPanel(final String string) {
    super(string);
    properPanel = new ScrollPane();
    properPanel.setPrefSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
    propTable = new ConstructMappedTable();
    addProp = new MsstButton("Add");
    init();

  }

  private void init() {
    GridPane northPanel = new GridPane();
    northPanel.setPadding(new Insets(10, 0, 4, 4));
    keyTextField = new MsstTextField();
    keyTextField.setPrefWidth(TEXT_FIELD_SIZE);
    valueTextField = new MsstTextField();
    valueTextField.setPrefWidth(TEXT_FIELD_SIZE);
    Label lblField1 = new Label("Key  ");
    addProp.setPrefWidth(80);
    propTable.setPrefSize(northPanel.getPrefHeight(), northPanel.getPrefWidth());
    Label lblField2 = new Label("Value");
    northPanel.setHgap(10);
    northPanel.setVgap(5);
    northPanel.add(lblField1, 0, 0);
    northPanel.add(keyTextField, 1, 0);
    northPanel.add(lblField2, 0, 1);
    northPanel.add(valueTextField, 1, 1);
    GridPane.setColumnSpan(addProp, 2);
    GridPane.setHalignment(addProp, HPos.CENTER);
    northPanel.add(addProp, 0, 2);
    GridPane.setColumnSpan(propTable, 3);
    northPanel.add(propTable, 0, 3);
    properPanel.setContent(northPanel);
    properPanel.setFitToHeight(true);
    properPanel.setFitToWidth(true);
    setContent(properPanel);
  }

  public void LoadDefaultContent(final UserDefinedProjectDetailsDto usrDto) {
    addRowsToPropTable(usrDto.getPropertyDto().getPropertyMap());
    removeListners();
    addProp.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(final ActionEvent arg0) {
        addActionSteps(usrDto.getPropertyDto().getPropertyMap());
      }
    });
  }

  private void removeListners() {
    CommonDataUtils.removeActionListeners(addProp);
  }

  protected void nullifyTheTextFields() {
    keyTextField.setText(null);
    valueTextField.setText(null);

  }

  private void addRowsToPropTable(final Map<String, String> map) {
    propTable.getItems().clear();
    ObservableList<Map.Entry<String, String>> items =
        FXCollections.observableArrayList(map.entrySet());
    propTable.setItems(items);

  }

  public void populateProperTable(final RestServiceVirtualizeDto rstDto) {
    removeListners();
    addRowsToPropTable(rstDto.getPropertiesMap());
    addProp.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(final ActionEvent arg0) {
        addActionSteps(rstDto.getPropertiesMap());
      }
    });
  }

  public void populateProperTable(final SoapServiceVirtualizeDto srv) {
    removeListners();
    addRowsToPropTable(srv.getPropertiesMap());
    addProp.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(final ActionEvent arg0) {
        addActionSteps(srv.getPropertiesMap());
      }
    });
  }

  private void addActionSteps(final Map<String, String> mapWhereKeyNeedToAdded) {
    String keytext = keyTextField.getText();
    String propvalue = valueTextField.getText();
    if (!(CommonDataUtils.validate(keyTextField, valueTextField, mapWhereKeyNeedToAdded))) {
      return;
    }
    mapWhereKeyNeedToAdded.put(keytext, propvalue);
    int nextIndex = propTable.getSelectionModel().getSelectedIndex() + 1;
    propTable.getItems().add(nextIndex, MyEntrySet.getEntrySet(keytext, propvalue));
    propTable.getSelectionModel().select(nextIndex);
    nullifyTheTextFields();
    CommonUiActions.projectChanged();
  }


  public void removeEntries() {
    propTable.getItems().clear();

  }
}
