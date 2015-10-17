package com.mstt.qa.servicevirtualization.uicomponent.treegui;

import static org.apache.commons.collections.CollectionUtils.isNotEmpty;

import java.awt.Dimension;
import java.util.List;

import javafx.scene.control.ScrollPane;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

import com.mstt.qa.servicevirtualization.servicevirtualizationutils.dto.RestServiceVirtualizeDto;
import com.mstt.qa.servicevirtualization.servicevirtualizationutils.dto.ServiceTestDetailsDto;
import com.mstt.qa.servicevirtualization.servicevirtualizationutils.dto.SoapServiceVirtualizeDto;
import com.mstt.qa.servicevirtualization.servicevirtualizationutils.dto.UserDefinedProjectDetailsDto;
import com.mstt.qa.servicevirtualization.uicomponent.MsstGuiPckg;
import com.mstt.qa.servicevirtualization.uicontrols.MsstTreeItem;

public class LoadServiceTree extends ScrollPane {
  TreeView<String> tree;
  TreeItem<String> rootNode;
  TreeItem<String> selectedNode;
  TreeItem<String> soapServiceRootNode;
  TreeItem<String> restServiceRootNode;
  TreeItem<String> serviceTestRootNode;

  public LoadServiceTree(final UserDefinedProjectDetailsDto usrDto, final TreeItem<String> rootNode) {
    System.out.println(MsstGuiPckg.getInstance().getGuiPreferredSize().treePanelWithHeight() + "\t"
        + MsstGuiPckg.getInstance().getGuiPreferredSize().treePanelWithWidth());
    setPrefSize(MsstGuiPckg.getInstance().getGuiPreferredSize().treePanelWithHeight(), MsstGuiPckg
        .getInstance().getGuiPreferredSize().treePanelWithWidth());
    setFitToWidth(true);
    setFitToHeight(true);
    this.rootNode = rootNode;
    tree = new TreeView<String>();
    // tree.setEditable(true);
    tree.setRoot(rootNode);
    tree.setContextMenu(new TreePopup());
    tree.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    // tree.getSelectionModel().selectedItemProperty().addListener(new TreeMenuListener());
    tree.setOnMouseClicked(new TreeMenuListener());
    generateTree(usrDto, rootNode);
    tree.setPrefHeight(getPrefHeight());
    tree.setPrefWidth(getPrefWidth());
    setContent(tree);
  }

  public void regenerateTree() {
    MsstGuiPckg pck = MsstGuiPckg.getInstance();
    rootNode.getChildren().clear();
    generateTree(pck.getUsrDto(), rootNode);
    rootNode.setValue(pck.getUsrDto().getProjectDetails().getProjectName());
  }

  private void generateTree(final UserDefinedProjectDetailsDto usrDto,
      final TreeItem<String> rootNode2) {
    TreeItem<String> dftMutableTreeSv = new TreeItem<String>("Service Virtualization");
    soapServiceRootNode = new TreeItem<String>("Soap Service Virtualization");
    addSoapSvList(usrDto.getSoapServiceVirtualizeDtolist(), soapServiceRootNode);
    restServiceRootNode = new TreeItem<String>("Rest Virtualization");
    addRestSvlist(usrDto.getRestServiceVirtualizeDtolist(), restServiceRootNode);
    serviceTestRootNode = new TreeItem<String>("Service Test");
    addServiceTestDetails(usrDto.getServiceTestDetails());
    dftMutableTreeSv.getChildren().add(soapServiceRootNode);
    dftMutableTreeSv.getChildren().add(restServiceRootNode);
    rootNode2.getChildren().add(dftMutableTreeSv);
    rootNode2.getChildren().add(serviceTestRootNode);
  }

  private void addServiceTestDetails(final List<ServiceTestDetailsDto> serviceTestDetails) {
    for (ServiceTestDetailsDto serviceTestDetailsDto : serviceTestDetails) {
      MsstTreeItem dft =
          new MsstTreeItem(serviceTestDetailsDto, serviceTestDetailsDto.getServiceTestName(),
              serviceTestDetailsDto.getServiceTestReferenceId());
      addChildServiceTestDetails(dft, serviceTestDetailsDto);
      serviceTestRootNode.getChildren().add(dft);
    }

  }

  private void addChildServiceTestDetails(final TreeItem<String> dft,
      final ServiceTestDetailsDto serviceTestDetails) {
    if (isNotEmpty(serviceTestDetails.getServiceTestChildDetailslist())) {
      for (ServiceTestDetailsDto childSrvDto : serviceTestDetails.getServiceTestChildDetailslist()) {
        MsstTreeItem serviceTestTreeNode =
            new MsstTreeItem(childSrvDto, childSrvDto.getServiceTestName(),
                childSrvDto.getServiceTestReferenceId());
        dft.getChildren().add(serviceTestTreeNode);
        if (isNotEmpty(childSrvDto.getServiceTestChildDetailslist())) {
          addChildServiceTestDetails(serviceTestTreeNode, childSrvDto);
        } else {
          continue;
        }
      }

    }

  }

  private void addRestSvlist(final List<RestServiceVirtualizeDto> restServiceVirtualizeDtolist,
      final TreeItem<String> restServiceRootNode2) {
    for (RestServiceVirtualizeDto rst : restServiceVirtualizeDtolist) {
      MsstTreeItem rstMut =
          new MsstTreeItem(rst, rst.getRestServiceVirtualName(), rst.getRestVirtualReferenceId());
      restServiceRootNode2.getChildren().add(rstMut);
    }
  }

