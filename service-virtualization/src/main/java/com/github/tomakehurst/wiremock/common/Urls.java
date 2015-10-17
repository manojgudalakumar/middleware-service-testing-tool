package com.github.tomakehurst.wiremock.common;

import java.net.URI;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import com.github.tomakehurst.wiremock.http.QueryParameter;
import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.Maps;

public class Urls {

  public static Map<String, QueryParameter> splitQuery(final String query) {
    if (query == null) {
      return Collections.emptyMap();
    }

    Iterable<String> pairs = Splitter.on('&').split(query);
    ImmutableListMultimap.Builder<String, String> builder = ImmutableListMultimap.builder();
    for (String queryElement : pairs) {
      int firstEqualsIndex = queryElement.indexOf('=');
      if (firstEqualsIndex == -1) {
        builder.putAll(queryElement, "");
      } else {
        String key = queryElement.substring(0, firstEqualsIndex);
        String value = queryElement.substring(firstEqualsIndex + 1);
        builder.putAll(key, value);
      }
    }

    return Maps.transformEntries(builder.build().asMap(),
        new Maps.EntryTransformer<String, Collection<String>, QueryParameter>() {
          public QueryParameter transformEntry(final String key, final Collection<String> values) {
            return new QueryParameter(key, ImmutableList.copyOf(values));
          }
        });
  }

  public static Map<String, QueryParameter> splitQuery(final URI uri) {
    if (uri == null) {
      return Collections.emptyMap();
    }

    return splitQuery(uri.getQuery());
  }
}
