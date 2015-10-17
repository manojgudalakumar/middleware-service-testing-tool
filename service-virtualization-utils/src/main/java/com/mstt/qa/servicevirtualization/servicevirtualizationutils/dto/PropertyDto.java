package com.mstt.qa.servicevirtualization.servicevirtualizationutils.dto;

import java.util.HashMap;
import java.util.Map;

import com.mstt.qa.servicevirtualization.servicevirtualizationutils.exceptions.PropertyAlreadyExistsException;

public class PropertyDto {
  private Map<String, String> propertyMap;
  private String level;
  private String testCaseReferenceId;
  private String parentTestCaseReferenceId;

  public PropertyDto() {
    propertyMap = new HashMap<String, String>();
  }

  public String getLevel() {
    return level;
  }

  public void setLevel(final String level) {
    this.level = level;
  }

  public Map<String, String> getPropertyMap() {
    return propertyMap;
  }

  public void setPropertyMap(final Map<String, String> propertyMap) {
    this.propertyMap = propertyMap;
  }

  public void addProperty(final String key, final String value)
      throws PropertyAlreadyExistsException {
    if (propertyMap.containsKey(key)) {
      throw new PropertyAlreadyExistsException(key, value);
    } else {
      propertyMap.put(key, value);
    }
  }

  public void removeProperty(final String key) {
    this.propertyMap.remove(key);
  }

  public String getTestCaseReferenceId() {
    return testCaseReferenceId;
  }

  public void setTestCaseReferenceId(final String testCaseReferenceId) {
    this.testCaseReferenceId = testCaseReferenceId;
  }

  public String getParentTestCaseReferenceId() {
    return parentTestCaseReferenceId;
  }

  public void setParentTestCaseReferenceId(final String parentTestCaseReferenceId) {
    this.parentTestCaseReferenceId = parentTestCaseReferenceId;
  }
}
