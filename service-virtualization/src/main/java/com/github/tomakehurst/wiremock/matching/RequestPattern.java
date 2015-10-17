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
package com.github.tomakehurst.wiremock.matching;

import static com.github.tomakehurst.wiremock.common.LocalNotifier.notifier;
import static com.github.tomakehurst.wiremock.http.RequestMethod.ANY;
import static com.github.tomakehurst.wiremock.matching.ValuePattern.matching;
import static com.google.common.base.Predicates.notNull;
import static com.google.common.collect.FluentIterable.from;
import static com.google.common.collect.Iterables.all;
import static com.google.common.collect.Iterables.any;
import static com.google.common.collect.Iterables.filter;
import static com.google.common.collect.Iterables.size;
import static com.google.common.collect.Iterables.transform;
import static com.google.common.collect.Maps.newLinkedHashMap;
import static java.util.Arrays.asList;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize.Inclusion;
import com.github.tomakehurst.wiremock.common.Json;
import com.github.tomakehurst.wiremock.http.HttpHeader;
import com.github.tomakehurst.wiremock.http.QueryParameter;
import com.github.tomakehurst.wiremock.http.Request;
import com.github.tomakehurst.wiremock.http.RequestMethod;
import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableSet;

@JsonSerialize(include = Inclusion.NON_NULL)
public class RequestPattern {

  private String urlPattern;
  private String url;
  private String urlPath;
  private String urlPathPattern;
  private RequestMethod method;
  private Map<String, ValuePattern> headerPatterns;
  private Map<String, ValuePattern> queryParamPatterns;
  private List<ValuePattern> bodyPatterns;

  public RequestPattern(final RequestMethod method, final String url,
      final Map<String, ValuePattern> headerPatterns,
      final Map<String, ValuePattern> queryParamPatterns) {
    this.url = url;
    this.method = method;
    this.headerPatterns = headerPatterns;
    this.queryParamPatterns = queryParamPatterns;
  }

  public RequestPattern(final RequestMethod method, final String url,
      final Map<String, ValuePattern> headerPatterns) {
    this.url = url;
    this.method = method;
    this.headerPatterns = headerPatterns;
  }

  public RequestPattern(final RequestMethod method) {
    this.method = method;
  }

  public RequestPattern(final RequestMethod method, final String url) {
    this.url = url;
    this.method = method;
  }

  public RequestPattern() {}

  public static RequestPattern everything() {
    RequestPattern requestPattern = new RequestPattern(RequestMethod.ANY);
    requestPattern.setUrlPattern(".*");
    return requestPattern;
  }

  public static RequestPattern buildRequestPatternFrom(final String json) {
    return Json.read(json, RequestPattern.class);
  }

  private void assertIsInValidState() {
    if (from(asList(url, urlPath, urlPattern, urlPathPattern)).filter(notNull()).size() > 1) {
      throw new IllegalStateException(
          "Only one of url, urlPattern, urlPath or urlPathPattern may be set");
    }
  }

  public boolean isMatchedBy(final Request request) {
    return (urlIsMatch(request) && methodMatches(request)
        && requiredAbsentHeadersAreNotPresentIn(request) && headersMatch(request)
        && queryParametersMatch(request) && bodyMatches(request));
  }

  private boolean urlIsMatch(final Request request) {
    String candidateUrl = request.getUrl();
    boolean matched;
    if (url != null) {
      matched = url.equals(candidateUrl);
    } else if (urlPattern != null) {
      matched = candidateUrl.matches(urlPattern);
    } else if (urlPathPattern != null) {
      matched = candidateUrl.matches(urlPathPattern.concat(".*"));
    } else {
      matched = candidateUrl.startsWith(urlPath);
    }

    return matched;
  }

  private boolean methodMatches(final Request request) {
    boolean matched = method.equals(ANY) || request.getMethod().equals(method);
    if (!matched) {
      notifier().info(
          String.format("URL %s is match, but method %s is not", request.getUrl(),
              request.getMethod()));
    }

    return matched;
  }

  private boolean requiredAbsentHeadersAreNotPresentIn(final Request request) {
    return !any(requiredAbsentHeaderKeys(), new Predicate<String>() {
      @Override
      public boolean apply(final String key) {
        return request.getAllHeaderKeys().contains(key);
      }
    });
  }

  private Set<String> requiredAbsentHeaderKeys() {
    if (headerPatterns == null) {
      return ImmutableSet.of();
    }

    return ImmutableSet.copyOf(filter(
        transform(headerPatterns.entrySet(), TO_KEYS_WHERE_VALUE_ABSENT), REMOVING_NULL));
  }

  private boolean headersMatch(final Request request) {
    return noHeadersAreRequiredToBePresent()
        || all(headerPatterns.entrySet(), matchHeadersIn(request));
  }

  private boolean queryParametersMatch(final Request request) {
    return (queryParamPatterns == null || all(queryParamPatterns.entrySet(),
        matchQueryParametersIn(request)));
  }

  private boolean noHeadersAreRequiredToBePresent() {
    return headerPatterns == null || allHeaderPatternsSpecifyAbsent();
  }

  private boolean allHeaderPatternsSpecifyAbsent() {
    return size(filter(headerPatterns.values(), new Predicate<ValuePattern>() {
      @Override
      public boolean apply(final ValuePattern headerPattern) {
        return !headerPattern.nullSafeIsAbsent();
      }
    })) == 0;
  }

