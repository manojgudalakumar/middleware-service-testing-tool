package com.github.tomakehurst.wiremock.junit;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

import org.junit.rules.MethodRule;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.Options;

public class WireMockClassRule extends WireMockServer implements MethodRule, TestRule {

  public WireMockClassRule(final Options options) {
    super(options);
  }

  public WireMockClassRule(final int port, final Integer httpsPort) {
    this(wireMockConfig().port(port).httpsPort(httpsPort));
  }

  public WireMockClassRule(final int port) {
    this(wireMockConfig().port(port));
  }

  public WireMockClassRule() {
    this(wireMockConfig());
  }

  public Statement apply(final Statement base, final FrameworkMethod method, final Object target) {
    return apply(base, null);
  }

  public Statement apply(final Statement base, final Description description) {
    return new Statement() {

      @Override
      public void evaluate() throws Throwable {
        if (isRunning()) {
          try {
            before();
            base.evaluate();
          } finally {
            after();
            client.resetMappings();
          }
        } else {
          start();
          WireMock.configureFor("localhost", port());
          try {
            before();
            base.evaluate();
          } finally {
            after();
            stop();
          }
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
