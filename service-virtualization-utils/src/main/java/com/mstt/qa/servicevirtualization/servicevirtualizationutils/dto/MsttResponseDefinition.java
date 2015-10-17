package com.mstt.qa.servicevirtualization.servicevirtualizationutils.dto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MsttResponseDefinition {

  public int getStatus() {
    return status;
  }

  public void setStatus(final int status) {
    this.status = status;
  }

  public String getBodyFileName() {
    return bodyFileName;
  }

  public void setBodyFileName(final String bodyFileName) {
    this.bodyFileName = bodyFileName;
  }

  public Map<String, String> getHeaders() {
    return headers;
  }

  public void setHeaders(final Map<String, String> headers) {
    this.headers = headers;
  }

  public Integer getFixedDelayMilliseconds() {
    return fixedDelayMilliseconds;
  }

  public void setFixedDelayMilliseconds(final Integer fixedDelayMilliseconds) {
    this.fixedDelayMilliseconds = fixedDelayMilliseconds;
  }

  public String getProxyBaseUrl() {
    return proxyBaseUrl;
  }

  public void setProxyBaseUrl(final String proxyBaseUrl) {
    this.proxyBaseUrl = proxyBaseUrl;
  }

  public List<String> getResponseTransformerNames() {
    return responseTransformerNames;
  }

  public void setResponseTransformerNames(final List<String> responseTransformerNames) {
    this.responseTransformerNames = responseTransformerNames;
  }

  public String getResponse() {
    return response;
  }

  public void setResponse(final String response) {
    this.response = response;
  }

  public Map<String, String> getProxyAdditionalHeaders() {
    return proxyAdditionalHeaders;
  }

  public void setProxyAdditionalHeaders(final Map<String, String> proxyAdditionalHeaders) {
    this.proxyAdditionalHeaders = proxyAdditionalHeaders;
  }

  private int status;
  private String bodyFileName;
  private Map<String, String> headers;
  private Map<String, String> proxyAdditionalHeaders;
  private Integer fixedDelayMilliseconds;
  private String proxyBaseUrl;
  private List<String> responseTransformerNames;
  private String response;

  public MsttResponseDefinition() {
    headers = new HashMap<String, String>();
    proxyAdditionalHeaders = new HashMap<String, String>();
  }
}
