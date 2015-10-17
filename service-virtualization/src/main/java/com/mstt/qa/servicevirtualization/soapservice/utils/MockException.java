package com.mstt.qa.servicevirtualization.soapservice.utils;

public class MockException extends RuntimeException {

  private static final long serialVersionUID = -6134253185981847980L;
  private String reason;

  public MockException() {

  }

  public MockException(final String reason) {
    this.reason = reason;
  }

  @Override
  public String getMessage() {
    return reason;
  }
}
