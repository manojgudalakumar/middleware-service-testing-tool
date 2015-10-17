package com.mstt.qa.servicevirtualization.uicomponent.panels;

import com.mstt.qa.servicevirtualization.servicevirtualizationutils.dto.RestServiceVirtualizeDto;

public class RestServiceVirtualUiPanel extends CenterPanel {
  private final VirtualDetailsTabbedPane virtualizeDetailsPane;
  private final RestServiceVirtualUiMainPanel virtualizeScrollPane;

  public RestServiceVirtualUiPanel(final RestServiceVirtualizeDto rst) {
    super(rst.getRestServiceVirtualName());
    setId(rst.getRestVirtualReferenceId());
    virtualizeDetailsPane =
        new VirtualDetailsTabbedPane(rst.getRequesStub(), rst.getResponseStub());
    virtualizeScrollPane = new RestServiceVirtualUiMainPanel(rst, virtualizeDetailsPane);
    st.getItems().add(virtualizeScrollPane);
    st.getItems().add(virtualizeDetailsPane);
    setDividerPosition();
  }
}
