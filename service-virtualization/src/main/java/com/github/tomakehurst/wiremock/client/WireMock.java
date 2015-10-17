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
package com.github.tomakehurst.wiremock.client;

import static com.github.tomakehurst.wiremock.client.RequestPatternBuilder.allRequests;

import java.util.List;

import com.github.tomakehurst.wiremock.core.Admin;
import com.github.tomakehurst.wiremock.global.GlobalSettings;
import com.github.tomakehurst.wiremock.global.RequestDelaySpec;
import com.github.tomakehurst.wiremock.http.RequestMethod;
import com.github.tomakehurst.wiremock.matching.RequestPattern;
import com.github.tomakehurst.wiremock.stubbing.ListStubMappingsResult;
import com.github.tomakehurst.wiremock.stubbing.StubMapping;
import com.github.tomakehurst.wiremock.verification.FindRequestsResult;
import com.github.tomakehurst.wiremock.verification.LoggedRequest;
import com.github.tomakehurst.wiremock.verification.VerificationResult;

import org.skyscreamer.jsonassert.JSONCompareMode;

public class WireMock {

  private static final int DEFAULT_PORT = 8080;
  private static final String DEFAULT_HOST = "localhost";

  private final Admin admin;

  private static ThreadLocal<WireMock> defaultInstance = new ThreadLocal<WireMock>() {
    @Override
    protected WireMock initialValue() {
      return new WireMock();
    }
  };

  public WireMock(final Admin admin) {
    this.admin = admin;
  }

  public WireMock(final int port) {
    this(DEFAULT_HOST, port);
  }

  public WireMock(final String host, final int port) {
    admin = new HttpAdminClient(host, port);
  }

  public WireMock(final String host, final int port, final String urlPathPrefix) {
    admin = new HttpAdminClient(host, port, urlPathPrefix);
  }

  public WireMock() {
    admin = new HttpAdminClient(DEFAULT_HOST, DEFAULT_PORT);
  }

  public static void givenThat(final MappingBuilder mappingBuilder) {
    defaultInstance.get().register(mappingBuilder);
  }

  public static void stubFor(final MappingBuilder mappingBuilder) {
    givenThat(mappingBuilder);
  }

  public static ListStubMappingsResult listAllStubMappings() {
    return defaultInstance.get().allStubMappings();
  }

  public static void configureFor(final int port) {
    defaultInstance.set(new WireMock(port));
  }

  public static void configureFor(final String host, final int port) {
    defaultInstance.set(new WireMock(host, port));
  }

  public static void configureFor(final String host, final int port, final String urlPathPrefix) {
    defaultInstance.set(new WireMock(host, port, urlPathPrefix));
  }

  public static void configure() {
    defaultInstance.set(new WireMock());
  }

  public void saveMappings() {
    admin.saveMappings();
  }

  public static void saveAllMappings() {
    defaultInstance.get().saveMappings();
  }

  public void resetMappings() {
    admin.resetMappings();
  }

  public static void reset() {
    defaultInstance.get().resetMappings();
  }

  public static void resetAllRequests() {
    defaultInstance.get().resetRequests();
  }

  public void resetRequests() {
    admin.resetRequests();
  }

  public void resetScenarios() {
    admin.resetScenarios();
  }

  public static void resetAllScenarios() {
    defaultInstance.get().resetScenarios();
  }

  public void resetToDefaultMappings() {
    admin.resetToDefaultMappings();
  }

  public static void resetToDefault() {
    defaultInstance.get().resetToDefaultMappings();
  }

  public void register(final MappingBuilder mappingBuilder) {
    StubMapping mapping = mappingBuilder.build();
    register(mapping);
  }

  public void register(final StubMapping mapping) {
    admin.addStubMapping(mapping);
  }

  public ListStubMappingsResult allStubMappings() {
    return admin.listAllStubMappings();
  }

  public static UrlMatchingStrategy urlEqualTo(final String url) {
    UrlMatchingStrategy urlStrategy = new UrlMatchingStrategy();
    urlStrategy.setUrl(url);
    return urlStrategy;
  }

  public static UrlMatchingStrategy urlMatching(final String url) {
    UrlMatchingStrategy urlStrategy = new UrlMatchingStrategy();
    urlStrategy.setUrlPattern(url);
    return urlStrategy;
  }