  private boolean bodyMatches(final Request request) {
    if (bodyPatterns == null) {
      return true;
    }

    boolean matches = all(bodyPatterns, matching(request.getBodyAsString()));

    if (!matches) {
      notifier().info(
          String.format("URL %s is match, but body is not: %s", request.getUrl(),
              request.getBodyAsString()));
    }

    return matches;
  }

  public String getUrlPattern() {
    return urlPattern;
  }

  public void setUrlPattern(final String urlPattern) {
    this.urlPattern = urlPattern;
    assertIsInValidState();
  }

  public RequestMethod getMethod() {
    return method;
  }

  public void setMethod(final RequestMethod method) {
    this.method = method;
  }

  public Map<String, ValuePattern> getHeaders() {
    return headerPatterns;
  }

  public Map<String, ValuePattern> getQueryParameters() {
    return queryParamPatterns;
  }

  public void setQueryParameters(final Map<String, ValuePattern> queryParamPatterns) {
    this.queryParamPatterns = queryParamPatterns;
  }

  public void addHeader(final String key, final ValuePattern pattern) {
    if (headerPatterns == null) {
      headerPatterns = newLinkedHashMap();
    }

    headerPatterns.put(key, pattern);
  }

  public void addQueryParam(final String key, final ValuePattern valuePattern) {
    if (queryParamPatterns == null) {
      queryParamPatterns = newLinkedHashMap();
    }

    queryParamPatterns.put(key, valuePattern);
  }

  public void setHeaders(final Map<String, ValuePattern> headers) {
    this.headerPatterns = headers;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(final String url) {
    this.url = url;
    assertIsInValidState();
  }

  public String getUrlPath() {
    return urlPath;
  }

  public void setUrlPath(final String urlPath) {
    this.urlPath = urlPath;
    assertIsInValidState();
  }

  public String getUrlPathPattern() {
    return urlPathPattern;
  }

  public void setUrlPathPattern(final String urlPathPattern) {
    this.urlPathPattern = urlPathPattern;
    assertIsInValidState();
  }

  public List<ValuePattern> getBodyPatterns() {
    return bodyPatterns;
  }

  public void setBodyPatterns(final List<ValuePattern> bodyPatterns) {
    this.bodyPatterns = bodyPatterns;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((bodyPatterns == null) ? 0 : bodyPatterns.hashCode());
    result = prime * result + ((headerPatterns == null) ? 0 : headerPatterns.hashCode());
    result = prime * result + ((method == null) ? 0 : method.hashCode());
    result = prime * result + ((url == null) ? 0 : url.hashCode());
    result = prime * result + ((urlPattern == null) ? 0 : urlPattern.hashCode());
    return result;
  }

  @Override
  public boolean equals(final Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    RequestPattern other = (RequestPattern) obj;
    if (bodyPatterns == null) {
      if (other.bodyPatterns != null) {
        return false;
      }
    } else if (!bodyPatterns.equals(other.bodyPatterns)) {
      return false;
    }
    if (headerPatterns == null) {
      if (other.headerPatterns != null) {
        return false;
      }
    } else if (!headerPatterns.equals(other.headerPatterns)) {
      return false;
    }
    if (!method.equals(other.method)) {
      return false;
    }
    if (url == null) {
      if (other.url != null) {
        return false;
      }
    } else if (!url.equals(other.url)) {
      return false;
    }
    if (urlPattern == null) {
      if (other.urlPattern != null) {
        return false;
      }
    } else if (!urlPattern.equals(other.urlPattern)) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return Json.write(this);
  }

  private static final Function<Map.Entry<String, ValuePattern>, String> TO_KEYS_WHERE_VALUE_ABSENT =
      new Function<Map.Entry<String, ValuePattern>, String>() {
        @Override
        public String apply(final Map.Entry<String, ValuePattern> input) {
          return input.getValue().nullSafeIsAbsent() ? input.getKey() : null;
        }
      };

  private static final Predicate<String> REMOVING_NULL = new Predicate<String>() {
    @Override
    public boolean apply(final String input) {
      return input != null;
    }
  };

  private static Predicate<Map.Entry<String, ValuePattern>> matchHeadersIn(final Request request) {
    return new Predicate<Map.Entry<String, ValuePattern>>() {
      @Override
      public boolean apply(final Map.Entry<String, ValuePattern> headerPattern) {
        ValuePattern headerValuePattern = headerPattern.getValue();
        String key = headerPattern.getKey();
        HttpHeader header = request.header(key);

        boolean match = header.hasValueMatching(headerValuePattern);

        if (!match) {
          notifier().info(
              String.format("URL %s is match, but header %s is not. For a match, value should %s",
                  request.getUrl(), key, headerValuePattern.toString()));
        }

        return match;
      }
    };
  }

  private Predicate<? super Map.Entry<String, ValuePattern>> matchQueryParametersIn(
      final Request request) {
    return new Predicate<Map.Entry<String, ValuePattern>>() {
      @Override
      public boolean apply(final Map.Entry<String, ValuePattern> entry) {
        ValuePattern valuePattern = entry.getValue();
        String key = entry.getKey();
        Optional<QueryParameter> queryParam = Optional.fromNullable(request.queryParameter(key));
        boolean match = queryParam.isPresent() && queryParam.get().hasValueMatching(valuePattern);

        if (!match) {
          notifier().info(
              String.format(
                  "URL %s is match, but query parameter %s is not. For a match, value should %s",
                  request.getUrl(), key, valuePattern.toString()));
        }

        return match;
      }
    };
  }
}
