package com.github.tomakehurst.wiremock.common;

public class FatalStartupException extends RuntimeException {

  private static final long serialVersionUID = 3403297358781801596L;

  public FatalStartupException(final String message) {
    super(message);
  }

  public FatalStartupException(final String message, final Throwable cause) {
    super(message, cause);
  }

  public FatalStartupException(final Throwable cause) {
    super(cause);
  }

}
