package com.github.tomakehurst.wiremock.global;

public interface RequestDelayControl {

  void setDelay(int milliseconds);

  void clearDelay();

  void delayIfRequired() throws InterruptedException;
}
