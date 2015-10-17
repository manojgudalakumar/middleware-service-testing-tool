package com.github.tomakehurst.wiremock.jetty6;

import static com.github.tomakehurst.wiremock.common.Urls.splitQuery;
import static com.google.common.base.Charsets.UTF_8;
import static com.google.common.base.Strings.isNullOrEmpty;
import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.io.ByteStreams.toByteArray;
import static java.util.Collections.list;

import java.io.IOException;
import java.net.URI;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import com.github.tomakehurst.wiremock.http.ContentTypeHeader;
import com.github.tomakehurst.wiremock.http.HttpHeader;
import com.github.tomakehurst.wiremock.http.HttpHeaders;
import com.github.tomakehurst.wiremock.http.QueryParameter;
import com.github.tomakehurst.wiremock.http.Request;
import com.github.tomakehurst.wiremock.http.RequestMethod;
import com.google.common.base.Optional;

public class Jetty6HttpServletRequestAdapter implements Request {

  private final HttpServletRequest request;
  private byte[] cachedBody;
  private String urlPrefixToRemove;

  public Jetty6HttpServletRequestAdapter(final HttpServletRequest request) {
    this.request = request;
  }

  public Jetty6HttpServletRequestAdapter(final HttpServletRequest request,
      final String urlPrefixToRemove) {
    this.request = request;
    this.urlPrefixToRemove = urlPrefixToRemove;
  }

  public String getUrl() {
    String url = request.getRequestURI();

    String contextPath = request.getContextPath();
    if (!isNullOrEmpty(contextPath) && url.startsWith(contextPath)) {
      url = url.substring(contextPath.length());
    }
    if (!isNullOrEmpty(urlPrefixToRemove) && url.startsWith(urlPrefixToRemove)) {
      url = url.substring(urlPrefixToRemove.length());
    }

    return withQueryStringIfPresent(url);
  }

  public String getAbsoluteUrl() {
    return withQueryStringIfPresent(request.getRequestURL().toString());
  }

  private String withQueryStringIfPresent(final String url) {
    return url + (isNullOrEmpty(request.getQueryString()) ? "" : "?" + request.getQueryString());
  }

  public RequestMethod getMethod() {
    return RequestMethod.valueOf(request.getMethod().toUpperCase());
  }

  public byte[] getBody() {
    if (cachedBody == null) {
      try {
        cachedBody = toByteArray(request.getInputStream());
      } catch (IOException ioe) {
        throw new RuntimeException(ioe);
      }
    }

    return cachedBody;
  }

  public String getBodyAsString() {
    byte[] body = getBody();
    return new String(body, UTF_8);
  }

  @SuppressWarnings("unchecked")
  public String getHeader(final String key) {
    List<String> headerNames = list(request.getHeaderNames());
    for (String currentKey : headerNames) {
      if (currentKey.toLowerCase().equals(key.toLowerCase())) {
        return request.getHeader(currentKey);
      }
    }

    return null;
  }

  @SuppressWarnings("unchecked")
  public HttpHeader header(final String key) {
    List<String> headerNames = list(request.getHeaderNames());
    for (String currentKey : headerNames) {
      if (currentKey.toLowerCase().equals(key.toLowerCase())) {
        List<String> valueList = list(request.getHeaders(currentKey));
        return new HttpHeader(key, valueList);
      }
    }

    return HttpHeader.absent(key);
  }

  public ContentTypeHeader contentTypeHeader() {
    return getHeaders().getContentTypeHeader();
  }

  public boolean containsHeader(final String key) {
    return header(key).isPresent();
  }

  public HttpHeaders getHeaders() {
    List<HttpHeader> headerList = newArrayList();
    for (String key : getAllHeaderKeys()) {
      headerList.add(header(key));
    }

    return new HttpHeaders(headerList);
  }

  @SuppressWarnings("unchecked")
  public Set<String> getAllHeaderKeys() {
    LinkedHashSet<String> headerKeys = new LinkedHashSet<String>();
    for (Enumeration<String> headerNames = request.getHeaderNames(); headerNames.hasMoreElements();) {
      headerKeys.add(headerNames.nextElement());
    }

    return headerKeys;
  }

  public QueryParameter queryParameter(final String key) {
    return Optional.fromNullable(splitQuery(request.getQueryString()).get(key)).or(
        QueryParameter.absent(key));
  }

  public boolean isBrowserProxyRequest() {
    if (request instanceof org.mortbay.jetty.Request) {
      org.mortbay.jetty.Request jettyRequest = (org.mortbay.jetty.Request) request;
      URI uri = URI.create(jettyRequest.getUri().toString());
      return uri.isAbsolute();
    }

    return false;
  }

  @Override
  public String toString() {
    return request.toString() + getBodyAsString();
  }

  public int portNumber() {
    return request.getServerPort();
  }
}
