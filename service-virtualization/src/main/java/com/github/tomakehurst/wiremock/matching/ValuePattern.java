package com.github.tomakehurst.wiremock.matching;

import static com.github.tomakehurst.wiremock.common.LocalNotifier.notifier;
import static java.util.regex.Pattern.DOTALL;
import static org.skyscreamer.jsonassert.JSONCompare.compareJSON;
import static org.skyscreamer.jsonassert.JSONCompareMode.NON_EXTENSIBLE;

import java.io.IOException;
import java.util.regex.Pattern;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

import org.custommonkey.xmlunit.Diff;
import org.custommonkey.xmlunit.XMLUnit;
import org.custommonkey.xmlunit.XpathEngine;
import org.custommonkey.xmlunit.exceptions.XpathException;
import org.json.JSONException;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.skyscreamer.jsonassert.JSONCompareResult;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize.Inclusion;
import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.jayway.jsonpath.JsonPath;

@JsonSerialize(include = Inclusion.NON_NULL)
public class ValuePattern {

  static {
    XMLUnit.setIgnoreWhitespace(true);
  }

  private String equalToJson;
  private String equalToXml;
  private String matchesXPath;
  private JSONCompareMode jsonCompareMode;
  private String equalTo;
  private String contains;
  private String matches;
  private String doesNotMatch;
  private Boolean absent;
  private String matchesJsonPath;

  public static ValuePattern equalTo(final String value) {
    ValuePattern valuePattern = new ValuePattern();
    valuePattern.setEqualTo(value);
    return valuePattern;
  }

  public static ValuePattern equalToJson(final String value) {
    ValuePattern valuePattern = new ValuePattern();
    valuePattern.setEqualToJson(value);
    return valuePattern;
  }

  public static ValuePattern equalToXml(final String value) {
    ValuePattern valuePattern = new ValuePattern();
    valuePattern.setEqualToXml(value);
    return valuePattern;
  }

  public static ValuePattern equalToXPath(final String value) {
    ValuePattern valuePattern = new ValuePattern();
    valuePattern.setMatchesXPath(value);
    return valuePattern;
  }

  public static ValuePattern equalToJson(final String value, final JSONCompareMode jsonCompareMode) {
    ValuePattern valuePattern = new ValuePattern();
    valuePattern.setEqualToJson(value);
    valuePattern.setJsonCompareMode(jsonCompareMode);
    return valuePattern;
  }

  public static ValuePattern containing(final String value) {
    ValuePattern valuePattern = new ValuePattern();
    valuePattern.setContains(value);
    return valuePattern;
  }

  public static ValuePattern matches(final String value) {
    ValuePattern valuePattern = new ValuePattern();
    valuePattern.setMatches(value);
    return valuePattern;
  }

  public static ValuePattern absent() {
    ValuePattern valuePattern = new ValuePattern();
    valuePattern.absent = true;
    return valuePattern;
  }

  public boolean isMatchFor(final String value) {
    checkOneMatchTypeSpecified();

    if (absent != null) {
      return (absent && value == null);
    } else if (equalToJson != null) {
      return isEqualJson(value);
    } else if (equalToXml != null) {
      return isEqualXml(value);
    } else if (matchesXPath != null) {
      return isXPathMatch(value);
    } else if (equalTo != null) {
      return value.equals(equalTo);
    } else if (contains != null) {
      return value.contains(contains);
    } else if (matches != null) {
      return isMatch(matches, value);
    } else if (doesNotMatch != null) {
      return !isMatch(doesNotMatch, value);
    } else if (matchesJsonPath != null) {
      return isJsonPathMatch(value);
    }

    return false;
  }

  public static Predicate<ValuePattern> matching(final String value) {
    return new Predicate<ValuePattern>() {
      public boolean apply(final ValuePattern input) {
        return input.isMatchFor(value);
      }
    };
  }