  private void addSoapSvList(final List<SoapServiceVirtualizeDto> soapServiceVirtualizeDtolist,
      final TreeItem<String> soapServiceRootNode2) {
    for (SoapServiceVirtualizeDto soap : soapServiceVirtualizeDtolist) {
      MsstTreeItem soapMut =
          new MsstTreeItem(soap, soap.getSoapServiceVirtualizeName(),
              soap.getSoapVirtualReferenceId());
      soapServiceRootNode2.getChildren().add(soapMut);
    }
  }

  public Dimension getMinimumSize() {
    return new Dimension(200, 400);
  }

  public Dimension getPreferredSize() {
    return new Dimension(400, 400);
  }

  public ScrollPane getTreeJpanel() {
    return this;
  }

  public void reload() {
    regenerateTree();
  }

  public void selectnode(final String referenceId) {
    tree.getSelectionModel().select(getNode(referenceId));
  }

  private TreeItem<String> getNode(final String referenceId) {
    TreeItem<String> treNode = getSoapVirtualizeNode(referenceId);
    if (treNode != null) {
      return treNode;
    }
    treNode = getRestVirtualizeNode(referenceId);
    if (treNode != null) {
      return treNode;
    }
    treNode = getServiceTestDetailsNode(referenceId);
    if (treNode != null) {
      return treNode;
    }
    return null;
  }

  private TreeItem<String> getSoapVirtualizeNode(final String referenceId) {
    int cc;
    cc = soapServiceRootNode.getChildren().size();
    for (int i = 0; i < cc; i++) {
      MsstTreeItem child = (MsstTreeItem) soapServiceRootNode.getChildren().get(i);
      Object obj = child.getUserObject();
      if (obj instanceof SoapServiceVirtualizeDto) {
        SoapServiceVirtualizeDto sp = (SoapServiceVirtualizeDto) child.getUserObject();
        if (sp.getSoapVirtualReferenceId().equals(referenceId))
          return child;
      }
    }
    return null;

  }

  private TreeItem<String> getServiceTestDetailsNode(final String referenceId) {
    int cc;
    cc = serviceTestRootNode.getChildren().size();
    for (int i = 0; i < cc; i++) {
      MsstTreeItem child = (MsstTreeItem) serviceTestRootNode.getChildren().get(i);
      Object obj = child.getUserObject();
      if (obj instanceof ServiceTestDetailsDto) {
        ServiceTestDetailsDto sp = (ServiceTestDetailsDto) child.getUserObject();
        if (sp.getServiceTestReferenceId().equals(referenceId))
          return child;
        else {
          return getServiceTestDetailsNode(child, referenceId);
        }
      }
    }
    return null;
  }

  private TreeItem<String> getServiceTestDetailsNode(final TreeItem<String> child,
      final String referenceId) {
    int cc;
    cc = child.getChildren().size();
    for (int i = 0; i < cc; i++) {
      MsstTreeItem grandChild = (MsstTreeItem) child.getChildren().get(i);

      Object obj = grandChild.getUserObject();
      if (obj instanceof ServiceTestDetailsDto) {
        ServiceTestDetailsDto sp = (ServiceTestDetailsDto) grandChild.getUserObject();
        if (sp.getServiceTestReferenceId().equals(referenceId))
          return child;
        else {
          return getServiceTestDetailsNode(grandChild, referenceId);
        }
      }
    }
    return null;
  }

  private TreeItem<String> getRestVirtualizeNode(final String referenceId) {
    int cc;
    cc = restServiceRootNode.getChildren().size();
    for (int i = 0; i < cc; i++) {
      MsstTreeItem child = (MsstTreeItem) restServiceRootNode.getChildren().get(i);
      Object obj = child.getUserObject();
      if (obj instanceof RestServiceVirtualizeDto) {
        RestServiceVirtualizeDto rest = (RestServiceVirtualizeDto) child.getUserObject();
        if (rest.getRestVirtualReferenceId().equals(referenceId))
          return child;
      }
    }
    return null;
  }

  public void nodeChanged(final String referenceId, final String value) {
    TreeItem<String> treeItem = getNode(referenceId);
    treeItem.setValue(value);
  }

  public void insertSoapServiceNode(final SoapServiceVirtualizeDto soapDto) {
    MsstTreeItem soapServiceNode =
        new MsstTreeItem(soapDto, soapDto.getSoapServiceVirtualizeName(),
            soapDto.getSoapVirtualReferenceId());
    soapServiceRootNode.getChildren().add(soapServiceNode);
    selectnode(soapDto.getSoapVirtualReferenceId());
  }

  public void insertRestServiceNode(final RestServiceVirtualizeDto restDto) {
    MsstTreeItem restServiceNode =
        new MsstTreeItem(restDto, restDto.getRestServiceVirtualName(),
            restDto.getRestVirtualReferenceId());
    restServiceRootNode.getChildren().add(restServiceNode);
    selectnode(restDto.getRestVirtualReferenceId());
  }

  public void rootNodeChanged(final String value) {
    rootNode.setValue(value);
  }

  public ScrollPane getTreepanel() {
    return this;
  }
}
