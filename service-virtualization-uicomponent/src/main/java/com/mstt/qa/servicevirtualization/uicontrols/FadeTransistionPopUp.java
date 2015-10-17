package com.mstt.qa.servicevirtualization.uicontrols;

import javafx.animation.Transition;
import javafx.stage.Popup;

public class FadeTransistionPopUp extends Transition {
  private final Popup popup;

  public FadeTransistionPopUp(final Popup popup) {
    this.popup = popup;
  }


  @Override
  protected void interpolate(final double frac) {
    popup.setOpacity(frac);
  }
}
