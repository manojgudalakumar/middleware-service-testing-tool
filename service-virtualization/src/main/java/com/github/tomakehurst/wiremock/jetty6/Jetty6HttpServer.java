package com.github.tomakehurst.wiremock.jetty6;

import static com.github.tomakehurst.wiremock.core.WireMockApp.ADMIN_CONTEXT_ROOT;
import static com.github.tomakehurst.wiremock.jetty6.Jetty6HandlerDispatchingServlet.SHOULD_FORWARD_TO_FILES_CONTEXT;
import static com.google.common.collect.Maps.newHashMap;

import java.util.Map;

import org.mortbay.jetty.Handler;
import org.mortbay.jetty.MimeTypes;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.bio.SocketConnector;
import org.mortbay.jetty.handler.ContextHandlerCollection;
import org.mortbay.jetty.servlet.Context;
import org.mortbay.jetty.servlet.DefaultServlet;
import org.mortbay.jetty.servlet.ServletHolder;
import org.mortbay.thread.QueuedThreadPool;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.common.FileSource;
import com.github.tomakehurst.wiremock.common.HttpsSettings;
import com.github.tomakehurst.wiremock.common.JettySettings;
import com.github.tomakehurst.wiremock.common.Notifier;
import com.github.tomakehurst.wiremock.core.Options;
import com.github.tomakehurst.wiremock.global.RequestDelayControl;
import com.github.tomakehurst.wiremock.http.AdminRequestHandler;
import com.github.tomakehurst.wiremock.http.HttpServer;
import com.github.tomakehurst.wiremock.http.RequestHandler;
import com.github.tomakehurst.wiremock.http.StubRequestHandler;
import com.github.tomakehurst.wiremock.servlet.ContentTypeSettingFilter;
import com.github.tomakehurst.wiremock.servlet.TrailingSlashFilter;
import com.github.tomakehurst.wiremock.soap.SoapStubRequestHandler;

class Jetty6HttpServer implements HttpServer {

  private static final String FILES_URL_MATCH = String.format("/%s/*", WireMockServer.FILES_ROOT);

  private final Server jettyServer;
  private final DelayableSocketConnector httpConnector;
  private final DelayableSocketConnector soaphttpConnector;
  private final DelayableSslSocketConnector httpsConnector;
  private final ContextHandlerCollection contextHanlderCollection;

  Jetty6HttpServer(final Options options, final AdminRequestHandler adminRequestHandler,
      final StubRequestHandler stubRequestHandler,
      final SoapStubRequestHandler soapStubRequestHandler,
      final RequestDelayControl requestDelayControl) {
    contextHanlderCollection = new ContextHandlerCollection();
    jettyServer = new Server();

    QueuedThreadPool threadPool = new QueuedThreadPool(options.containerThreads());
    jettyServer.setThreadPool(threadPool);

    httpConnector =
        createConnector(requestDelayControl, options.bindAddress(), options.portNumber(),
            options.jettySettings());
    httpConnector.setName("httpConnector");
    soaphttpConnector =
        createConnector(requestDelayControl, options.bindAddress(), options.soapPortNumber(),
            options.jettySettings());
    soaphttpConnector.setName("soapConnector");
    jettyServer.addConnector(httpConnector);
    jettyServer.addConnector(soaphttpConnector);

    if (options.httpsSettings().enabled()) {
      httpsConnector =
          createHttpsConnector(requestDelayControl, options.httpsSettings(),
              options.jettySettings());
      jettyServer.addConnector(httpsConnector);
    } else {
      httpsConnector = null;
    }

    Notifier notifier = options.notifier();
    addAdminContext(adminRequestHandler, notifier);
    addMockServiceContext(stubRequestHandler, options.filesRoot(), notifier);
    addMockSoapServiceContext(soapStubRequestHandler, options.filesRoot(), notifier);
    addToServer(jettyServer);

  }

  private void addToServer(final Server jettyServer2) {
    contextHanlderCollection.setServer(jettyServer2);
  }

