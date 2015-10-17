package com.mstt.qa.servicevirtualization.soapservice.utils;

import java.text.CharacterIterator;
import java.text.StringCharacterIterator;

/**
 * Utility class of string operations.
 * 
 * @author Sripathi
 * 
 */
public class StringUtil {

  /**
   * Checks for null or empty.
   * 
   * @param str input string to be checked.
   * @return true if string is not null and empty.
   */
  public static boolean isValid(final String str) {

    return (str != null && str.trim().length() != 0) ? true : false;
  }

  /**
   * Encodes the string to be used in HTML.
   * 
   * @param aText string.
   * @return encoded.
   */
  public static String forHTML(final String aText) {
    final StringBuilder result = new StringBuilder();
    final StringCharacterIterator iterator = new StringCharacterIterator(aText);
    char character = iterator.current();
    while (character != CharacterIterator.DONE) {
      if (character == '\"') {
        result.append("DOUBLE_QUOTE");
      } else if (character == '\r') {
        result.append("RETURN_CAR");
      } else if (character == '\n') {
        result.append("NEW_LINE");
      } else if (character == '\'') {
        result.append("SINGLE_QUOTE");
      } else {
        // the char is not a special one
        // add it to the result as is
        result.append(character);
      }
      character = iterator.next();
    }
    return result.toString();
  }

  /**
   * Encodes the string to be used in HTML.
   * 
   * @param aText string.
   * @return encoded.
   */
  public static String forHtmlComplete(final String aText) {
    final StringBuilder result = new StringBuilder();
    final StringCharacterIterator iterator = new StringCharacterIterator(aText);
    char character = iterator.current();
    while (character != CharacterIterator.DONE) {
      if (character == '<') {
        result.append("&lt;");
      } else if (character == '>') {
        result.append("&gt;");
      } else if (character == '&') {
        result.append("&amp;");
      } else if (character == '\"') {
        result.append("&quot;");
      } else if (character == '\n') {
        result.append("<br>");
      } else if (character == ' ') {
        result.append("&nbsp;");
      } else if (character == '\t') {
        addCharEntity(9, result);
      } else if (character == '!') {
        addCharEntity(33, result);
      } else if (character == '#') {
        addCharEntity(35, result);
      } else if (character == '$') {
        addCharEntity(36, result);
      } else if (character == '%') {
        addCharEntity(37, result);
      } else if (character == '\'') {
        addCharEntity(39, result);
      } else if (character == '(') {
        addCharEntity(40, result);
      } else if (character == ')') {
        addCharEntity(41, result);
      } else if (character == '*') {
        addCharEntity(42, result);
      } else if (character == '+') {
        addCharEntity(43, result);
      } else if (character == ',') {
        addCharEntity(44, result);
      } else if (character == '-') {
        addCharEntity(45, result);
      } else if (character == '.') {
        addCharEntity(46, result);
      } else if (character == '/') {
        addCharEntity(47, result);
      } else if (character == ':') {
        addCharEntity(58, result);
      } else if (character == ';') {
        addCharEntity(59, result);
      } else if (character == '=') {
        addCharEntity(61, result);
      } else if (character == '?') {
        addCharEntity(63, result);
      } else if (character == '@') {
        addCharEntity(64, result);
      } else if (character == '[') {
        addCharEntity(91, result);
      } else if (character == '\\') {
        addCharEntity(92, result);
      } else if (character == ']') {
        addCharEntity(93, result);
      } else if (character == '^') {
        addCharEntity(94, result);
      } else if (character == '_') {
        addCharEntity(95, result);
      } else if (character == '`') {
        addCharEntity(96, result);
      } else if (character == '{') {
        addCharEntity(123, result);
      } else if (character == '|') {
        addCharEntity(124, result);
      } else if (character == '}') {
        addCharEntity(125, result);
      } else if (character == '~') {
        addCharEntity(126, result);
      } else {
        // the char is not a special one
        // add it to the result as is
        result.append(character);
      }
      character = iterator.next();
    }
    return result.toString();

  }

  private static void addCharEntity(final Integer aIdx, final StringBuilder aBuilder) {
    String padding = "";
    if (aIdx <= 9) {
      padding = "00";
    } else if (aIdx <= 99) {
      padding = "0";
    } else {
      // no prefix
    }
    String number = padding + aIdx.toString();
    aBuilder.append("&#" + number + ";");
  }

  public static String failureMsg(final String msg) {
    return "<font color='red'>Failed</font> : " + msg;
  }

  public static String successMsg(final String msg) {
    return "<font color='green'>Success</font> : " + msg;
  }
}
