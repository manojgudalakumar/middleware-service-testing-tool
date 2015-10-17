package com.mstt.qa.servicevirtualization.uicomponent.treegui;

import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

import com.mstt.qa.servicevirtualization.servicevirtualizationutils.dto.ProjectDetailsDto;
import com.mstt.qa.servicevirtualization.servicevirtualizationutils.dto.RestServiceVirtualizeDto;
import com.mstt.qa.servicevirtualization.servicevirtualizationutils.dto.ServiceTestDetailsDto;
import com.mstt.qa.servicevirtualization.servicevirtualizationutils.dto.SoapServiceVirtualizeDto;
import com.mstt.qa.servicevirtualization.uicomponent.utils.ConstantsUtils;

public class MsttTreeRendering extends DefaultTreeCellRenderer {
  private static final String SERVICE_TEST = "Service Test";
  private static final String REST_VIRTUALIZATION = "Rest Virtualization";
  private static final String SOAP_SERVICE_VIRTUALIZATION = "Soap Service Virtualization";
  private static final String SERVICE_VIRTUALIZATION = "Service Virtualization";
  private static final long serialVersionUID = 854577664778633634L;

  @Override
  public Component getTreeCellRendererComponent(final JTree tree, final Object value,
      final boolean sel, final boolean expanded, final boolean leaf, final int row,
      final boolean p_hasFocus) {
    setSize(200, 400);
    String nodeName = null;
    String nodeType = null;
    DefaultMutableTreeNode dft = (DefaultMutableTreeNode) value;
    Object obj = dft.getUserObject();
    if (obj instanceof SoapServiceVirtualizeDto) {
      SoapServiceVirtualizeDto soapV = (SoapServiceVirtualizeDto) obj;
      nodeName = soapV.getSoapServiceVirtualizeName();
      nodeType = "Soap";
    } else if (obj instanceof RestServiceVirtualizeDto) {
      RestServiceVirtualizeDto restDto = (RestServiceVirtualizeDto) obj;
      nodeName = restDto.getRestServiceVirtualName();
      nodeType = "Rest";
    } else if (obj instanceof ServiceTestDetailsDto) {
      ServiceTestDetailsDto servTestDto = (ServiceTestDetailsDto) obj;
      nodeName = servTestDto.getServiceTestName();
      nodeType = "ServiceTest";
    } else if (obj instanceof ProjectDetailsDto) {
      nodeName = ((ProjectDetailsDto) obj).getProjectName();
      nodeType = ((ProjectDetailsDto) obj).getProjectName();
    } else if (obj instanceof String) {
      nodeName = (String) obj;
      nodeType = (String) obj;
    }

    super.getTreeCellRendererComponent(tree, nodeName, sel, expanded, leaf, row, p_hasFocus);
    ImageIcon ic = getImageIcon(nodeType, leaf);
    if (ic != null) {
      setIcon(ic);
      setDisabledIcon(ic);
    }
    return this;
  }

  private ImageIcon getImageIcon(final String nodeType, final boolean leaf) {
    ImageIcon img = null;
    if (nodeType != null) {
      if (nodeType.equals("Soap")) {
        img = new ImageIcon(ConstantsUtils.SOAP_IMG_ICON_PATH);
      } else if (nodeType.equals("Rest")) {
        img = new ImageIcon(ConstantsUtils.REST_IMG_ICON_PATH);
      } else if (nodeType.equals("ServiceTest")) {
        if (leaf) {
          img = new ImageIcon(ConstantsUtils.SERVICE_DETAILS_SUITE_IMG_ICON_PATH);
        } else {
          img = new ImageIcon(ConstantsUtils.SERVICE_DETAILS_TEST_IMG_ICON_PATH);
        }
      } else if (nodeType.equals(SERVICE_VIRTUALIZATION)) {
        img = new ImageIcon(ConstantsUtils.SV_IMG_ICON_PATH);
      } else if (nodeType.equals(SOAP_SERVICE_VIRTUALIZATION)) {
        img = new ImageIcon(ConstantsUtils.SOAP_ROOT_IMG_ICON_PATH);
      } else if (nodeType.equals(REST_VIRTUALIZATION)) {
        img = new ImageIcon(ConstantsUtils.REST_ROOT_IMG_ICON_PATH);
      } else if (nodeType.equals(SERVICE_TEST)) {
        img = new ImageIcon(ConstantsUtils.SERVICE_DETAILS_ROOT_IMG_ICON_PATH);
      }
    } else {
      img = new ImageIcon(ConstantsUtils.DEFAULT_IMG_ICON_PATH);
    }
    return img;
  }
}
