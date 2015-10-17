package com.mstt.qa.servicevirtualization.uicomponent.panels;

import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.scene.control.TabPane;

import com.mstt.qa.servicevirtualization.commoncomponents.DataPanel;
import com.mstt.qa.servicevirtualization.commoncomponents.PropertyPanel;
import com.mstt.qa.servicevirtualization.servicevirtualizationutils.dto.RestServiceVirtualizeDto;
import com.mstt.qa.servicevirtualization.servicevirtualizationutils.dto.SoapServiceVirtualizeDto;
import com.mstt.qa.servicevirtualization.servicevirtualizationutils.dto.UserDefinedProjectDetailsDto;
import com.mstt.qa.servicevirtualization.servicevirtualizationutils.utils.CommonMethodsUtils;
import com.mstt.qa.servicevirtualization.uicomponent.MsstGuiPckg;

public class DataPropertyPanel extends TabPane {
  final static Logger logger = CommonMethodsUtils.getLogger(DataPropertyPanel.class.getName());
  private final PropertyPanel propPanel;
  private final DataPanel dataPanel;

  public DataPropertyPanel() {
    logger.log(Level.FINE, "this is the data propetry");
    logger.info("this is infor");
    propPanel = new PropertyPanel("Propties");
    dataPanel = new DataPanel("Data");
    setPrefSize(MsstGuiPckg.getInstance().getGuiPreferredSize().propertyDataPanelWithHeight(),
        MsstGuiPckg.getInstance().getGuiPreferredSize().propertyDataPanelWithWidth());
    setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
  }

  public void addPropertyPanel() {
    getTabs().add(propPanel);
  }

  public void addDataPanel() {
    getTabs().add(dataPanel);
  }

  public void LoadDefaultContent(final UserDefinedProjectDetailsDto usrDto) {
    dataPanel.populateDataTableModel(usrDto.getProjectDetails());
    propPanel.LoadDefaultContent(usrDto);
  }

  public void resetPanel() {
    emptyPanel();
    dataPanel.populateDataTableModel();
  }

  public void emptyPanel() {
    propPanel.removeEntries();
    dataPanel.removeEntries();
  }

  public void populatePropertyPanel(final RestServiceVirtualizeDto obj) {
    propPanel.populateProperTable(obj);
    dataPanel.populateDataTable(obj);
  }

  public void populatePropertyPanel(final SoapServiceVirtualizeDto obj) {
    dataPanel.populateDataTable(obj);
    propPanel.populateProperTable(obj);
  }

}
