package com.mstt.qa.servicevirtualization.uicomponent.panels;

import java.util.List;

import com.mstt.qa.servicevirtualization.servicevirtualizationutils.dto.PatternMatcherDto;

public class QueryParameterPanel extends VirtualDetailsPatternListPanel {

  public QueryParameterPanel(final String tabName, final List<PatternMatcherDto> patternList) {
    super(patternList, false, tabName);
  }

  @Override
  public void addbuttonactionListener() {
    // TODO Auto-generated method stub

  }

  @Override
  public void deletebuttonactionListener() {
    // TODO Auto-generated method stub

  }

  @Override
  public void updatebuttonactionListener() {
    // TODO Auto-generated method stub

  }

}
