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
package com.github.tomakehurst.wiremock.common;

import static com.google.common.base.Splitter.on;
import static com.google.common.collect.FluentIterable.from;
import static com.google.common.collect.Iterables.size;
import static java.lang.Math.min;

import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.client.utils.URIBuilder;

import com.github.tomakehurst.wiremock.http.Request;
import com.google.common.base.Joiner;

public class UniqueFilenameGenerator {
  public static String generate(final Request request, final String prefix, final String id) {
    URI uri = URI.create(request.getUrl());
    return getRandomId(prefix, id, uri);
  }

  private static String getRandomId(final String prefix, final String id, final URI uri) {
    Iterable<String> uriPathNodes = on("/").omitEmptyStrings().split(uri.getPath());
    int nodeCount = size(uriPathNodes);

    String pathPart =
        nodeCount > 0 ? Joiner.on("-").join(from(uriPathNodes).skip(nodeCount - min(nodeCount, 2)))
            : "(root)";

    return new StringBuilder(prefix).append("-").append(pathPart).append("-").append(id)
        .append(".json").toString();
  }

  public static String generateIdFromUrl(final String prefix, final String id, final String url) {
    URIBuilder urlbuild = null;
    try {
      urlbuild = new URIBuilder(url);
      urlbuild = urlbuild.removeQuery();
      return getRandomId(prefix, id, new URI(urlbuild.toString()));
    } catch (URISyntaxException e) {
      e.printStackTrace();
    }
    return null;
  }

}
