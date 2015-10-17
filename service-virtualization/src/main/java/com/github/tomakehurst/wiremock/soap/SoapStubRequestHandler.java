package com.github.tomakehurst.wiremock.soap;

import static com.github.tomakehurst.wiremock.common.LocalNotifier.notifier;

import com.github.tomakehurst.wiremock.core.StubServer;
import com.github.tomakehurst.wiremock.http.AbstractRequestHandler;
import com.github.tomakehurst.wiremock.http.Request;
import com.github.tomakehurst.wiremock.http.ResponseDefinition;

public class SoapStubRequestHandler extends AbstractRequestHandler {

  private final StubServer stubServer;

  public SoapStubRequestHandler(final StubServer stubServer,
      final StubSoapResponseRender responseRenderer) {
    super(responseRenderer);
    this.stubServer = stubServer;
  }

  @Override
  public ResponseDefinition handleRequest(final Request request) {
    System.out.println("The recevied request is" + request);
    notifier().info("Request received:\n" + request);

    ResponseDefinition responseDef = stubServer.serveStubFor(request);

    return responseDef;
  }

}
