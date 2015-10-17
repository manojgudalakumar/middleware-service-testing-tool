package com.github.tomakehurst.wiremock;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static com.google.common.base.Preconditions.checkState;

import java.util.List;

import com.github.tomakehurst.wiremock.client.MappingBuilder;
import com.github.tomakehurst.wiremock.client.RequestPatternBuilder;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.common.FatalStartupException;
import com.github.tomakehurst.wiremock.common.FileSource;
import com.github.tomakehurst.wiremock.common.Notifier;
import com.github.tomakehurst.wiremock.common.ProxySettings;
import com.github.tomakehurst.wiremock.core.Admin;
import com.github.tomakehurst.wiremock.core.Container;
import com.github.tomakehurst.wiremock.core.Options;
import com.github.tomakehurst.wiremock.core.WireMockApp;
import com.github.tomakehurst.wiremock.extension.ResponseTransformer;
import com.github.tomakehurst.wiremock.global.GlobalSettings;
import com.github.tomakehurst.wiremock.global.GlobalSettingsHolder;
import com.github.tomakehurst.wiremock.global.RequestDelayControl;
import com.github.tomakehurst.wiremock.global.RequestDelaySpec;
import com.github.tomakehurst.wiremock.global.ThreadSafeRequestDelayControl;
import com.github.tomakehurst.wiremock.http.AdminRequestHandler;
import com.github.tomakehurst.wiremock.http.BasicResponseRenderer;
import com.github.tomakehurst.wiremock.http.HttpServer;
import com.github.tomakehurst.wiremock.http.HttpServerFactory;
import com.github.tomakehurst.wiremock.http.ProxyResponseRenderer;
import com.github.tomakehurst.wiremock.http.RequestListener;
import com.github.tomakehurst.wiremock.http.StubRequestHandler;
import com.github.tomakehurst.wiremock.http.StubResponseRenderer;
import com.github.tomakehurst.wiremock.jetty6.Jetty6HttpServerFactory;
import com.github.tomakehurst.wiremock.jetty6.LoggerAdapter;
import com.github.tomakehurst.wiremock.junit.Stubbing;
import com.github.tomakehurst.wiremock.matching.RequestPattern;
import com.github.tomakehurst.wiremock.soap.SoapStubRequestHandler;
import com.github.tomakehurst.wiremock.soap.StubSoapResponseRender;
import com.github.tomakehurst.wiremock.standalone.JsonFileMappingsLoader;
import com.github.tomakehurst.wiremock.standalone.JsonFileMappingsSaver;
import com.github.tomakehurst.wiremock.standalone.MappingsLoader;
import com.github.tomakehurst.wiremock.stubbing.ListStubMappingsResult;
import com.github.tomakehurst.wiremock.stubbing.StubMapping;
import com.github.tomakehurst.wiremock.stubbing.StubMappingJsonRecorder;
import com.github.tomakehurst.wiremock.stubbing.StubMappings;
import com.github.tomakehurst.wiremock.verification.FindRequestsResult;
import com.github.tomakehurst.wiremock.verification.LoggedRequest;
import com.github.tomakehurst.wiremock.verification.VerificationResult;
import com.mstt.qa.servicevirtualization.servicevirtualizationutils.dto.UserDefinedProjectDetailsDto;

import org.mortbay.log.Log;

public class WireMockServer implements Container, Stubbing, Admin {

  public static final String FILES_ROOT = "__files";
  public static final String MAPPINGS_ROOT = "mappings";

  private final WireMockApp wireMockApp;
  private final StubRequestHandler stubRequestHandler;
  private final SoapStubRequestHandler soapStubRequestHandler;

  private final HttpServer httpServer;
  private final FileSource fileSource;
  private final Notifier notifier;

  private final Options options;

  protected final WireMock client;

