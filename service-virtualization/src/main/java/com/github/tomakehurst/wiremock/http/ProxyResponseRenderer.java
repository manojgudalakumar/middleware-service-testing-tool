/*
 * Copyright (C) 2011 Thomas Akehurst
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package com.github.tomakehurst.wiremock.http;

import static com.github.tomakehurst.wiremock.common.HttpClientUtils.getEntityAsByteArrayAndCloseStream;
import static com.github.tomakehurst.wiremock.common.LocalNotifier.notifier;
import static com.github.tomakehurst.wiremock.http.RequestMethod.PATCH;
import static com.github.tomakehurst.wiremock.http.RequestMethod.POST;
import static com.github.tomakehurst.wiremock.http.RequestMethod.PUT;
import static com.github.tomakehurst.wiremock.http.Response.response;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.LinkedList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpOptions;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpTrace;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.InputStreamEntity;

import com.github.tomakehurst.wiremock.common.KeyStoreSettings;
import com.github.tomakehurst.wiremock.common.ProxySettings;
import com.google.common.collect.ImmutableList;

public class ProxyResponseRenderer implements ResponseRenderer {

  private static final int MINUTES = 1000 * 60;
  private static final String TRANSFER_ENCODING = "transfer-encoding";
  private static final String CONTENT_LENGTH = "content-length";
  private static final String HOST_HEADER = "host";

  private final HttpClient client;
  private final boolean preserveHostHeader;
  private final String hostHeaderValue;

  public ProxyResponseRenderer(final ProxySettings proxySettings,
      final KeyStoreSettings trustStoreSettings, final boolean preserveHostHeader,
      final String hostHeaderValue) {
    client = HttpClientFactory.createClient(1000, 5 * MINUTES, proxySettings, trustStoreSettings);

    this.preserveHostHeader = preserveHostHeader;
    this.hostHeaderValue = hostHeaderValue;
  }

  public ProxyResponseRenderer() {
    this(ProxySettings.NO_PROXY, KeyStoreSettings.NO_STORE, false, null);
  }

  public Response render(final ResponseDefinition responseDefinition) {
    HttpUriRequest httpRequest = getHttpRequestFor(responseDefinition);
    addRequestHeaders(httpRequest, responseDefinition);

    try {
      addBodyIfPostPutOrPatch(httpRequest, responseDefinition);
      HttpResponse httpResponse = client.execute(httpRequest);

      return response().status(httpResponse.getStatusLine().getStatusCode())
          .headers(headersFrom(httpResponse, responseDefinition))
          .body(getEntityAsByteArrayAndCloseStream(httpResponse)).fromProxy(true).build();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private HttpHeaders headersFrom(final HttpResponse httpResponse,
      final ResponseDefinition responseDefinition) {
    List<HttpHeader> httpHeaders = new LinkedList<HttpHeader>();
    for (Header header : httpResponse.getAllHeaders()) {
      httpHeaders.add(new HttpHeader(header.getName(), header.getValue()));
    }

    if (responseDefinition.getHeaders() != null) {
      httpHeaders.addAll(responseDefinition.getHeaders().all());
    }

    return new HttpHeaders(httpHeaders);
  }

  private static HttpUriRequest getHttpRequestFor(final ResponseDefinition response) {
    RequestMethod method = response.getOriginalRequest().getMethod();
    String url = response.getProxyUrl();
    notifier().info("Proxying: " + method + " " + url);

    switch (method) {
      case GET:
        return new HttpGet(url);
      case POST:
        return new HttpPost(url);
      case PUT:
        return new HttpPut(url);
      case DELETE:
        return new HttpDelete(url);
      case HEAD:
        return new HttpHead(url);
      case OPTIONS:
        return new HttpOptions(url);
      case TRACE:
        return new HttpTrace(url);
      case PATCH:
        return new HttpPatch(url);
      default:
        throw new RuntimeException("Cannot create HttpMethod for " + method);
    }
  }

  private void addRequestHeaders(final HttpRequest httpRequest, final ResponseDefinition response) {
    Request originalRequest = response.getOriginalRequest();
    for (String key : originalRequest.getAllHeaderKeys()) {
      if (headerShouldBeTransferred(key)) {
        if (!HOST_HEADER.equalsIgnoreCase(key) || preserveHostHeader) {
          String value = originalRequest.getHeader(key);
          httpRequest.addHeader(key, value);
        } else {
          if (hostHeaderValue != null) {
            httpRequest.addHeader(key, hostHeaderValue);
          } else if (response.getProxyBaseUrl() != null) {
            httpRequest.addHeader(key, URI.create(response.getProxyBaseUrl()).getHost());
          }
        }
      }
    }

    if (response.getAdditionalProxyRequestHeaders() != null) {
      for (String key : response.getAdditionalProxyRequestHeaders().keys()) {
        httpRequest.setHeader(key, response.getAdditionalProxyRequestHeaders().getHeader(key)
            .firstValue());
      }
    }
  }

  private static boolean headerShouldBeTransferred(final String key) {
    return !ImmutableList.of(CONTENT_LENGTH, TRANSFER_ENCODING, "connection").contains(
        key.toLowerCase());
  }

  private static void addBodyIfPostPutOrPatch(final HttpRequest httpRequest,
      final ResponseDefinition response) throws UnsupportedEncodingException {
    Request originalRequest = response.getOriginalRequest();
    if (originalRequest.getMethod().isOneOf(PUT, POST, PATCH)) {
      HttpEntityEnclosingRequest requestWithEntity = (HttpEntityEnclosingRequest) httpRequest;
      requestWithEntity.setEntity(buildEntityFrom(originalRequest));
    }
  }

  private static HttpEntity buildEntityFrom(final Request originalRequest) {
    ContentTypeHeader contentTypeHeader = originalRequest.contentTypeHeader().or("text/plain");
    ContentType contentType =
        ContentType.create(contentTypeHeader.mimeTypePart(),
            contentTypeHeader.encodingPart().or("utf-8"));

    if (originalRequest.containsHeader(TRANSFER_ENCODING)
        && originalRequest.header(TRANSFER_ENCODING).firstValue().equals("chunked")) {
      return new InputStreamEntity(new ByteArrayInputStream(originalRequest.getBody()), -1,
          contentType);
    }

    return new ByteArrayEntity(originalRequest.getBody());
  }

}
