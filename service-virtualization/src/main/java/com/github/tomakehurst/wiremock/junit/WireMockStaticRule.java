package com.github.tomakehurst.wiremock.junit;

import org.junit.rules.MethodRule;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.Options;

/**
 * @deprecated JUnit disallows this approach from version 4.11. Use {@link WireMockClassRule}
 *             instead
 */
@Deprecated
public class WireMockStaticRule implements MethodRule {

  private final WireMockServer wireMockServer;

  public WireMockStaticRule(final int port) {
    wireMockServer = new WireMockServer(port);
    wireMockServer.start();
    WireMock.configureFor("localhost", port);
  }

  public WireMockStaticRule() {
    this(Options.DEFAULT_PORT);
  }

  public void stopServer() {
    wireMockServer.stop();
  }

  public Statement apply(final Statement base, final FrameworkMethod method, final Object target) {
    return new Statement() {

      @Override
      public void evaluate() throws Throwable {
        try {
          before();
          base.evaluate();
        } finally {
          after();
          WireMock.reset();
        }
      }

    };
  }

  protected void before() {
    // NOOP
  }

  protected void after() {
    // NOOP
  }
}
