package com.mstt.qa.servicevirtualization.commoncomponents;

import java.util.HashMap;
import java.util.Map;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

import com.mstt.qa.servicevirtualization.servicevirtualizationutils.dto.RestServiceVirtualizeDto;
import com.mstt.qa.servicevirtualization.servicevirtualizationutils.dto.SoapServiceVirtualizeDto;
import com.mstt.qa.servicevirtualization.servicevirtualizationutils.dto.UserDefinedProjectDetailsDto;
import com.mstt.qa.servicevirtualization.servicevirtualizationutils.userproject.HandleProjectServiceData;
import com.mstt.qa.servicevirtualization.uicomponent.MsstGuiPckg;
import com.mstt.qa.servicevirtualization.uicomponent.panels.CenterPanel;
import com.mstt.qa.servicevirtualization.uicomponent.panels.RestServiceVirtualUiPanel;
import com.mstt.qa.servicevirtualization.uicomponent.panels.SoapServiceVirtualUiPanel;

public class CenterTabbedPane extends TabPane {
  private static final int NO_TAB_EXIST_INDEX = -1;
  private final Map<String, CenterPanel> mapOfTabsOpened;

  public CenterTabbedPane(final int top) {
    // super(top);
    setPrefHeight(MsstGuiPckg.getInstance().getGuiPreferredSize().virtualMainPanelWithHeight());
    setPrefWidth(MsstGuiPckg.getInstance().getGuiPreferredSize().virtualMainPanelWithWidth());
    mapOfTabsOpened = new HashMap<String, CenterPanel>();
    // addChangeListener(changeListener);
    getSelectionModel().selectedItemProperty().addListener(tabChangeLisenter);
  }

  public void resetPanel() {
    getChildren().removeAll();
  }

  public void showTab(final RestServiceVirtualizeDto rstDto) {
    int index = getTabIndex(rstDto.getRestVirtualReferenceId());
    if (index > NO_TAB_EXIST_INDEX) {
      getSelectionModel().select(index);
    } else {
      RestServiceVirtualUiPanel rstPanel = new RestServiceVirtualUiPanel(rstDto);
      mapOfTabsOpened.put(rstDto.getRestVirtualReferenceId(), rstPanel);
      getTabs().add(rstPanel);
      index = getTabIndex(rstDto.getRestVirtualReferenceId());
      // setTabComponentAt(index, new AddCloseButtonTab(this, rstDto.getRestServiceVirtualName(),
      // rstPanel, mapOfTabsOpened));
      getSelectionModel().select(index);
    }

  }

  public void showTab(final SoapServiceVirtualizeDto srv) {
    int index = getTabIndex(srv.getSoapVirtualReferenceId());

    if (index > NO_TAB_EXIST_INDEX) {
      getSelectionModel().select(index);
    } else {
      SoapServiceVirtualUiPanel rstPanel =
          new SoapServiceVirtualUiPanel(srv, MsstGuiPckg.getInstance().getWireMockOptions());
      mapOfTabsOpened.put(srv.getSoapVirtualReferenceId(), rstPanel);
      getTabs().add(rstPanel);
      index = getTabIndex(srv.getSoapVirtualReferenceId());
      // setTabComponentAt(index, new AddCloseButtonTab(this, srv.getSoapServiceVirtualizeName(),
      // rstPanel, mapOfTabsOpened));
      getSelectionModel().select(index);
    }

  }

  public void addNewRestPanel(final RestServiceVirtualizeDto rst) {
    MsstGuiPckg pck = MsstGuiPckg.getInstance();
    UserDefinedProjectDetailsDto usrd = pck.getUsrDto();
    usrd.addRestServiceVirtualize(rst);
    pck.getLstTree().insertRestServiceNode(rst);
    showTab(rst);

  }

  public void addNewSoapPanel(final SoapServiceVirtualizeDto soapDto) {
    MsstGuiPckg pck = MsstGuiPckg.getInstance();
    UserDefinedProjectDetailsDto usrd = pck.getUsrDto();
    usrd.addSoapServiceVirtualize(soapDto);
    pck.getLstTree().insertSoapServiceNode(soapDto);
    showTab(soapDto);

  }

  public Tab getTab(final String referenceid) {
    for (int i = 0; i < getTabs().size(); i++) {
      if (referenceid.equalsIgnoreCase(getTabs().get(i).getId()))
        return getTabs().get(i);
    }
    return null;
  }

  public int getTabIndex(final String referenceid) {
    for (int i = 0; i < getTabs().size(); i++) {
      if (referenceid.equalsIgnoreCase(getTabs().get(i).getId()))
        return i;
    }
    return NO_TAB_EXIST_INDEX;
  }

  // ChangeListener changeListener = new ChangeListener() {
  // @Override
  // public void stateChanged(final ChangeEvent changeEvent) {
  // JTabbedPane sourceTabbedPane = (JTabbedPane) changeEvent.getSource();
  // int index = sourceTabbedPane.getSelectedIndex();
  // MsstGuiPckg pmwtPackage = MsstGuiPckg.getInstance();
  // if (index != NO_TAB_EXIST_INDEX) {
  // String referenceId = sourceTabbedPane.getTitleAt(index);
  // pmwtPackage.getLstTree().selectnode(referenceId);
  // HandleProjectServiceData hnd = new HandleProjectServiceData();
  // Object obj = hnd.getUserObject(referenceId, pmwtPackage.getUsrDto());
  // if (obj instanceof RestServiceVirtualizeDto) {
  // pmwtPackage.getPropPanel().populatePropertyPanel((RestServiceVirtualizeDto) obj);
  // }
  // if (obj instanceof SoapServiceVirtualizeDto) {
  // pmwtPackage.getPropPanel().populatePropertyPanel((SoapServiceVirtualizeDto) obj);
  // }
  // } else
  // pmwtPackage.getPropPanel().emptyPanel();
  // }
  // };
  ChangeListener<Tab> tabChangeLisenter = new ChangeListener<Tab>() {
    @Override
    public void changed(final ObservableValue<? extends Tab> ov, final Tab t, final Tab t1) {
      MsstGuiPckg pmwtPackage = MsstGuiPckg.getInstance();
      pmwtPackage.getLstTree().selectnode(t1.getId());
      HandleProjectServiceData hnd = new HandleProjectServiceData();
      Object obj = hnd.getUserObject(t1.getId(), pmwtPackage.getUsrDto());
      if (obj instanceof RestServiceVirtualizeDto) {
        pmwtPackage.getPropPanel().populatePropertyPanel((RestServiceVirtualizeDto) obj);
      }
      if (obj instanceof SoapServiceVirtualizeDto) {
        pmwtPackage.getPropPanel().populatePropertyPanel((SoapServiceVirtualizeDto) obj);
      } else {
        pmwtPackage.getPropPanel().emptyPanel();
      }
    }
  };
}
