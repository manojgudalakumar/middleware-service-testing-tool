package com.mstt.qa.servicevirtualization.uicontrols;

import javafx.scene.control.TreeItem;

public class MsstTreeItem extends TreeItem<String> {
  private final Object userObj;
  private final String referenceId;

  public MsstTreeItem(final Object userObj, final String nodeName, final String referenceId) {
    super(nodeName);
    this.userObj = userObj;
    this.referenceId = referenceId;
  }

  public Object getUserObject() {
    return userObj;
  }

  public String getReferenceId() {
    return referenceId;
  }

}
