package com.mstt.qa.servicevirtualization.uicomponent.panels;

import java.util.List;

import com.mstt.qa.servicevirtualization.servicevirtualizationutils.dto.PatternMatcherDto;

public class RequestBodyPatternPanel extends VirtualDetailsPatternListPanel {
  List<PatternMatcherDto> requestBodyPatternList;

  public RequestBodyPatternPanel(final String tabName,
      final List<PatternMatcherDto> requestBodyPatternList) {
    super(requestBodyPatternList, true, tabName);
    this.requestBodyPatternList = requestBodyPatternList;
    addbuttonactionListener();
    deletebuttonactionListener();
    updatebuttonactionListener();
  }

  @Override
  public void addbuttonactionListener() {
    addbuttionActionListner(requestBodyPatternList);
  }

  @Override
  public void deletebuttonactionListener() {
    // deletebuttonactionListener(requestBodyPatternList);
  }


  @Override
  public void updatebuttonactionListener() {
    // updatebuttonactionListener(requestBodyPatternList);

  }

}
