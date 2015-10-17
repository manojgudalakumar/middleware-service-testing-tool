package com.mstt.qa.servicevirtualization.uicomponent.panels;

import java.util.List;

import javafx.scene.control.TabPane;

import org.apache.http.NameValuePair;

import com.mstt.qa.servicevirtualization.servicevirtualizationutils.dto.MsttRequestDefinition;
import com.mstt.qa.servicevirtualization.servicevirtualizationutils.dto.MsttResponseDefinition;
import com.mstt.qa.servicevirtualization.servicevirtualizationutils.dto.PatternMatcherDto;
import com.mstt.qa.servicevirtualization.servicevirtualizationutils.dto.RestServiceVirtualizeDto;
import com.mstt.qa.servicevirtualization.servicevirtualizationutils.utils.URLUtils;
import com.mstt.qa.servicevirtualization.uicomponent.MsstGuiPckg;

public class VirtualDetailsTabbedPane extends TabPane {
  RequestHeaderPanel requestHeaderDetailPanel;
  ResponseHeaderPanel responseHeaderDetailPanel;
  RequestBodyPatternPanel requestBodyPatternPanel;
  QueryParameterPanel queryParameterPanel;

  public VirtualDetailsTabbedPane(final MsttRequestDefinition msttReq,
      final MsttResponseDefinition msttRes) {
    setPrefHeight(MsstGuiPckg.getInstance().getGuiPreferredSize().virtualDetailsPanelWithHeight());
    setPrefWidth(MsstGuiPckg.getInstance().getGuiPreferredSize().virtualDetailsPanelWithWidth());
    setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
    requestHeaderDetailPanel =
        new RequestHeaderPanel("Request Header detail", msttReq.getHeaderPatterns());
    responseHeaderDetailPanel =
        new ResponseHeaderPanel("Response Header detail", msttRes.getHeaders());
    requestBodyPatternPanel =
        new RequestBodyPatternPanel("Request Body Pattern details", msttReq.getBodyPatterns());
    getTabs().add(getRequestHeaderPanel());
    getTabs().add(getResponseHeaderPanel());
    getTabs().add(getRequestBodyPatternPanel());
  }

  private RequestHeaderPanel getRequestHeaderPanel() {
    return requestHeaderDetailPanel;
  }

  private ResponseHeaderPanel getResponseHeaderPanel() {
    return responseHeaderDetailPanel;
  }

  private RequestBodyPatternPanel getRequestBodyPatternPanel() {
    return requestBodyPatternPanel;
  }

  public void populateQueryParamtersTab(final RestServiceVirtualizeDto rstDto, final String url) {
    if (queryParameterPanel == null) {
      List<NameValuePair> params = URLUtils.extractParameters(url);
      MsttRequestDefinition pkreqDef = rstDto.getRequesStub();
      List<PatternMatcherDto> patternList = pkreqDef.getQueryParamPatterns();
      URLUtils.copyNameValuePaitListToPatternMatcherList(params, patternList);
      queryParameterPanel = new QueryParameterPanel("Query Paraterms", patternList);
      getTabs().add(queryParameterPanel);
    }
    getSelectionModel().select(queryParameterPanel);

  }

}
