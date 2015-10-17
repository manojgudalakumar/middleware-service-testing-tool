package com.mstt.qa.servicevirtualization.servicevirtualizationutils.dto;

public class PatternMatcherDto {
  public String getKey() {
    return key;
  }

  public void setKey(final String key) {
    this.key = key;
  }

  public String getValue() {
    return value;
  }

  public void setValue(final String value) {
    this.value = value;
  }

  public String getPattern() {
    return pattern;
  }

  public void setPattern(final String pattern) {
    this.pattern = pattern;
  }

  public String[] getPatternMatcher() {
    return new String[] {key, pattern, value};
  }

  private String key;
  private String value;
  private String pattern;

  @Override
  public boolean equals(final Object o) {
    if (o == null)
      return false;
    if (!(o instanceof PatternMatcherDto))
      return false;

    PatternMatcherDto other = (PatternMatcherDto) o;
    return this.key == other.key;
  }
}
