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

import static com.github.tomakehurst.wiremock.http.Response.response;

import com.github.tomakehurst.wiremock.common.BinaryFile;
import com.github.tomakehurst.wiremock.common.FileSource;
import com.github.tomakehurst.wiremock.global.GlobalSettingsHolder;
import com.google.common.base.Optional;

public class StubResponseRenderer implements ResponseRenderer {

  private final FileSource fileSource;
  private final GlobalSettingsHolder globalSettingsHolder;
  private final ProxyResponseRenderer proxyResponseRenderer;

  public StubResponseRenderer(final FileSource fileSource,
      final GlobalSettingsHolder globalSettingsHolder,
      final ProxyResponseRenderer proxyResponseRenderer) {
    this.fileSource = fileSource;
    this.globalSettingsHolder = globalSettingsHolder;
    this.proxyResponseRenderer = proxyResponseRenderer;
  }

  public Response render(final ResponseDefinition responseDefinition) {
    if (!responseDefinition.wasConfigured()) {
      return Response.notConfigured();
    }

    addDelayIfSpecifiedGloballyOrIn(responseDefinition);
    if (responseDefinition.isProxyResponse()) {
      return proxyResponseRenderer.render(responseDefinition);
    } else {
      return renderDirectly(responseDefinition);
    }
  }

  private Response renderDirectly(final ResponseDefinition responseDefinition) {
    Response.Builder responseBuilder =
        response().status(responseDefinition.getStatus()).headers(responseDefinition.getHeaders())
            .fault(responseDefinition.getFault());

    if (responseDefinition.specifiesBodyFile()) {
      BinaryFile bodyFile = fileSource.getBinaryFileNamed(responseDefinition.getBodyFileName());
      responseBuilder.body(bodyFile.readContents());
    } else if (responseDefinition.specifiesBodyContent()) {
      if (responseDefinition.specifiesBinaryBodyContent()) {
        responseBuilder.body(responseDefinition.getByteBody());
      } else {
        responseBuilder.body(responseDefinition.getBody());
      }
    }

    return responseBuilder.build();
  }

  private void addDelayIfSpecifiedGloballyOrIn(final ResponseDefinition response) {
    Optional<Integer> optionalDelay = getDelayFromResponseOrGlobalSetting(response);
    if (optionalDelay.isPresent()) {
      try {
        Thread.sleep(optionalDelay.get());
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
      }
    }
  }

  private Optional<Integer> getDelayFromResponseOrGlobalSetting(final ResponseDefinition response) {
    Integer delay =
        response.getFixedDelayMilliseconds() != null ? response.getFixedDelayMilliseconds()
            : globalSettingsHolder.get().getFixedDelay();

    return Optional.fromNullable(delay);
  }
}