  @Override
  public void start() {
    try {
      jettyServer.start();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
    long timeout = System.currentTimeMillis() + 30000;
    while (!jettyServer.isStarted()) {
      try {
        Thread.currentThread().sleep(100);
      } catch (InterruptedException e) {
        // no-op
      }
      if (System.currentTimeMillis() > timeout) {
        throw new RuntimeException("Server took too long to start up.");
      }
    }
  }

  @Override
  public void stop() {
    try {
      jettyServer.stop();
      jettyServer.join();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public boolean isRunning() {
    return jettyServer != null && jettyServer.isRunning();
  }

  @Override
  public int port() {
    return httpConnector.getLocalPort();
  }

  @Override
  public int httpsPort() {
    return httpsConnector.getLocalPort();
  }

  private DelayableSocketConnector createConnector(final RequestDelayControl requestDelayControl,
      final String bindAddress, final int port, final JettySettings jettySettings) {
    DelayableSocketConnector connector = new DelayableSocketConnector(requestDelayControl);
    connector.setHost(bindAddress);
    connector.setPort(port);
    setJettySettings(jettySettings, connector);
    return connector;
  }

  private DelayableSslSocketConnector createHttpsConnector(
      final RequestDelayControl requestDelayControl, final HttpsSettings httpsSettings,
      final JettySettings jettySettings) {
    DelayableSslSocketConnector connector = new DelayableSslSocketConnector(requestDelayControl);
    connector.setPort(httpsSettings.port());
    connector.setKeystore(httpsSettings.keyStorePath());
    connector.setKeyPassword(httpsSettings.keyStorePassword());

    if (httpsSettings.hasTrustStore()) {
      connector.setTruststore(httpsSettings.trustStorePath());
      connector.setTrustPassword(httpsSettings.trustStorePassword());
    }

    connector.setNeedClientAuth(httpsSettings.needClientAuth());

    setJettySettings(jettySettings, connector);
    return connector;
  }

  private void setJettySettings(final JettySettings jettySettings, final SocketConnector connector) {
    if (jettySettings.getAcceptors().isPresent()) {
      connector.setAcceptors(jettySettings.getAcceptors().get());
    }
    if (jettySettings.getAcceptQueueSize().isPresent()) {
      connector.setAcceptQueueSize(jettySettings.getAcceptQueueSize().get());
    }

    int headerBufferSize = 8192;
    if (jettySettings.getRequestHeaderSize().isPresent()) {
      headerBufferSize = jettySettings.getRequestHeaderSize().get();
    }
    connector.setHeaderBufferSize(headerBufferSize);
  }

  private void addMockSoapServiceContext(final SoapStubRequestHandler soapStubRequestHandler,
      final FileSource filesRoot, final Notifier notifier) {
    Context mockServiceContext = getMockServiceContext(filesRoot, notifier);
    mockServiceContext.setAttribute(SoapStubRequestHandler.class.getName(), soapStubRequestHandler);
    ServletHolder soapServletHolder =
        mockServiceContext.addServlet(Jetty6SoapRequestHandlerDispatchingServlet.class, "/");
    soapServletHolder.setInitParameter(RequestHandler.HANDLER_CLASS_KEY,
        SoapStubRequestHandler.class.getName());
    soapServletHolder.setInitParameter(SHOULD_FORWARD_TO_FILES_CONTEXT, "true");
    mockServiceContext.setConnectorNames(new String[] {"soapConnector"});
    addContextToCollection(mockServiceContext);
  }

  private void addContextToCollection(final Context mockServiceContext) {
    contextHanlderCollection.addHandler(mockServiceContext);
  }

  private Context getMockServiceContext(final FileSource filesRoot, final Notifier notifier) {
    Context mockServiceContext = new Context(jettyServer, "/");
    Map<String, String> initParams = newHashMap();
    initParams.put("org.mortbay.jetty.servlet.Default.maxCacheSize", "0");
    initParams.put("org.mortbay.jetty.servlet.Default.resourceBase", filesRoot.getPath());
    initParams.put("org.mortbay.jetty.servlet.Default.dirAllowed", "false");
    mockServiceContext.setInitParams(initParams);
    mockServiceContext.addServlet(DefaultServlet.class, FILES_URL_MATCH);
    mockServiceContext.setAttribute(Notifier.KEY, notifier);
    MimeTypes mimeTypes = new MimeTypes();
    mimeTypes.addMimeMapping("json", "application/json");
    mimeTypes.addMimeMapping("html", "text/html");
    mimeTypes.addMimeMapping("xml", "application/xml");
    mimeTypes.addMimeMapping("txt", "text/plain");
    mockServiceContext.setMimeTypes(mimeTypes);
    mockServiceContext.setWelcomeFiles(new String[] {"index.json", "index.html", "index.xml",
        "index.txt"});
    mockServiceContext.addFilter(ContentTypeSettingFilter.class, FILES_URL_MATCH, Handler.FORWARD);
    mockServiceContext.addFilter(TrailingSlashFilter.class, FILES_URL_MATCH, Handler.ALL);
    return mockServiceContext;
  }

  private void addMockServiceContext(final StubRequestHandler stubRequestHandler,
      final FileSource fileSource, final Notifier notifier) {
    Context mockServiceContext = getMockServiceContext(fileSource, notifier);
    mockServiceContext.setAttribute(StubRequestHandler.class.getName(), stubRequestHandler);
    ServletHolder servletHolder =
        mockServiceContext.addServlet(Jetty6HandlerDispatchingServlet.class, "/");
    servletHolder.setInitParameter(RequestHandler.HANDLER_CLASS_KEY,
        StubRequestHandler.class.getName());
    servletHolder.setInitParameter(SHOULD_FORWARD_TO_FILES_CONTEXT, "true");
    mockServiceContext.setConnectorNames(new String[] {"httpConnector"});
    addContextToCollection(mockServiceContext);
  }

  private void addAdminContext(final AdminRequestHandler adminRequestHandler,
      final Notifier notifier) {
    Context adminContext = new Context(jettyServer, ADMIN_CONTEXT_ROOT);
    ServletHolder servletHolder =
        adminContext.addServlet(Jetty6HandlerDispatchingServlet.class, "/");
    servletHolder.setInitParameter(RequestHandler.HANDLER_CLASS_KEY,
        AdminRequestHandler.class.getName());
    adminContext.setAttribute(AdminRequestHandler.class.getName(), adminRequestHandler);
    adminContext.setAttribute(Notifier.KEY, notifier);
    jettyServer.addHandler(adminContext);
  }

}
