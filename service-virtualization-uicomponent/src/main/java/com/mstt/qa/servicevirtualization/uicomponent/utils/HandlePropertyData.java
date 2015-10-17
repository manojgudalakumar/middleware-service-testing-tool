package com.mstt.qa.servicevirtualization.uicomponent.utils;

import java.util.Map;

public class HandlePropertyData {
  public String getEvaluatedString(final Map<String, String> propKeyMap, final String str) {
    StringBuilder strBuilder = new StringBuilder(str);
    for (Map.Entry<String, String> property : propKeyMap.entrySet()) {
      replaceString(strBuilder, getEncryptedString(property.getKey()), property.getValue());
    }
    return strBuilder.toString();
  }

  private static String getEncryptedString(String key) {
    key = "${" + key + "}";
    return key;
  }

  public static void replaceString(final StringBuilder sb, final String toReplace,
      final String replacement) {
    int index = -1;
    while ((index = sb.lastIndexOf(toReplace)) != -1) {
      sb.replace(index, index + toReplace.length(), replacement);
    }
  }
}
