package com.github.tomakehurst.wiremock.http;

import static com.github.tomakehurst.wiremock.common.LocalNotifier.notifier;

import com.github.tomakehurst.wiremock.core.StubServer;

public class StubRequestHandler extends AbstractRequestHandler {

  private final StubServer stubServer;

  public StubRequestHandler(final StubServer stubServer, final ResponseRenderer responseRenderer) {
    super(responseRenderer);
    this.stubServer = stubServer;
  }

  @Override
  public ResponseDefinition handleRequest(final Request request) {
    notifier().info("Request received:\n" + request);

    ResponseDefinition responseDef = stubServer.serveStubFor(request);

    return responseDef;
  }

}
