package com.mstt.qa.servicevirtualization.uicomponent.panels;

import javafx.collections.ObservableList;
import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;

public class CenterPanel extends Tab {
  protected Button saveStub;
  protected boolean stubChanged;
  SplitPane st;

  public CenterPanel(final String tabName) {
    super(tabName);
    st = new SplitPane();
    st.setOrientation(Orientation.VERTICAL);
    setContent(st);
  }

  protected void setDividerPosition() {
    ObservableList<SplitPane.Divider> dividers = st.getDividers();
    dividers.get(0).setPosition(0.60);
  }

  public boolean getStubChange() {
    return stubChanged;
  }

  public void setStubChange(final boolean stubChange) {
    stubChanged = stubChange;
  }
}
