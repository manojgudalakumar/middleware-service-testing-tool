package com.mstt.qa.servicevirtualization.uicomponent.utils;

import javafx.beans.property.SimpleStringProperty;

public class TableEntry {
  SimpleStringProperty elementName;
  SimpleStringProperty elementValue;

  public TableEntry(final String elementName, final String elementValue) {
    this.elementName = new SimpleStringProperty(elementName);
    this.elementValue = new SimpleStringProperty(elementValue);
  }

  public TableEntry() {}

  public String getElementName() {
    return elementName.get();
  }

  public void setElementName(final SimpleStringProperty elementName) {
    this.elementName = elementName;
  }

  public void setElementName(final String elementName) {
    this.elementName = new SimpleStringProperty(elementName);
  }

  public String getElementValue() {
    return elementValue.get();
  }

  public void setElementValue(final SimpleStringProperty elementValue) {
    this.elementValue = elementValue;
  }

  public void setElementValue(final String elementValue) {
    this.elementValue = new SimpleStringProperty(elementValue);
  }
}