  private boolean isEqualJson(final String value) {
    JSONCompareResult result;
    try {
      result =
          compareJSON(equalToJson, value, Optional.fromNullable(jsonCompareMode).or(NON_EXTENSIBLE));
    } catch (JSONException e) {
      return false;
    }
    return result.passed();
  }

  private boolean isEqualXml(final String value) {
    try {
      Diff diff = XMLUnit.compareXML(equalToXml, value);
      return diff.similar();
    } catch (SAXException e) {
      return false;
    } catch (IOException e) {
      return false;
    }
  }

  private boolean isXPathMatch(final String value) {
    try {
      Document inDocument = XMLUnit.buildControlDocument(value);
      XpathEngine simpleXpathEngine = XMLUnit.newXpathEngine();
      NodeList nodeList = simpleXpathEngine.getMatchingNodes(matchesXPath, inDocument);
      return nodeList.getLength() > 0;
    } catch (SAXException e) {
      notifier().info(
          String.format("Warning: failed to parse the XML document. Reason: %s\nXML: %s",
              e.getMessage(), value));
      return false;
    } catch (IOException e) {
      notifier().info(e.getMessage());
      return false;
    } catch (XpathException e) {
      notifier().info("Warning: failed to evaluate the XPath expression " + matchesXPath);
      return false;
    }
  }

  private boolean isMatch(final String regex, final String value) {
    Pattern pattern = Pattern.compile(regex, DOTALL);
    return pattern.matcher(value).matches();
  }

  private boolean isJsonPathMatch(final String value) {
    try {
      Object obj = JsonPath.read(value, matchesJsonPath);
      if (obj instanceof JSONArray) {
        return ((JSONArray) obj).size() > 0;
      }

      if (obj instanceof JSONObject) {
        return ((JSONObject) obj).size() > 0;
      }

      return obj != null;
    } catch (Exception e) {
      String error;
      if (e.getMessage().equalsIgnoreCase("invalid path")) {
        error = "the JSON path didn't match the document structure";
      } else if (e.getMessage().equalsIgnoreCase("invalid container object")) {
        error = "the JSON document couldn't be parsed";
      } else {
        error = "of error '" + e.getMessage() + "'";
      }

      String message =
          String.format(
              "Warning: JSON path expression '%s' failed to match document '%s' because %s",
              matchesJsonPath, value, error);
      notifier().info(message);
      return false;
    }
  }

  private void checkNoMoreThanOneMatchTypeSpecified() {
    if (countAllAttributes() > 1) {
      throw new IllegalStateException("Only one type of match may be specified");
    }
  }

  private void checkOneMatchTypeSpecified() {
    if (countAllAttributes() == 0) {
      throw new IllegalStateException("One match type must be specified");
    }
  }

  private int countAllAttributes() {
    return count(equalToJson, equalToXml, matchesXPath, equalTo, contains, matches, doesNotMatch,
        absent, matchesJsonPath);
  }

  public static int count(final Object... objects) {
    int counter = 0;
    for (Object obj : objects) {
      if (obj != null) {
        counter++;
      }
    }

    return counter;
  }

  public void setEqualTo(final String equalTo) {
    this.equalTo = equalTo;
    checkNoMoreThanOneMatchTypeSpecified();
  }

  public void setEqualToJson(final String equalToJson) {
    this.equalToJson = equalToJson;
    checkNoMoreThanOneMatchTypeSpecified();
  }

  public void setEqualToXml(final String equalToXml) {
    this.equalToXml = equalToXml;
    checkNoMoreThanOneMatchTypeSpecified();
  }

  public void setMatchesXPath(final String matchesXPath) {
    this.matchesXPath = matchesXPath;
    checkNoMoreThanOneMatchTypeSpecified();
  }

  public void setContains(final String contains) {
    this.contains = contains;
    checkNoMoreThanOneMatchTypeSpecified();
  }

  public void setMatches(final String matches) {
    this.matches = matches;
    checkNoMoreThanOneMatchTypeSpecified();
  }

