package com.mstt.qa.servicevirtualization.uicomponent.panels;

import java.util.Calendar;

import com.mstt.qa.servicevirtualization.servicevirtualizationutils.dto.SoapServiceVirtualizeDto;
import com.mstt.qa.servicevirtualization.soapservice.utils.SoapUIUtil;
import com.mstt.qa.servicevirtualization.uicomponent.utils.VirtualizationUiOptions;
import com.mstt.qa.servicevirtualization.uicontrols.MsstButton;
import com.mstt.qa.servicevirtualization.uicontrols.MsstTextArea;
import com.mstt.qa.servicevirtualization.uicontrols.MsstTextField;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class SoapServiceVirtualUiMainPanel extends ScrollPane {
  MsstTextField wsdrlUrlTextField;
  MsstTextArea requestTextArea;
  MsstTextArea responseTextArea;
  MsstTextField serviceEndPointUrl;
  MsstButton saveButton;
  MsstButton loadWsdlButton;
  ComboBox<String> operationList;
  SoapServiceVirtualizeDto soap;
  private final VirtualizationUiOptions wireMockOptions;

  public SoapServiceVirtualUiMainPanel(final SoapServiceVirtualizeDto soap,
      final VirtualizationUiOptions wireMockOptions) {
    this.soap = soap;
    this.wireMockOptions = wireMockOptions;
    GridPane pane = new GridPane();
    pane.setAlignment(Pos.TOP_LEFT);
    pane.setHgap(10);
    pane.setVgap(10);
    setPadding(new Insets(5, 10, 5, 10));
    Label wsdlUrl = new Label("Wsdl URL:");
    Text requestXml = new Text("Request\nXml");
    requestXml.setTextAlignment(TextAlignment.JUSTIFY);
    Text responseXml = new Text("Response\nXml");
    responseXml.setTextAlignment(TextAlignment.JUSTIFY);
    loadWsdlButton = new MsstButton("LoadWsdl");
    wsdrlUrlTextField = new MsstTextField();
    wsdrlUrlTextField.setText(soap.getWsdlURL());
    GridPane.setHalignment(wsdrlUrlTextField, HPos.LEFT);
    Text endPointUrl = new Text("End point\nURL");
    operationList = new ComboBox<String>();
    serviceEndPointUrl = new MsstTextField();
    pane.add(wsdlUrl, 0, 1, 1, 1);
    pane.add(wsdrlUrlTextField, 1, 1, 5, 1);
    pane.add(loadWsdlButton, 6, 1, 2, 1);
    pane.add(endPointUrl, 0, 2);
    pane.add(serviceEndPointUrl, 1, 2, 5, 1);
    pane.add(operationList, 6, 2, 1, 1);
    saveButton = new MsstButton("Save Stub");
    requestTextArea = new MsstTextArea(soap.getRequestXml());
    responseTextArea = new MsstTextArea(soap.getResponseXml());
    requestTextArea.setMinWidth(300);
    responseTextArea.setMinWidth(300);
    GridPane.setHalignment(saveButton, HPos.CENTER);
    pane.add(requestXml, 0, 3, 1, 1);
    pane.add(requestTextArea, 1, 3, 2, 1);
    pane.add(responseXml, 3, 3, 1, 1);
    pane.add(responseTextArea, 4, 3, 3, 1);
    pane.add(saveButton, 0, 4, 5, 1);
    saveButton.setOnAction(saveButtonActionEvent);
    operationList.setOnAction(listActionListener);
    loadWsdlButton.setOnAction(loadWsdlActionListener);
    setFitToHeight(true);
    setFitToWidth(true);
    setContent(pane);
  }

  EventHandler<ActionEvent> saveButtonActionEvent = new EventHandler<ActionEvent>() {
    @Override
    public void handle(final ActionEvent t) {
      soap.getRequesStub().setUrl(serviceEndPointUrl.getText());
      soap.setSoapServiceWsdlUrl(wsdrlUrlTextField.getText());
      soap.setSoapEndPointUrl(serviceEndPointUrl.getText());
      soap.setRequestXml(requestTextArea.getText());
      soap.setResponseXml(responseTextArea.getText());
      soap.setLastUpdatedTimeStamp(Calendar.getInstance());
      soap.setSoapOperationName(operationList.getSelectionModel().getSelectedItem());
      soap.getResponseStub().setResponse(responseTextArea.getText());
      soap.getRequesStub().setRequestMethod("POST");
    }
  };
  EventHandler<ActionEvent> listActionListener = new EventHandler<ActionEvent>() {

    @Override
    public void handle(final ActionEvent event) {
      @SuppressWarnings("unchecked")
      ComboBox<String> ls = (ComboBox<String>) event.getSource();
      // ls.setCursor(new Cursor(Cursor.WAIT_CURSOR));
      String selectedItem = ls.getSelectionModel().getSelectedItem();
      System.out.println("The selected Item" + selectedItem);
      String str = wsdrlUrlTextField.getText();
      requestTextArea
          .setText(SoapUIUtil.getServiceRequestString(str, selectedItem, wireMockOptions));
      responseTextArea
          .setText(SoapUIUtil.getServiceResponseString(str, selectedItem, wireMockOptions));

    }
  };
  EventHandler<ActionEvent> loadWsdlActionListener = new EventHandler<ActionEvent>() {

    @Override
    public void handle(final ActionEvent event) {
      operationList.getItems().removeAll();
      for (String st : SoapUIUtil.getOperationName(wsdrlUrlTextField.getText().trim(),
          wireMockOptions)) {
        operationList.getItems().add(st);
      }
      for (String st : SoapUIUtil.getEndPoint(wsdrlUrlTextField.getText(), wireMockOptions)) {
        serviceEndPointUrl.setText(st);
      }
    }
  };
}