  public WireMockServer(final Options options) {
    this.options = options;
    this.fileSource = options.filesRoot();
    this.notifier = options.notifier();

    RequestDelayControl requestDelayControl = new ThreadSafeRequestDelayControl();
    MappingsLoader defaultMappingsLoader = makeDefaultMappingsLoader();
    JsonFileMappingsSaver mappingsSaver =
        new JsonFileMappingsSaver(fileSource.child(MAPPINGS_ROOT));

    wireMockApp =
        new WireMockApp(requestDelayControl, options.browserProxyingEnabled(),
            defaultMappingsLoader, mappingsSaver, options.requestJournalDisabled(),
            options.maxRequestJournalEntries(),
            options.extensionsOfType(ResponseTransformer.class), fileSource, this);

    AdminRequestHandler adminRequestHandler =
        new AdminRequestHandler(wireMockApp, new BasicResponseRenderer());
    stubRequestHandler =
        new StubRequestHandler(wireMockApp, new StubResponseRenderer(fileSource.child(FILES_ROOT),
            wireMockApp.getGlobalSettingsHolder(), new ProxyResponseRenderer(options.proxyVia(),
                options.httpsSettings().trustStore(), options.shouldPreserveHostHeader(),
                options.proxyHostHeader())));
    soapStubRequestHandler =
        new SoapStubRequestHandler(wireMockApp, new StubSoapResponseRender(
            fileSource.child(FILES_ROOT), wireMockApp.getGlobalSettingsHolder(),
            new ProxyResponseRenderer(options.proxyVia(), options.httpsSettings().trustStore(),
                options.shouldPreserveHostHeader(), options.proxyHostHeader())));
    HttpServerFactory httpServerFactory = new Jetty6HttpServerFactory();
    httpServer =
        httpServerFactory.buildHttpServer(options, adminRequestHandler, stubRequestHandler,
            soapStubRequestHandler, requestDelayControl);

    Log.setLog(new LoggerAdapter(notifier));

    client = new WireMock(wireMockApp);
  }

  private MappingsLoader makeDefaultMappingsLoader() {
    FileSource mappingsFileSource = fileSource.child(MAPPINGS_ROOT);
    if (mappingsFileSource.exists()) {
      return new JsonFileMappingsLoader(mappingsFileSource);
    } else {
      return new NoOpMappingsLoader();
    }
  }

  public WireMockServer(final int port, final Integer httpsPort, final FileSource fileSource,
      final boolean enableBrowserProxying, final ProxySettings proxySettings,
      final Notifier notifier) {
    this(wireMockConfig().port(port).httpsPort(httpsPort).fileSource(fileSource)
        .enableBrowserProxying(enableBrowserProxying).proxyVia(proxySettings).notifier(notifier));
  }

  public WireMockServer(final int port, final FileSource fileSource,
      final boolean enableBrowserProxying, final ProxySettings proxySettings) {
    this(wireMockConfig().port(port).fileSource(fileSource)
        .enableBrowserProxying(enableBrowserProxying).proxyVia(proxySettings));
  }

  public WireMockServer(final int port, final FileSource fileSource,
      final boolean enableBrowserProxying) {
    this(wireMockConfig().port(port).fileSource(fileSource)
        .enableBrowserProxying(enableBrowserProxying));
  }

  public WireMockServer(final int port) {
    this(wireMockConfig().port(port));
  }

  public WireMockServer(final int port, final Integer httpsPort) {
    this(wireMockConfig().port(port).httpsPort(httpsPort));
  }

  public WireMockServer() {
    this(wireMockConfig());
  }

  public void loadMappingsUsing(final MappingsLoader mappingsLoader) {
    wireMockApp.loadMappingsUsing(mappingsLoader);
  }

  public GlobalSettingsHolder getGlobalSettingsHolder() {
    return wireMockApp.getGlobalSettingsHolder();
  }

  public void addMockServiceRequestListener(final RequestListener listener) {
    stubRequestHandler.addRequestListener(listener);
    soapStubRequestHandler.addRequestListener(listener);
  }

  public void enableRecordMappings(final FileSource mappingsFileSource,
      final FileSource filesFileSource) {
    addMockServiceRequestListener(new StubMappingJsonRecorder(mappingsFileSource, filesFileSource,
        wireMockApp, options.matchingHeaders()));
    notifier.info("Recording mappings to " + mappingsFileSource.getPath());
  }

