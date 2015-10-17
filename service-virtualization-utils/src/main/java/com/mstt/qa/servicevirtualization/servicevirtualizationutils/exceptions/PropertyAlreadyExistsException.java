package com.mstt.qa.servicevirtualization.servicevirtualizationutils.exceptions;

public class PropertyAlreadyExistsException extends Exception {
  private static final long serialVersionUID = 1998156207367467728L;

  public PropertyAlreadyExistsException(final String key, String value) {
    super("Unable to add property {" + key + "," + value + "} already exists");
  }

}
