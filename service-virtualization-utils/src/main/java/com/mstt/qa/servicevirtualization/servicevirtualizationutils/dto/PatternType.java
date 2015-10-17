package com.mstt.qa.servicevirtualization.servicevirtualizationutils.dto;

public enum PatternType {
  equalToJson, equalToXml, matchesXPath, equalTo, contains, matches, doesNotMatch, absent, matchesJsonPath;
  public String value() {
    return super.toString();
  }

  public static String[] allPatterns() {
    PatternType[] patterType = values();
    String[] patternArray = new String[patterType.length];

    for (int i = 0; i < patterType.length; i++) {
      patternArray[i] = patterType[i].name();
    }

    return patternArray;
  }

  public static String[] equatePatterns() {
    String[] patternslist =
        {PatternType.equalTo.toString(), PatternType.matches.toString(),
            PatternType.doesNotMatch.toString(), PatternType.contains.toString()};
    return patternslist;
  }
}
