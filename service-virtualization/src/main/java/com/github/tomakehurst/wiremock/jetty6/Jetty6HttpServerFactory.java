package com.github.tomakehurst.wiremock.jetty6;

import com.github.tomakehurst.wiremock.core.Options;
import com.github.tomakehurst.wiremock.global.RequestDelayControl;
import com.github.tomakehurst.wiremock.http.AdminRequestHandler;
import com.github.tomakehurst.wiremock.http.HttpServer;
import com.github.tomakehurst.wiremock.http.HttpServerFactory;
import com.github.tomakehurst.wiremock.http.StubRequestHandler;
import com.github.tomakehurst.wiremock.soap.SoapStubRequestHandler;

public class Jetty6HttpServerFactory implements HttpServerFactory {

  public HttpServer buildHttpServer(final Options options,
      final AdminRequestHandler adminRequestHandler, final StubRequestHandler stubRequestHandler,
      final SoapStubRequestHandler soapStubRequestHandler,
      final RequestDelayControl requestDelayControl) {
    return new Jetty6HttpServer(options, adminRequestHandler, stubRequestHandler,
        soapStubRequestHandler, requestDelayControl);
  }
}
