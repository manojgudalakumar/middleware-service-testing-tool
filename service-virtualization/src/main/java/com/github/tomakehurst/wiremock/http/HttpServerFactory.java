package com.github.tomakehurst.wiremock.http;

import com.github.tomakehurst.wiremock.core.Options;
import com.github.tomakehurst.wiremock.global.RequestDelayControl;
import com.github.tomakehurst.wiremock.soap.SoapStubRequestHandler;

public interface HttpServerFactory {

  HttpServer buildHttpServer(Options options, AdminRequestHandler adminRequestHandler,
      StubRequestHandler stubRequestHandler, SoapStubRequestHandler soapStubRequestHandler,
      RequestDelayControl requestDelayControl);
}
