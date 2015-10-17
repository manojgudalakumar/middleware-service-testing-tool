package com.mstt.qa.servicevirtualization.servicevirtualizationutils.dto;

import java.util.ArrayList;
import java.util.List;

public class MsttRequestDefinition {

  public void setUrl(final String url) {
    this.url = url;
  }

  public String getRequestMethod() {
    return requestMethod;
  }

  public void setRequestMethod(final String requestMethod) {
    this.requestMethod = requestMethod;
  }

  public String getServiceVirtualziseType() {
    return serviceVirtualziseType;
  }

  public void setServiceVirtualziseType(final String serviceVirtualziseType) {
    this.serviceVirtualziseType = serviceVirtualziseType;
  }

  public String getRequestBody() {
    return requestBody;
  }

  public void setRequestBody(final String requestBody) {
    this.requestBody = requestBody;
  }

  public String getUrlPattern() {
    return urlPattern;
  }

  public void setUrlPattern(final String urlPattern) {
    this.urlPattern = urlPattern;
  }

  public String getUrlPath() {
    return urlPath;
  }

  public void setUrlPath(final String urlPath) {
    this.urlPath = urlPath;
  }

  public List<PatternMatcherDto> getHeaderPatterns() {
    return headerPatterns;
  }

  public void setHeaderPatterns(final List<PatternMatcherDto> headerPatterns) {
    this.headerPatterns = headerPatterns;
  }

  public List<PatternMatcherDto> getQueryParamPatterns() {
    return queryParamPatterns;
  }

  public void setQueryParamPatterns(final List<PatternMatcherDto> queryParamPatterns) {
    this.queryParamPatterns = queryParamPatterns;
  }

  public List<PatternMatcherDto> getBodyPatterns() {
    return bodyPatterns;
  }

  public void setBodyPatterns(final List<PatternMatcherDto> bodyPatterns) {
    this.bodyPatterns = bodyPatterns;
  }

  public Integer getPriority() {
    return priority;
  }

  public void setPriority(final Integer priority) {
    this.priority = priority;
  }

  public int getPortNbr() {
    return portNbr;
  }

  public void setPortNbr(final int portNbr) {
    this.portNbr = portNbr;
  }

  public boolean isBrowserProxyEnabled() {
    return isBrowserProxyEnabled;
  }

  public void setBrowserProxyEnabled(final boolean isBrowserProxyEnabled) {
    this.isBrowserProxyEnabled = isBrowserProxyEnabled;
  }

  public String getUrl() {
    return url;
  }

  private String url;
  private String requestMethod;
  private String serviceVirtualziseType;
  private String requestBody;
  private String urlPattern;
  private String urlPath;
  private List<PatternMatcherDto> headerPatterns;
  private List<PatternMatcherDto> queryParamPatterns;
  private List<PatternMatcherDto> bodyPatterns;
  private int portNbr;
  private boolean isBrowserProxyEnabled;
  private Integer priority;

  public MsttRequestDefinition() {
    headerPatterns = new ArrayList<PatternMatcherDto>();
    queryParamPatterns = new ArrayList<PatternMatcherDto>();
    bodyPatterns = new ArrayList<PatternMatcherDto>();
  }
}