  public void enableRecordMappings(final FileSource mappingsFileSource,
      final FileSource filesFileSource, final UserDefinedProjectDetailsDto usrDto,
      final Options addStubToProject) {
    addMockServiceRequestListener(new StubMappingJsonRecorder(mappingsFileSource, filesFileSource,
        wireMockApp, options.matchingHeaders(), usrDto, addStubToProject));
    notifier.info("Recording mappings to " + mappingsFileSource.getPath());
  }

  public void stop() {
    httpServer.stop();
  }

  public void start() {
    try {
      httpServer.start();
    } catch (Exception e) {
      throw new FatalStartupException(e);
    }
  }

  /**
   * Gracefully shutdown the server.
   *
   * This method assumes it is being called as the result of an incoming HTTP request.
   */

  @Override
  public void shutdown() {
    final WireMockServer server = this;
    Thread shutdownThread = new Thread(new Runnable() {

      @Override
      public void run() {
        try {
          // We have to sleep briefly to finish serving the shutdown
          // request before stopping the server, as
          // there's no support in Jetty for shutting down after the
          // current request.
          // See http://stackoverflow.com/questions/4650713
          Thread.sleep(100);
        } catch (InterruptedException e) {
          throw new RuntimeException(e);
        }
        server.stop();
      }
    });
    shutdownThread.start();
  }

  @Override
  public int port() {
    checkState(isRunning(),
        "Not listening on HTTP port. The WireMock server is most likely stopped");
    return httpServer.port();
  }

  public int httpsPort() {
    checkState(isRunning() && options.httpsSettings().enabled(),
        "Not listening on HTTPS port. Either HTTPS is not enabled or the WireMock server is stopped.");
    return httpServer.httpsPort();
  }

  public boolean isRunning() {
    return httpServer.isRunning();
  }

  @Override
  public void givenThat(final MappingBuilder mappingBuilder) {
    client.register(mappingBuilder);
  }

  @Override
  public void stubFor(final MappingBuilder mappingBuilder) {
    givenThat(mappingBuilder);
  }

  @Override
  public void verify(final RequestPatternBuilder requestPatternBuilder) {
    client.verifyThat(requestPatternBuilder);
  }

  @Override
  public void verify(final int count, final RequestPatternBuilder requestPatternBuilder) {
    client.verifyThat(count, requestPatternBuilder);
  }

  @Override
  public List<LoggedRequest> findAll(final RequestPatternBuilder requestPatternBuilder) {
    return client.find(requestPatternBuilder);
  }

  @Override
  public void setGlobalFixedDelay(final int milliseconds) {
    client.setGlobalFixedDelayVariable(milliseconds);
  }

  @Override
  public void addRequestProcessingDelay(final int milliseconds) {
    client.addDelayBeforeProcessingRequests(milliseconds);
  }

  @Override
  public void addStubMapping(final StubMapping stubMapping) {
    wireMockApp.addStubMapping(stubMapping);
  }

  @Override
  public ListStubMappingsResult listAllStubMappings() {
    return wireMockApp.listAllStubMappings();
  }

  @Override
  public void saveMappings() {
    wireMockApp.saveMappings();
  }

  @Override
  public void resetMappings() {
    wireMockApp.resetMappings();
  }

  @Override
  public void resetToDefaultMappings() {
    wireMockApp.resetToDefaultMappings();
  }

  @Override
  public void resetScenarios() {
    wireMockApp.resetScenarios();
  }

  @Override
  public VerificationResult countRequestsMatching(final RequestPattern requestPattern) {
    return wireMockApp.countRequestsMatching(requestPattern);
  }

  @Override
  public FindRequestsResult findRequestsMatching(final RequestPattern requestPattern) {
    return wireMockApp.findRequestsMatching(requestPattern);
  }

  @Override
  public void updateGlobalSettings(final GlobalSettings newSettings) {
    wireMockApp.updateGlobalSettings(newSettings);
  }

  @Override
  public void addSocketAcceptDelay(final RequestDelaySpec delaySpec) {
    wireMockApp.addSocketAcceptDelay(delaySpec);
  }

  @Override
  public void shutdownServer() {
    shutdown();
  }

  @Override
  public void resetRequests() {
    wireMockApp.resetRequests();
  }

  private static class NoOpMappingsLoader implements MappingsLoader {

    @Override
    public void loadMappingsInto(final StubMappings stubMappings) {
      // do nothing
    }
  }
}
