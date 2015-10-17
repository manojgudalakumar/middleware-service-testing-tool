package com.prokarma.qa.servicevirtualization.uicomponent.test;

import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.mstt.qa.servicevirtualization.uicomponent.utils.HandlePropertyData;

public class HandlePropertyDataTest {
  public HandlePropertyData handlePrope;

  @BeforeClass
  public void setUp() {
    handlePrope = new HandlePropertyData();
  }

  @Test
  public void emptyMap() {
    Map<String, String> str = new HashMap<String, String>();
    String str1 = "";
    Assert.assertEquals(handlePrope.getEvaluatedString(str, str1), "");
  }

  @Test
  public void mapEntry() {
    Map<String, String> str = new HashMap<String, String>();
    String str1 = "This is my ${name}";
    str.put("name", "manojkumar");
    Assert.assertEquals(handlePrope.getEvaluatedString(str, str1), "This is my manojkumar");
  }

  @Test
  public void mapEntryDuplicate() {
    Map<String, String> str = new HashMap<String, String>();
    String str1 = "This is my ${name} and ${name}";
    str.put("name", "manojkumar");
    Assert.assertEquals(handlePrope.getEvaluatedString(str, str1),
        "This is my manojkumar and manojkumar");
  }

  @Test
  public void mapEntryTitle() {
    Map<String, String> str = new HashMap<String, String>();
    String str1 = "This is my ${firstname} and ${lastname}";
    str.put("firstname", "manoj");
    str.put("lastname", "kumar");
    Assert.assertEquals(handlePrope.getEvaluatedString(str, str1), "This is my manoj and kumar");
  }

  @Test
  public void mapEntrySplChar() {
    Map<String, String> str = new HashMap<String, String>();
    String str1 = "This $ is my ${firstname} and ${lastname}";
    str.put("firstname", "manoj");
    str.put("lastname", "kumar");
    Assert.assertEquals(handlePrope.getEvaluatedString(str, str1), "This $ is my manoj and kumar");
  }

  @Test
  public void mapEntrySplCharOpenBrace() {
    Map<String, String> str = new HashMap<String, String>();
    String str1 = "This { is my ${firstname} and ${lastname}";
    str.put("firstname", "manoj");
    str.put("lastname", "kumar");
    Assert.assertEquals(handlePrope.getEvaluatedString(str, str1), "This { is my manoj and kumar");
  }

  @Test
  public void mapEntrySplCharCloseBrace() {
    Map<String, String> str = new HashMap<String, String>();
    String str1 = "This } is my ${firstname} and ${lastname}";
    str.put("firstname", "manoj");
    str.put("lastname", "kumar");
    Assert.assertEquals(handlePrope.getEvaluatedString(str, str1), "This } is my manoj and kumar");
  }

  @Test
  public void mapEntrySplCharOpenCloseBrace() {
    Map<String, String> str = new HashMap<String, String>();
    String str1 = "This } is my ${firstname} and ${} ${lastname}";
    str.put("firstname", "manoj");
    str.put("lastname", "kumar");
    Assert.assertEquals(handlePrope.getEvaluatedString(str, str1),
        "This } is my manoj and ${} kumar");
  }

  @Test
  public void mapEntrySplCharWithKey() {
    Map<String, String> str = new HashMap<String, String>();
    String str1 = "This firstname is my ${firstname} and lastname ${lastname}";
    str.put("firstname", "manoj");
    str.put("lastname", "kumar");
    Assert.assertEquals(handlePrope.getEvaluatedString(str, str1),
        "This firstname is my manoj and lastname kumar");
  }

  @Test
  public void mapEntrySplCharWithKeyAndValue() {
    Map<String, String> str = new HashMap<String, String>();
    String str1 = "This firstname is my ${firstname} and manoj ${lastname}";
    str.put("firstname", "manoj");
    str.put("lastname", "kumar");
    Assert.assertEquals(handlePrope.getEvaluatedString(str, str1),
        "This firstname is my manoj and manoj kumar");
  }
}
