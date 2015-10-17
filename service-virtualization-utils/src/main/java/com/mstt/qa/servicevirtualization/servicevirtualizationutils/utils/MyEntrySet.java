package com.mstt.qa.servicevirtualization.servicevirtualizationutils.utils;

import java.util.Map.Entry;

public class MyEntrySet {
  public static Entry<String, String> getEntrySet(final String key, final String value) {
    Entry<String, String> myEntrySet = new Entry<String, String>() {

      @Override
      public String setValue(final String arg0) {
        return value;
      }

      @Override
      public String getValue() {
        // TODO Auto-generated method stub
        return value;
      }

      @Override
      public String getKey() {
        // TODO Auto-generated method stub
        return key;
      }
    };
    return myEntrySet;
  }
}
