package com.mstt.qa.servicevirtualization.uicomponent.utils;

public class GuiPreferredSize {
  private final double screenHeight;
  private final double screenWidht;

  public double getScreenHeight() {
    return screenHeight;
  }

  public double getScreenWidht() {
    return screenWidht;
  }

  public GuiPreferredSize(final double screenHeight, final double screenWidht) {
    System.out.println(screenHeight + "\t" + screenWidht);
    this.screenHeight = screenHeight;
    this.screenWidht = screenWidht;
  }

  public double treePanelWithHeight() {
    return screenHeight;
  }

  public double treePanelWithWidth() {
    return (screenWidht * 30) / 100;
  }

  public double loggerPanelWithHeight() {
    return (screenHeight * 15) / 100;
  }

  public double loggerPanelWithWidth() {
    return (screenWidht * 80) / 100;
  }

  public double propertyDataPanelWithHeight() {
    return (screenHeight * 85) / 100;
  }

  public double propertyDataPanelWithWidth() {
    return (screenWidht * 15) / 100;
  }

  public double centerPanelWithHeight() {
    return (screenHeight * 85) / 100;
  }

  public double centerPanelWithWidth() {
    return (screenWidht * 80) / 100;
  }

  public double virtualDetailsPanelWithHeight() {
    return (screenHeight * 35) / 100;
  }

  public double virtualDetailsPanelWithWidth() {
    return (screenWidht * 50) / 100;
  }

  public double virtualMainPanelWithHeight() {
    return (screenHeight * 50) / 100;
  }

  public double virtualMainPanelWithWidth() {
    return (screenWidht * 50) / 100;
  }

}