  public static UrlMatchingStrategy urlPathEqualTo(final String urlPath) {
    UrlMatchingStrategy urlStrategy = new UrlMatchingStrategy();
    urlStrategy.setUrlPath(urlPath);
    return urlStrategy;
  }

  public static UrlMatchingStrategy urlPathMatching(final String urlPath) {
    UrlMatchingStrategy urlStrategy = new UrlMatchingStrategy();
    urlStrategy.setUrlPathPattern(urlPath);
    return urlStrategy;
  }

  public static ValueMatchingStrategy equalTo(final String value) {
    ValueMatchingStrategy headerStrategy = new ValueMatchingStrategy();
    headerStrategy.setEqualTo(value);
    return headerStrategy;
  }

  public static ValueMatchingStrategy equalToJson(final String value) {
    ValueMatchingStrategy headerStrategy = new ValueMatchingStrategy();
    headerStrategy.setEqualToJson(value);
    return headerStrategy;
  }

  public static ValueMatchingStrategy equalToJson(final String value,
      final JSONCompareMode jsonCompareMode) {
    ValueMatchingStrategy valueMatchingStrategy = new ValueMatchingStrategy();
    valueMatchingStrategy.setJsonCompareMode(jsonCompareMode);
    valueMatchingStrategy.setEqualToJson(value);
    return valueMatchingStrategy;
  }

  public static ValueMatchingStrategy equalToXml(final String value) {
    ValueMatchingStrategy headerStrategy = new ValueMatchingStrategy();
    headerStrategy.setEqualToXml(value);
    return headerStrategy;
  }

  public static ValueMatchingStrategy matchingXPath(final String value) {
    ValueMatchingStrategy headerStrategy = new ValueMatchingStrategy();
    headerStrategy.setMatchingXPath(value);
    return headerStrategy;
  }

  public static ValueMatchingStrategy containing(final String value) {
    ValueMatchingStrategy headerStrategy = new ValueMatchingStrategy();
    headerStrategy.setContains(value);
    return headerStrategy;
  }

  public static ValueMatchingStrategy matching(final String value) {
    ValueMatchingStrategy headerStrategy = new ValueMatchingStrategy();
    headerStrategy.setMatches(value);
    return headerStrategy;
  }

  public static ValueMatchingStrategy notMatching(final String value) {
    ValueMatchingStrategy headerStrategy = new ValueMatchingStrategy();
    headerStrategy.setDoesNotMatch(value);
    return headerStrategy;
  }

  public static ValueMatchingStrategy matchingJsonPath(final String jsonPath) {
    ValueMatchingStrategy matchingStrategy = new ValueMatchingStrategy();
    matchingStrategy.setJsonMatchesPath(jsonPath);
    return matchingStrategy;
  }

  public static MappingBuilder get(final UrlMatchingStrategy urlMatchingStrategy) {
    return new MappingBuilder(RequestMethod.GET, urlMatchingStrategy);
  }

  public static MappingBuilder post(final UrlMatchingStrategy urlMatchingStrategy) {
    return new MappingBuilder(RequestMethod.POST, urlMatchingStrategy);
  }

  public static MappingBuilder put(final UrlMatchingStrategy urlMatchingStrategy) {
    return new MappingBuilder(RequestMethod.PUT, urlMatchingStrategy);
  }

  public static MappingBuilder delete(final UrlMatchingStrategy urlMatchingStrategy) {
    return new MappingBuilder(RequestMethod.DELETE, urlMatchingStrategy);
  }

  public static MappingBuilder patch(final UrlMatchingStrategy urlMatchingStrategy) {
    return new MappingBuilder(RequestMethod.PATCH, urlMatchingStrategy);
  }

  public static MappingBuilder head(final UrlMatchingStrategy urlMatchingStrategy) {
    return new MappingBuilder(RequestMethod.HEAD, urlMatchingStrategy);
  }

  public static MappingBuilder options(final UrlMatchingStrategy urlMatchingStrategy) {
    return new MappingBuilder(RequestMethod.OPTIONS, urlMatchingStrategy);
  }

  public static MappingBuilder trace(final UrlMatchingStrategy urlMatchingStrategy) {
    return new MappingBuilder(RequestMethod.TRACE, urlMatchingStrategy);
  }

  public static MappingBuilder any(final UrlMatchingStrategy urlMatchingStrategy) {
    return new MappingBuilder(RequestMethod.ANY, urlMatchingStrategy);
  }

