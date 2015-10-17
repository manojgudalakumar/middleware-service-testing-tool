package com.github.tomakehurst.wiremock.http;

import java.util.Collections;
import java.util.List;

public class QueryParameter extends MultiValue {

  public QueryParameter(final String key, final List<String> values) {
    super(key, values);
  }

  public static QueryParameter absent(final String key) {
    return new QueryParameter(key, Collections.<String>emptyList());
  }
}
