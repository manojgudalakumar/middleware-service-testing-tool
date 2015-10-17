package com.mstt.qa.servicevirtualization.servicevirtualizationutils.dto;

import static java.util.Arrays.asList;

public enum ServiceDataType {
  JSON, XML, json, xml, html, HTML;

  public static ServiceDataType fromString(final String value) {
    return ServiceDataType.valueOf(value);
  }

  public String value() {
    return super.toString();
  }

  public boolean isOneOf(final ServiceDataType... methods) {
    return asList(methods).contains(this);
  }
}
