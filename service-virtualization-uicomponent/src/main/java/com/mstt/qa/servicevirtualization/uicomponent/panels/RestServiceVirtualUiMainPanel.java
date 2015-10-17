package com.mstt.qa.servicevirtualization.uicomponent.panels;

import java.net.MalformedURLException;
import java.util.Arrays;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import com.mstt.qa.servicevirtualization.servicevirtualizationutils.dto.MsttRequestDefinition;
import com.mstt.qa.servicevirtualization.servicevirtualizationutils.dto.MsttResponseDefinition;
import com.mstt.qa.servicevirtualization.servicevirtualizationutils.dto.RestServiceVirtualizeDto;
import com.mstt.qa.servicevirtualization.servicevirtualizationutils.utils.URLUtils;
import com.mstt.qa.servicevirtualization.uicontrols.MsstButton;
import com.mstt.qa.servicevirtualization.uicontrols.MsstTextArea;
import com.mstt.qa.servicevirtualization.uicontrols.MsstTextField;

public class RestServiceVirtualUiMainPanel extends ScrollPane {
  final static boolean shouldWeightX = true;
  final static boolean RIGHT_TO_LEFT = false;
  MsstTextField restUrlTextField;
  MsstTextArea requestTextArea;
  MsstTextArea responseTextArea;
  ToggleGroup responseType;
  RadioButton json;
  RadioButton xml;
  private final MsstButton saveButton;
  List<String> requestMethods = Arrays.asList("GET", "POST", "PUT");
  RestServiceVirtualizeDto rstDto;
  ComboBox<String> requestMethodType;
  final VirtualDetailsTabbedPane virtualizeDetailsPane;

  public RestServiceVirtualUiMainPanel(final RestServiceVirtualizeDto rstDto,
      final VirtualDetailsTabbedPane virtualizeDetailsPane) {
    this.rstDto = rstDto;
    this.virtualizeDetailsPane = virtualizeDetailsPane;
    buildResponseType();
    GridPane pane = new GridPane();
    pane.setAlignment(Pos.TOP_LEFT);
    pane.setHgap(10);
    pane.setVgap(5);
    setPadding(new Insets(5, 10, 5, 10));
    Text wsdlUrl = new Text("End Point\n URL:");
    Text responseTypeLabel = new Text("Response\nType");
    Text responseMethodLabel = new Text("Response\nMethod");
    Text requestXml = new Text("Request\nXml");
    requestXml.setTextAlignment(TextAlignment.JUSTIFY);
    Text responseXml = new Text("Response\nXml");
    responseXml.setTextAlignment(TextAlignment.JUSTIFY);
    restUrlTextField = new MsstTextField();
    requestTextArea = new MsstTextArea(rstDto.getRequestData());
    responseTextArea = new MsstTextArea(rstDto.getResponseData());
    requestTextArea.setMinWidth(300);
    responseTextArea.setMinWidth(300);
    GridPane.setHalignment(restUrlTextField, HPos.LEFT);
    requestMethodType = new ComboBox<String>();
    GridPane.setHalignment(requestMethodType, HPos.LEFT);
    requestMethodType.setItems(FXCollections.observableList(requestMethods));
    pane.add(wsdlUrl, 0, 1, 1, 1);
    pane.add(restUrlTextField, 1, 1, 3, 1);
    pane.add(responseTypeLabel, 4, 1, 1, 1);
    pane.add(json, 5, 1, 1, 1);
    pane.add(xml, 6, 1, 1, 1);
    pane.add(responseMethodLabel, 7, 1, 1, 1);
    pane.add(requestMethodType, 8, 1, 1, 1);
    saveButton = new MsstButton("Save Stub");
    GridPane.setHalignment(saveButton, HPos.CENTER);
    pane.add(requestXml, 0, 3, 1, 1);
    pane.add(requestTextArea, 1, 3, 3, 1);
    pane.add(responseXml, 4, 3, 1, 1);
    pane.add(responseTextArea, 5, 3, 4, 1);
    pane.add(saveButton, 0, 4, 8, 1);
    saveButton.setOnAction(saveButtonActionEventHandler);
    requestMethodType.setOnAction(requestMethodActionEventHandler);
    setContent(pane);
  }

  private void buildResponseType() {
    responseType = new ToggleGroup();
    json = new RadioButton("JSON");
    xml = new RadioButton("XML");
    json.setToggleGroup(responseType);
    xml.setToggleGroup(responseType);
  }

  EventHandler<ActionEvent> saveButtonActionEventHandler = new EventHandler<ActionEvent>() {

    @Override
    public void handle(final ActionEvent event) {
      loadStub();
    }
  };
  EventHandler<ActionEvent> requestMethodActionEventHandler = new EventHandler<ActionEvent>() {

    @Override
    public void handle(final ActionEvent event) {
      String selectedItem = requestMethodType.getSelectionModel().getSelectedItem();
      if (selectedItem == "GET") {
        // removecompfrompanel(middlePanel, requestTextAreaScrolPane);
        populateQueryParametersTab();
      } else {
        // addcomptopanel(middlePanel, requestTextAreaScrolPane);
      }
    }
  };

  protected void populateQueryParametersTab() {
    virtualizeDetailsPane.populateQueryParamtersTab(rstDto, restUrlTextField.getText().trim());
  }

  public void loadStub() {
    populatePkmwtStubRequest(rstDto.getRequesStub());
    populatePkmwtStubResponse(rstDto.getResponseStub());
  }

  private void populatePkmwtStubRequest(final MsttRequestDefinition requesStub) {
    try {
      requesStub.setRequestMethod(requestMethodType.getSelectionModel().getSelectedItem());
      requesStub.setServiceVirtualziseType("REST");
      requesStub.setUrl(URLUtils.getPathFromURL(restUrlTextField.getText()));
      requesStub.setRequestBody(requestTextArea.getText());
    } catch (MalformedURLException e) {
      e.printStackTrace();
    }
  }

  private void populatePkmwtStubResponse(final MsttResponseDefinition responseStub) {
    responseStub.setResponse(responseTextArea.getText());
  }
}
