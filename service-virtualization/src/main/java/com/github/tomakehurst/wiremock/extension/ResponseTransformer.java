package com.github.tomakehurst.wiremock.extension;

import com.github.tomakehurst.wiremock.common.FileSource;
import com.github.tomakehurst.wiremock.http.Request;
import com.github.tomakehurst.wiremock.http.ResponseDefinition;

public abstract class ResponseTransformer implements Extension {

  public abstract ResponseDefinition transform(Request request,
      ResponseDefinition responseDefinition, FileSource files);

  public boolean applyGlobally() {
    return true;
  }
}
