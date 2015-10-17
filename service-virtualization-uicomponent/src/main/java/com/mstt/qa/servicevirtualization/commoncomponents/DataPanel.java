package com.mstt.qa.servicevirtualization.commoncomponents;

import static com.mstt.qa.servicevirtualization.servicevirtualizationutils.utils.CommonMethodsUtils.calendarToString;

import java.util.Calendar;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.Region;

import com.mstt.qa.servicevirtualization.servicevirtualizationutils.dto.MsttRequestDefinition;
import com.mstt.qa.servicevirtualization.servicevirtualizationutils.dto.MsttResponseDefinition;
import com.mstt.qa.servicevirtualization.servicevirtualizationutils.dto.ProjectDetailsDto;
import com.mstt.qa.servicevirtualization.servicevirtualizationutils.dto.RestServiceVirtualizeDto;
import com.mstt.qa.servicevirtualization.servicevirtualizationutils.dto.SoapServiceVirtualizeDto;
import com.mstt.qa.servicevirtualization.uicomponent.MsstGuiPckg;
import com.mstt.qa.servicevirtualization.uicomponent.utils.CommonUiActions;
import com.mstt.qa.servicevirtualization.uicomponent.utils.TableEntry;

public class DataPanel extends Tab {
  private static final String PRIORITY = "Priority";
  private static final String RESPONSE_DELAY = "Response Delay";
  public static final String LAST_UPDATED_TIMESTAMP = "Last updated timestamp";
  public static final String CREATED_TIMESTAMP = "Created timestamp";
  private static final String VIRTUAL_NAME = "Virtual Name";
  private static final String PROJECT_NAME = "Project Name";
  private static final String PROJECT_DETAILS = "Project Details";
  private static final String PROJECT_SHORT_NAME = "Project Short Name";
  private final TableView<TableEntry> dataTable;
  private static final String EMPTY_STRING = "";
  protected static final int KEY_COLOUMN_INDEX = 0;
  protected static final int VALUE_COLOUMN_INDEX = 1;
  private final ScrollPane tableView;
  private final TableColumn<TableEntry, String> elementName;
  private final TableColumn<TableEntry, String> elementValue;

  // https://docs.oracle.com/javafx/2/ui_controls/table-view.htm
  @SuppressWarnings("unchecked")
  public DataPanel(final String string) {
    super(string);
    tableView = new ScrollPane();
    tableView.setPrefSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
    tableView.setMaxSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
    // set(MsstGuiPckg.getInstance().getGuiPreferredSize().propertyDataPanelWithHeight(),
    // MsstGuiPckg.getInstance().getGuiPreferredSize().propertyDataPanelWithWidth());
    elementName = new TableColumn<>("element-name");
    elementValue = new TableColumn<>("element-value");
    elementName.setCellValueFactory(new PropertyValueFactory<TableEntry, String>("elementName"));
    elementValue.setCellValueFactory(new PropertyValueFactory<TableEntry, String>("elementValue"));
    dataTable = new TableView<TableEntry>();
    dataTable.getColumns().addAll(elementName, elementValue);
    dataTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    dataTable.setPrefHeight(MsstGuiPckg.getInstance().getGuiPreferredSize()
        .propertyDataPanelWithHeight());
    dataTable.setPrefWidth(MsstGuiPckg.getInstance().getGuiPreferredSize()
        .propertyDataPanelWithWidth());
    dataTable.setEditable(true);
    tableView.setContent(dataTable);;
    tableView.setFitToHeight(true);
    tableView.setFitToWidth(true);
    setContent(tableView);

  }



  public void removeEntries() {
    dataTable.getItems().clear();
  }

  public void populateDataTableModel() {
    removeEntries();
    TableEntry projectName = new TableEntry();
    TableEntry projectShortName = new TableEntry();
    TableEntry projectDescription = new TableEntry();
    projectName.setElementName(PROJECT_NAME);
    projectShortName.setElementName(PROJECT_SHORT_NAME);
    projectDescription.setElementName(PROJECT_DETAILS);
    dataTable.getItems().add(projectName);
    dataTable.getItems().add(projectShortName);
    dataTable.getItems().add(projectDescription);
  }

  public void populateDataTableModel(final ProjectDetailsDto projectDetailsDto) {
    removeEntries();
    TableEntry projectName = new TableEntry(PROJECT_NAME, projectDetailsDto.getProjectName());
    TableEntry projectShortName =
        new TableEntry(PROJECT_SHORT_NAME, projectDetailsDto.getProjectShortName());
    TableEntry projectDescription =
        new TableEntry(PROJECT_DETAILS, projectDetailsDto.getProjectDescription());
    dataTable.getItems().add(projectName);
    dataTable.getItems().add(projectShortName);
    dataTable.getItems().add(projectDescription);
    elementValue.setCellFactory(TextFieldTableCell.forTableColumn());
    elementValue.setOnEditCommit((final TableColumn.CellEditEvent<TableEntry, String> t) -> {
      updateTableDetails(projectDetailsDto,
          t.getTableView().getItems().get(t.getTablePosition().getRow()), t.getNewValue());
    });
  }

  protected void changeRootNodeName(final String value) {
    MsstGuiPckg.getInstance().getLstTree().rootNodeChanged(value);

  }

