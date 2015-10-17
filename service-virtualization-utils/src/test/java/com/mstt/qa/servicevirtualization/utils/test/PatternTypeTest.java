package com.mstt.qa.servicevirtualization.utils.test;

import junit.framework.TestCase;

import com.mstt.qa.servicevirtualization.servicevirtualizationutils.dto.PatternType;

import org.junit.Test;

public class PatternTypeTest extends TestCase {
  @Test
  public void testPatternType() {
    assertEquals(PatternType.doesNotMatch.toString(), "doesNotMatch");
    assertEquals(PatternType.doesNotMatch.value(), "doesNotMatch");
    assertEquals(PatternType.doesNotMatch.name(), "doesNotMatch");

  }
}
