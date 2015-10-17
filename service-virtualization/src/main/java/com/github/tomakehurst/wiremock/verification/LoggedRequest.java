package com.github.tomakehurst.wiremock.verification;

import static com.github.tomakehurst.wiremock.common.Urls.splitQuery;
import static com.github.tomakehurst.wiremock.http.HttpHeaders.copyOf;

import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.tomakehurst.wiremock.http.ContentTypeHeader;
import com.github.tomakehurst.wiremock.http.HttpHeader;
import com.github.tomakehurst.wiremock.http.HttpHeaders;
import com.github.tomakehurst.wiremock.http.QueryParameter;
import com.github.tomakehurst.wiremock.http.Request;
import com.github.tomakehurst.wiremock.http.RequestMethod;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LoggedRequest implements Request {

  public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

  private final String url;
  private final String absoluteUrl;
  private final RequestMethod method;
  private final HttpHeaders headers;
  private final Map<String, QueryParameter> queryParams;
  private final byte[] body;
  private final boolean isBrowserProxyRequest;
  private final Date loggedDate;

  public static LoggedRequest createFrom(final Request request) {
    return new LoggedRequest(request.getUrl(), request.getAbsoluteUrl(), request.getMethod(),
        copyOf(request.getHeaders()), request.getBody(), request.isBrowserProxyRequest(),
        new Date());
  }

  public LoggedRequest(final String url, final String absoluteUrl, final RequestMethod method,
      final HttpHeaders headers, final byte[] body, final boolean isBrowserProxyRequest,
      final Date loggedDate) {

    this.url = url;
    this.absoluteUrl = absoluteUrl;
    this.method = method;
    this.body = body;
    this.headers = headers;
    this.queryParams = splitQuery(URI.create(url));
    this.isBrowserProxyRequest = isBrowserProxyRequest;
    this.loggedDate = loggedDate;
  }

  @JsonCreator
  public LoggedRequest(@JsonProperty("url") final String url,
      @JsonProperty("absoluteUrl") final String absoluteUrl,
      @JsonProperty("method") final RequestMethod method,
      @JsonProperty("headers") final HttpHeaders headers, @JsonProperty("body") final String body,
      @JsonProperty("browserProxyRequest") final boolean isBrowserProxyRequest,
      @JsonProperty("loggedDate") final Date loggedDate) {
    this(url, absoluteUrl, method, headers, body.getBytes(), isBrowserProxyRequest, loggedDate);
  }

  public String getUrl() {
    return url;
  }

  public String getAbsoluteUrl() {
    return absoluteUrl;
  }

  public RequestMethod getMethod() {
    return method;
  }

  @JsonIgnore
  public String getHeader(final String key) {
    HttpHeader header = header(key);
    if (header.isPresent()) {
      return header.firstValue();
    }

    return null;
  }

  public HttpHeader header(final String key) {
    return headers.getHeader(key);
  }

  public ContentTypeHeader contentTypeHeader() {
    return headers.getContentTypeHeader();
  }

  public boolean containsHeader(final String key) {
    return getHeader(key) != null;
  }

  public byte[] getBody() {
    return body;
  }

  @JsonProperty("body")
  public String getBodyAsString() {
    return new String(body);
  }

  @JsonIgnore
  public Set<String> getAllHeaderKeys() {
    return headers.keys();
  }

  public QueryParameter queryParameter(final String key) {
    return queryParams.get(key);
  }

  public HttpHeaders getHeaders() {
    return headers;
  }

  public boolean isBrowserProxyRequest() {
    return isBrowserProxyRequest;
  }

  public Date getLoggedDate() {
    return loggedDate;
  }

  public String getLoggedDateString() {
    return format(loggedDate);
  }

  private String format(final Date date) {
    return new SimpleDateFormat(DATE_FORMAT).format(date);
  }

  @JsonIgnore
  public int portNumber() {
    // TODO Auto-generated method stub
    return 8083;
  }
}
