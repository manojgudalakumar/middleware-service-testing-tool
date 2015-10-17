package com.mstt.qa.servicevirtualization.uicomponent.treegui;

import static com.mstt.qa.servicevirtualization.servicevirtualizationutils.utils.CommonMethodsUtils.isNotNull;

import java.util.logging.Logger;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;

import com.mstt.qa.servicevirtualization.commoncomponents.CenterTabbedPane;
import com.mstt.qa.servicevirtualization.servicevirtualizationutils.dto.MsttRequestDefinition;
import com.mstt.qa.servicevirtualization.servicevirtualizationutils.dto.MsttResponseDefinition;
import com.mstt.qa.servicevirtualization.servicevirtualizationutils.dto.RestServiceVirtualizeDto;
import com.mstt.qa.servicevirtualization.servicevirtualizationutils.dto.SoapServiceVirtualizeDto;
import com.mstt.qa.servicevirtualization.servicevirtualizationutils.utils.CommonMethodsUtils;
import com.mstt.qa.servicevirtualization.uicomponent.MsstGuiPckg;
import com.mstt.qa.servicevirtualization.uicomponent.panels.DataPropertyPanel;
import com.mstt.qa.servicevirtualization.uicomponent.utils.MsttStubHandler;
import com.mstt.qa.servicevirtualization.uicontrols.MsstTreeItem;

public class TreeMenuListener implements EventHandler<MouseEvent>, ChangeListener<TreeItem<String>> {
  final static Logger logger = CommonMethodsUtils.getLogger(TreeMenuListener.class.getName());

  public TreeMenuListener() {}

  private void loadRestServiceVirtualizpanel(final RestServiceVirtualizeDto rstDto) {

    MsstGuiPckg pck = MsstGuiPckg.getInstance();
    MsttRequestDefinition msttReq =
        getRequestDef(rstDto.getRestVirtualMappingFileName(), pck.getmsttStubHandler());
    MsttResponseDefinition msttRes =
        getResponseDef(rstDto.getRestVirtualMappingFileName(), pck.getmsttStubHandler());
    rstDto.setRequesStub(msttReq);
    rstDto.setResponseStub(msttRes);
    CenterTabbedPane cntr = pck.getCenterTabPane();
    cntr.showTab(rstDto);

    DataPropertyPanel prop = pck.getPropPanel();
    prop.populatePropertyPanel(rstDto);

  }

  private MsttResponseDefinition getResponseDef(final String restVirtualMappingFileName,
      final MsttStubHandler msttStubHandler) {
    return msttStubHandler.readResponseDefinitionFromMappingFile(restVirtualMappingFileName);
  }

  private MsttRequestDefinition getRequestDef(final String restVirtualMappingFileName,
      final MsttStubHandler msttStubHandler) {
    return msttStubHandler.readRequestDefinitionFromMappingFile(restVirtualMappingFileName);
  }

  private void loadSoapServiceVirtualizpanel(final SoapServiceVirtualizeDto srv) {
    MsstGuiPckg pck = MsstGuiPckg.getInstance();
    CenterTabbedPane cntr = pck.getCenterTabPane();
    MsttRequestDefinition msttReq =
        getRequestDef(srv.getVirtualizeServiceMappingFileName(), pck.getmsttStubHandler());
    MsttResponseDefinition msttRes =
        getResponseDef(srv.getVirtualizeServiceMappingFileName(), pck.getmsttStubHandler());
    srv.setRequesStub(msttReq);
    srv.setResponseStub(msttRes);
    cntr.showTab(srv);
    DataPropertyPanel prop = pck.getPropPanel();
    prop.populatePropertyPanel(srv);

  }

  @Override
  public void changed(final ObservableValue<? extends TreeItem<String>> arg0,
      final TreeItem<String> arg1, final TreeItem<String> arg2) {
    // TODO Auto-generated method stub

  }

  @SuppressWarnings("unchecked")
  @Override
  public void handle(final MouseEvent event) {
    if (event.getClickCount() == 2 && !event.isConsumed()) {
      TreeView<String> treeView = (TreeView<String>) event.getSource();
      if (treeView.getSelectionModel().getSelectedItem() instanceof MsstTreeItem) {
        MsstTreeItem node = (MsstTreeItem) treeView.getSelectionModel().getSelectedItem();
        if (isNotNull(node)) {
          if (node.isLeaf()) {
            logger.info("loading tab details");
            Object obj = node.getUserObject();
            if (obj instanceof SoapServiceVirtualizeDto) {
              loadSoapServiceVirtualizpanel((SoapServiceVirtualizeDto) node.getUserObject());
            }
            if (obj instanceof RestServiceVirtualizeDto) {
              loadRestServiceVirtualizpanel((RestServiceVirtualizeDto) node.getUserObject());
            }
          }
        }
      } else {
        TreeItem<String> node = treeView.getSelectionModel().getSelectedItem();
        MsstGuiPckg pck = MsstGuiPckg.getInstance();
        if (node.getValue().equals(pck.getUsrDto().getProjectDetails().getProjectName())) {
          DataPropertyPanel prop = pck.getPropPanel();
          prop.LoadDefaultContent(pck.getUsrDto());
        }

      }
    }
  }
}
