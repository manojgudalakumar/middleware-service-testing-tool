package com.mstt.qa.servicevirtualization.uicomponent.panels;

import java.util.List;

import com.mstt.qa.servicevirtualization.servicevirtualizationutils.dto.PatternMatcherDto;

public class RequestHeaderPanel extends VirtualDetailsPatternListPanel {
  List<PatternMatcherDto> requestHeaderList;

  public RequestHeaderPanel(final String string, final List<PatternMatcherDto> requestHeaderList) {
    super(requestHeaderList, true, string);
    this.requestHeaderList = requestHeaderList;
    addbuttonactionListener();
    deletebuttonactionListener();
    updatebuttonactionListener();
  }

  @Override
  public void addbuttonactionListener() {
    addbuttionActionListner(requestHeaderList);
  }

  @Override
  public void deletebuttonactionListener() {
    // deletebuttonactionListener(requestHeaderList);
  }

  @Override
  public void updatebuttonactionListener() {
    // updatebuttonactionListener(requestHeaderList);

  }

}