  public void populateDataTable(final RestServiceVirtualizeDto rstDto) {
    populateServiceData(rstDto.getRestServiceVirtualName(), rstDto.getCreationTimeStamp(),
        rstDto.getLastUpdatedTimeStamp(), rstDto.getResponseStub(), rstDto.getRequesStub());
    elementValue.setCellFactory(TextFieldTableCell.forTableColumn());
    elementValue.setOnEditCommit((final TableColumn.CellEditEvent<TableEntry, String> t) -> {
      updateTableDetails(rstDto, t.getTableView().getItems().get(t.getTablePosition().getRow()),
          t.getNewValue());
    });
  }


  protected void changeProjectPanelNodeName(final String restVirtualReferenceId, final String value) {
    MsstGuiPckg.getInstance().getLstTree().nodeChanged(restVirtualReferenceId, value);

  }

  protected void changeTabTitle(final String value, final String referenceId) {
    CenterTabbedPane centerJtab = MsstGuiPckg.getInstance().getCenterTabPane();
    centerJtab.getTab(referenceId).setText(value);
  }

  public void populateDataTable(final SoapServiceVirtualizeDto srv) {
    // dataTableModel.removeActionListeners();
    populateServiceData(srv.getSoapServiceVirtualizeName(), srv.getCreationTimeStamp(),
        srv.getLastUpdatedTimeStamp(), srv.getResponseStub(), srv.getRequesStub());
    elementValue.setCellFactory(TextFieldTableCell.forTableColumn());
    elementValue.setOnEditCommit((final TableColumn.CellEditEvent<TableEntry, String> t) -> {
      updateTableDetails(srv, t.getTableView().getItems().get(t.getTablePosition().getRow()),
          t.getNewValue());
    });

  }



  private void populateServiceData(final String serviceVirtualName,
      final Calendar creationTimeStamp, final Calendar lastUpdatedTimeStamp,
      final MsttResponseDefinition msttRes, final MsttRequestDefinition msttReq) {
    removeEntries();
    System.out.println("in the serviece populating" + serviceVirtualName);
    TableEntry virtualName = new TableEntry(VIRTUAL_NAME, serviceVirtualName);
    TableEntry fixedDelayinMilliSeconds = new TableEntry();
    TableEntry virtualCreation =
        new TableEntry(CREATED_TIMESTAMP, calendarToString(creationTimeStamp));
    TableEntry virtualLastUpdation =
        new TableEntry(LAST_UPDATED_TIMESTAMP, calendarToString(lastUpdatedTimeStamp));
    TableEntry priority = new TableEntry();
    fixedDelayinMilliSeconds.setElementName(RESPONSE_DELAY);
    if (msttRes.getFixedDelayMilliseconds() != null) {
      fixedDelayinMilliSeconds.setElementValue("" + msttRes.getFixedDelayMilliseconds());
    } else {
      fixedDelayinMilliSeconds.setElementValue(EMPTY_STRING);
    }
    priority.setElementName(PRIORITY);
    if (msttReq.getPriority() != null) {
      priority.setElementValue("" + msttReq.getPriority());
    } else {
      priority.setElementValue(EMPTY_STRING);
    }
    ObservableList<TableEntry> data =
        FXCollections.observableArrayList(virtualName, fixedDelayinMilliSeconds, priority,
            virtualCreation, virtualLastUpdation);
    dataTable.setItems(data);
    // dataTable.getItems().addAll();

  }

  private void updateTableDetails(final RestServiceVirtualizeDto rstDto,
      final TableEntry tableEntry, final String value) {
    String key = tableEntry.getElementName();
    System.out.println(key + "value : " + value);
    if (VIRTUAL_NAME.equals(key)) {
      rstDto.setRestServiceVirtualName(value);
      changeTabTitle(value, rstDto.getRestVirtualReferenceId());
      changeProjectPanelNodeName(rstDto.getRestVirtualReferenceId(), value);
    } else if (RESPONSE_DELAY.equals(key)) {
      rstDto.getResponseStub().setFixedDelayMilliseconds(Integer.parseInt(value));
    } else if (PRIORITY.equals(key)) {
      rstDto.getRequesStub().setPriority(Integer.parseInt(value));
    }
    CommonUiActions.projectChanged();

  }

  private void updateTableDetails(final SoapServiceVirtualizeDto srv, final TableEntry tableEntry,
      final String value) {
    String key = tableEntry.getElementName();
    System.out.println(key + "value : " + value);
    if (VIRTUAL_NAME.equals(key)) {
      srv.setSoapServiceVirtualizeName(value);
      changeTabTitle(value, srv.getSoapVirtualReferenceId());
      changeProjectPanelNodeName(srv.getSoapVirtualReferenceId(), value);
    } else if (RESPONSE_DELAY.equals(key)) {
      srv.getResponseStub().setFixedDelayMilliseconds(Integer.parseInt(value));
    } else if (PRIORITY.equals(key)) {
      srv.getRequesStub().setPriority(Integer.parseInt(value));
    }
    CommonUiActions.projectChanged();
  }

  private void updateTableDetails(final ProjectDetailsDto projectDetailsDto,
      final TableEntry tableEntry, final String value) {
    String key = tableEntry.getElementName();
    System.out.println(key + "value : " + value);
    if (PROJECT_NAME.equals(key)) {
      projectDetailsDto.setProjectName(value);
      changeRootNodeName(value);
    } else if (PROJECT_SHORT_NAME.equals(key)) {
      projectDetailsDto.setProjectShortName(value);
    } else if (PROJECT_DETAILS.equals(key)) {
      projectDetailsDto.setProjectDescription(value);
    }
    CommonUiActions.projectChanged();
  }

}
