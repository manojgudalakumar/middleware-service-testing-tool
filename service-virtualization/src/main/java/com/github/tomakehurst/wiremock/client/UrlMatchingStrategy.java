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

import com.github.tomakehurst.wiremock.matching.RequestPattern;

public class UrlMatchingStrategy {

  private String url;
  private String urlPattern;
  private String urlPath;
  private String urlPathPattern;

  public void contributeTo(final RequestPattern requestPattern) {
    requestPattern.setUrl(url);
    requestPattern.setUrlPattern(urlPattern);
    requestPattern.setUrlPath(urlPath);
    requestPattern.setUrlPathPattern(urlPathPattern);
  }

  public void setUrl(final String url) {
    this.url = url;
  }

  public void setUrlPattern(final String urlPattern) {
    this.urlPattern = urlPattern;
  }

  public void setUrlPath(final String urlPath) {
    this.urlPath = urlPath;
  }

  public void setUrlPathPattern(final String urlPathPattern) {
    this.urlPathPattern = urlPathPattern;
  }
}