  public static MappingBuilder request(final String method,
      final UrlMatchingStrategy urlMatchingStrategy) {
    return new MappingBuilder(RequestMethod.fromString(method), urlMatchingStrategy);
  }

  public static ResponseDefinitionBuilder aResponse() {
    return new ResponseDefinitionBuilder();
  }

  public void verifyThat(final RequestPatternBuilder requestPatternBuilder) {
    RequestPattern requestPattern = requestPatternBuilder.build();
    VerificationResult result = admin.countRequestsMatching(requestPattern);
    result.assertRequestJournalEnabled();

    if (result.getCount() < 1) {
      throw new VerificationException(requestPattern, find(allRequests()));
    }
  }

  public void verifyThat(final int count, final RequestPatternBuilder requestPatternBuilder) {
    RequestPattern requestPattern = requestPatternBuilder.build();
    VerificationResult result = admin.countRequestsMatching(requestPattern);
    result.assertRequestJournalEnabled();

    if (result.getCount() != count) {
      throw new VerificationException(requestPattern, count, find(allRequests()));
    }
  }

  public static void verify(final RequestPatternBuilder requestPatternBuilder) {
    defaultInstance.get().verifyThat(requestPatternBuilder);
  }

  public static void verify(final int count, final RequestPatternBuilder requestPatternBuilder) {
    defaultInstance.get().verifyThat(count, requestPatternBuilder);
  }

  public List<LoggedRequest> find(final RequestPatternBuilder requestPatternBuilder) {
    FindRequestsResult result = admin.findRequestsMatching(requestPatternBuilder.build());
    result.assertRequestJournalEnabled();
    return result.getRequests();
  }

  public static List<LoggedRequest> findAll(final RequestPatternBuilder requestPatternBuilder) {
    return defaultInstance.get().find(requestPatternBuilder);
  }

  public static RequestPatternBuilder getRequestedFor(final UrlMatchingStrategy urlMatchingStrategy) {
    return new RequestPatternBuilder(RequestMethod.GET, urlMatchingStrategy);
  }

  public static RequestPatternBuilder postRequestedFor(final UrlMatchingStrategy urlMatchingStrategy) {
    return new RequestPatternBuilder(RequestMethod.POST, urlMatchingStrategy);
  }

  public static RequestPatternBuilder putRequestedFor(final UrlMatchingStrategy urlMatchingStrategy) {
    return new RequestPatternBuilder(RequestMethod.PUT, urlMatchingStrategy);
  }

  public static RequestPatternBuilder deleteRequestedFor(
      final UrlMatchingStrategy urlMatchingStrategy) {
    return new RequestPatternBuilder(RequestMethod.DELETE, urlMatchingStrategy);
  }

  public static RequestPatternBuilder patchRequestedFor(
      final UrlMatchingStrategy urlMatchingStrategy) {
    return new RequestPatternBuilder(RequestMethod.PATCH, urlMatchingStrategy);
  }

  public static RequestPatternBuilder headRequestedFor(final UrlMatchingStrategy urlMatchingStrategy) {
    return new RequestPatternBuilder(RequestMethod.HEAD, urlMatchingStrategy);
  }

  public static RequestPatternBuilder optionsRequestedFor(
      final UrlMatchingStrategy urlMatchingStrategy) {
    return new RequestPatternBuilder(RequestMethod.OPTIONS, urlMatchingStrategy);
  }

  public static RequestPatternBuilder traceRequestedFor(
      final UrlMatchingStrategy urlMatchingStrategy) {
    return new RequestPatternBuilder(RequestMethod.TRACE, urlMatchingStrategy);
  }

  public static void setGlobalFixedDelay(final int milliseconds) {
    defaultInstance.get().setGlobalFixedDelayVariable(milliseconds);
  }

  public void setGlobalFixedDelayVariable(final int milliseconds) {
    GlobalSettings settings = new GlobalSettings();
    settings.setFixedDelay(milliseconds);
    admin.updateGlobalSettings(settings);
  }

  public void addDelayBeforeProcessingRequests(final int milliseconds) {
    admin.addSocketAcceptDelay(new RequestDelaySpec(milliseconds));
  }

  public static void addRequestProcessingDelay(final int milliseconds) {
    defaultInstance.get().addDelayBeforeProcessingRequests(milliseconds);
  }

  public void shutdown() {
    admin.shutdownServer();
  }

  public static void shutdownServer() {
    defaultInstance.get().shutdown();
  }
}