  public void setDoesNotMatch(final String doesNotMatch) {
    this.doesNotMatch = doesNotMatch;
    checkNoMoreThanOneMatchTypeSpecified();
  }

  public void setAbsent(final Boolean absent) {
    this.absent = absent;
    checkNoMoreThanOneMatchTypeSpecified();
  }

  public void setMatchesJsonPaths(final String matchesJsonPath) {
    this.matchesJsonPath = matchesJsonPath;
    checkNoMoreThanOneMatchTypeSpecified();
  }

  public String getEqualTo() {
    return equalTo;
  }

  public String getEqualToJson() {
    return equalToJson;
  }

  public String getEqualToXml() {
    return equalToXml;
  }

  public String getMatchesXPath() {
    return matchesXPath;
  }

  public JSONCompareMode getJsonCompareMode() {
    return jsonCompareMode;
  }

  public void setJsonCompareMode(final JSONCompareMode jsonCompareMode) {
    this.jsonCompareMode = jsonCompareMode;
  }

  public String getContains() {
    return contains;
  }

  public String getMatches() {
    return matches;
  }

  public String getDoesNotMatch() {
    return doesNotMatch;
  }

  public Boolean isAbsent() {
    return absent;
  }

  public String getMatchesJsonPath() {
    return matchesJsonPath;
  }

  public boolean nullSafeIsAbsent() {
    return (absent != null && absent);
  }

  @Override
  public String toString() {
    if (equalToJson != null) {
      return "equalJson " + equalToJson;
    } else if (equalToXml != null) {
      return "equalXml " + equalToXml;
    } else if (matchesXPath != null) {
      return "equalXPath " + matchesXPath;
    } else if (equalTo != null) {
      return "equal " + equalTo;
    } else if (contains != null) {
      return "contains " + contains;
    } else if (matches != null) {
      return "matches " + matches;
    } else if (doesNotMatch != null) {
      return "not match " + doesNotMatch;
    } else if (matchesJsonPath != null) {
      return "matches JSON path " + matchesJsonPath;
    } else {
      return "is absent";
    }
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;

    ValuePattern that = (ValuePattern) o;

    if (absent != null ? !absent.equals(that.absent) : that.absent != null)
      return false;
    if (contains != null ? !contains.equals(that.contains) : that.contains != null)
      return false;
    if (doesNotMatch != null ? !doesNotMatch.equals(that.doesNotMatch) : that.doesNotMatch != null)
      return false;
    if (equalTo != null ? !equalTo.equals(that.equalTo) : that.equalTo != null)
      return false;
    if (equalToJson != null ? !equalToJson.equals(that.equalToJson) : that.equalToJson != null)
      return false;
    if (equalToXml != null ? !equalToXml.equals(that.equalToXml) : that.equalToXml != null)
      return false;
    if (matchesXPath != null ? !matchesXPath.equals(that.matchesXPath) : that.matchesXPath != null)
      return false;
    if (matches != null ? !matches.equals(that.matches) : that.matches != null)
      return false;
    if (matchesJsonPath != null ? !matchesJsonPath.equals(that.matchesJsonPath)
        : that.matchesJsonPath != null)
      return false;

    return true;
  }

  @Override
  public int hashCode() {
    int result = equalTo != null ? equalTo.hashCode() : 0;
    result = 31 * result + (equalToJson != null ? equalToJson.hashCode() : 0);
    result = 31 * result + (equalToXml != null ? equalToXml.hashCode() : 0);
    result = 31 * result + (matchesXPath != null ? matchesXPath.hashCode() : 0);
    result = 31 * result + (contains != null ? contains.hashCode() : 0);
    result = 31 * result + (matches != null ? matches.hashCode() : 0);
    result = 31 * result + (doesNotMatch != null ? doesNotMatch.hashCode() : 0);
    result = 31 * result + (absent != null ? absent.hashCode() : 0);
    result = 31 * result + (matchesJsonPath != null ? matchesJsonPath.hashCode() : 0);
    return result;
  }
}
