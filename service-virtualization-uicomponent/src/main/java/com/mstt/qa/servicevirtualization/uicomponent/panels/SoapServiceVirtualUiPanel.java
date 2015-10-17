package com.mstt.qa.servicevirtualization.uicomponent.panels;

import com.mstt.qa.servicevirtualization.servicevirtualizationutils.dto.SoapServiceVirtualizeDto;
import com.mstt.qa.servicevirtualization.uicomponent.utils.VirtualizationUiOptions;

public class SoapServiceVirtualUiPanel extends CenterPanel {
  private final VirtualDetailsTabbedPane virtualizeDetailsPane;
  private final SoapServiceVirtualUiMainPanel virtualizeScrollPane;
  final static int maxGap = 20;

  public SoapServiceVirtualUiPanel(final SoapServiceVirtualizeDto srv,
      final VirtualizationUiOptions wireMockOptions) {
    super(srv.getSoapServiceVirtualizeName());
    setId(srv.getSoapVirtualReferenceId());
    virtualizeDetailsPane =
        new VirtualDetailsTabbedPane(srv.getRequesStub(), srv.getResponseStub());
    virtualizeScrollPane = new SoapServiceVirtualUiMainPanel(srv, wireMockOptions);
    st.getItems().add(virtualizeScrollPane);
    st.getItems().add(virtualizeDetailsPane);
    setDividerPosition();
  }
}
