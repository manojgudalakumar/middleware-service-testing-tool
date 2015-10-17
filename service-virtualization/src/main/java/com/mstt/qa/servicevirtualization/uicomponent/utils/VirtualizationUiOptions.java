package com.mstt.qa.servicevirtualization.uicomponent.utils;

import static com.github.tomakehurst.wiremock.common.ProxySettings.NO_PROXY;
import static com.github.tomakehurst.wiremock.http.CaseInsensitiveKey.TO_CASE_INSENSITIVE_KEYS;

import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.github.tomakehurst.wiremock.common.ConsoleNotifier;
import com.github.tomakehurst.wiremock.common.FileSource;
import com.github.tomakehurst.wiremock.common.HttpsSettings;
import com.github.tomakehurst.wiremock.common.JettySettings;
import com.github.tomakehurst.wiremock.common.Notifier;
import com.github.tomakehurst.wiremock.common.ProxySettings;
import com.github.tomakehurst.wiremock.common.SingleRootFileSource;
import com.github.tomakehurst.wiremock.core.Options;
import com.github.tomakehurst.wiremock.extension.Extension;
import com.github.tomakehurst.wiremock.extension.ExtensionLoader;
import com.github.tomakehurst.wiremock.http.CaseInsensitiveKey;
import com.google.common.base.Optional;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Iterators;
import com.google.common.collect.UnmodifiableIterator;

public class VirtualizationUiOptions implements Options {
  private final String RECORD_MAPPINGS = "record-mappings";
  private final String MATCH_HEADERS = "match-headers";
  private final String PROXY_ALL = "proxy-all";
  private final String PRESERVE_HOST_HEADER = "preserve-host-header";
  private final String PROXY_VIA = "proxy-via";
  private final String PORT = "port";
  private final String HTTPS_PORT = "https-port";
  private final String HTTPS_KEYSTORE = "https-keystore";
  private final String VERBOSE = "verbose";
  private final String ENABLE_BROWSER_PROXYING = "enable-browser-proxying";
  private final String DISABLE_REQUEST_JOURNAL = "no-request-journal";
  private final String JETTY_ACCEPTOR_THREAD_COUNT = "jetty-acceptor-threads";
  private final String JETTY_ACCEPT_QUEUE_SIZE = "jetty-accept-queue-size";
  private final String JETTY_HEADER_BUFFER_SIZE = "jetty-header-buffer-size";
  private boolean runbackgrond;
  private Boolean recordMapping;
  private String matchHeaders;
  private String proxyAll;
  private Boolean preserveHostHeader;
  private String proxyVia;
  private Integer port;
  private Integer soapPort;
  private String bindAddress;
  private Integer httpsPort;
  private String httpsKeyStore;
  private String httpsKeyStorePassowrd;
  private String httpsTrustStore;
  private String httpsTrustStorePassowrd;
  private Boolean httpsRequireClientCertificate;
  private Boolean verbose;
  private Boolean enableBrowserProxying;
  private Boolean disableRequestJournal;
  private String extensions;
  private Integer maxEntriesRequestJournal;
  private Integer jettyAcceptorThreads;
  private Integer jettyAcceptQueueSize;
  private Integer jettyHeaderBufferSize;
  private String rootDir;
  private Integer containerThreads;
  private final boolean addToProject = true;

  @Override
  public int soapPortNumber() {
    if (hasValue(getSoapPort())) {
      return getSoapPort();
    }

    return DEFAULT_SOAP_PORT;
  }

  public boolean verboseLoggingEnabled() {
    return hasValue(getVerbose());
  }

  public boolean recordMappingsEnabled() {
    return hasValue(getRecordMapping());
  }

  @Override
  public List<CaseInsensitiveKey> matchingHeaders() {
    if (hasValue(getMatchHeaders())) {
      String headerSpec = getMatchHeaders();
      UnmodifiableIterator<String> headerKeys = Iterators.forArray(headerSpec.split(","));
      return ImmutableList.copyOf(Iterators.transform(headerKeys, TO_CASE_INSENSITIVE_KEYS));
    }

    return Collections.emptyList();
  }

  private boolean hasValue(final Object obj) {
    if (obj == null)
      return false;
    return true;
  }

  @Override
  public int portNumber() {
    if (hasValue(getPort())) {
      return getPort();
    }

    return DEFAULT_PORT;
  }

  @Override
  public String bindAddress() {
    if (hasValue(getBindAddress())) {
      return getBindAddress();
    }

    return DEFAULT_BIND_ADDRESS;
  }

  @Override
  public HttpsSettings httpsSettings() {
    return new HttpsSettings.Builder().port(httpsPortNumber()).keyStorePath(getHttpsKeyStore())
        .keyStorePassword(getHttpsKeyStorePassowrd()).trustStorePath(getHttpsTrustStore())
        .trustStorePassword(getHttpsTrustStorePassowrd())
        .needClientAuth(getHttpsRequireClientCertificate()).build();
  }

  @Override
  public JettySettings jettySettings() {

    JettySettings.Builder builder = JettySettings.Builder.aJettySettings();

    if (hasValue(getJettyAcceptorThreads())) {
      builder = builder.withAcceptors(getJettyAcceptorThreads());
    }

    if (hasValue(getJettyAcceptQueueSize())) {
      builder = builder.withAcceptQueueSize(getJettyAcceptQueueSize());
    }

    if (hasValue(getJettyHeaderBufferSize())) {
      builder = builder.withRequestHeaderSize(getJettyHeaderBufferSize());
    }

    return builder.build();
  }

  private int httpsPortNumber() {
    return hasValue(getHttpsPort()) ? getHttpsPort() : -1;
  }

  public Boolean specifiesProxyUrl() {
    return hasValue(getProxyAll());
  }

  public String proxyUrl() {
    return getProxyAll();
  }

  @Override
  public boolean shouldPreserveHostHeader() {
    return hasValue(getPreserveHostHeader());
  }

  @Override
  public String proxyHostHeader() {
    return hasValue(getProxyAll()) ? URI.create(getProxyAll()).getHost() : null;
  }

  @Override
  public <T extends Extension> Map<String, T> extensionsOfType(final Class<T> extensionType) {
    if (hasValue(getExtensions())) {
      String classNames = getExtensions();
      return ExtensionLoader.loadExtension(classNames.split(","));
    }

    return Collections.emptyMap();
  }

  @Override
  public boolean browserProxyingEnabled() {
    return getEnableBrowserProxying();
  }

  @Override
  public ProxySettings proxyVia() {
    if (hasValue(getProxyVia())) {
      String proxyVia = getProxyVia();
      return ProxySettings.fromString(proxyVia);
    }

    return NO_PROXY;
  }

  @Override
  public FileSource filesRoot() {
    return new SingleRootFileSource(hasValue(getRootDir()) ? getRootDir()
        : System.getProperty("user.dir"));
  }

  @Override
  public Notifier notifier() {
    return new ConsoleNotifier(verboseLoggingEnabled());
  }

  @Override
  public boolean requestJournalDisabled() {
    return getDisableRequestJournal();
  }

  private Boolean specifiesMaxRequestJournalEntries() {
    return hasValue(getMaxEntriesRequestJournal());
  }

  @Override
  public Optional<Integer> maxRequestJournalEntries() {
    if (specifiesMaxRequestJournalEntries()) {
      return Optional.of(getMaxEntriesRequestJournal());
    }
    return Optional.absent();
  }

  @Override
  public int containerThreads() {
    if (hasValue(getContainerThreads())) {
      return getContainerThreads();
    }

    return DEFAULT_CONTAINER_THREADS;
  }

  @Override
  public String toString() {
    ImmutableMap.Builder<String, Object> builder = ImmutableMap.builder();
    builder.put(PORT, portNumber());

    if (httpsSettings().enabled()) {
      builder.put(HTTPS_PORT, nullToString(httpsSettings().port())).put(HTTPS_KEYSTORE,
          nullToString(httpsSettings().keyStorePath()));
    }

    if (!(proxyVia() == NO_PROXY)) {
      builder.put(PROXY_VIA, proxyVia());
    }
    if (proxyUrl() != null) {
      builder.put(PROXY_ALL, nullToString(proxyUrl())).put(PRESERVE_HOST_HEADER,
          shouldPreserveHostHeader());
    }

    builder.put(ENABLE_BROWSER_PROXYING, browserProxyingEnabled());

    if (recordMappingsEnabled()) {
      builder.put(RECORD_MAPPINGS, recordMappingsEnabled()).put(MATCH_HEADERS, matchingHeaders());
    }

    builder.put(DISABLE_REQUEST_JOURNAL, requestJournalDisabled()).put(VERBOSE,
        verboseLoggingEnabled());

    if (jettySettings().getAcceptQueueSize().isPresent()) {
      builder.put(JETTY_ACCEPT_QUEUE_SIZE, jettySettings().getAcceptQueueSize().get());
    }

    if (jettySettings().getAcceptors().isPresent()) {
      builder.put(JETTY_ACCEPTOR_THREAD_COUNT, jettySettings().getAcceptors().get());
    }

    if (jettySettings().getRequestHeaderSize().isPresent()) {
      builder.put(JETTY_HEADER_BUFFER_SIZE, jettySettings().getRequestHeaderSize().get());
    }

    StringBuilder sb = new StringBuilder();
    for (Map.Entry<String, Object> param : builder.build().entrySet()) {
      int paddingLength = 29 - param.getKey().length();
      sb.append(param.getKey()).append(":").append(Strings.repeat(" ", paddingLength))
          .append(nullToString(param.getValue())).append("\n");
    }

    return sb.toString();
  }

  private String nullToString(final Object value) {
    if (value == null) {
      return "(null)";
    }

    return value.toString();
  }

  public Boolean getRecordMapping() {
    return recordMapping;
  }

  public void setRecordMapping(final Boolean recordMapping) {
    this.recordMapping = recordMapping;
  }

  public String getMatchHeaders() {
    return matchHeaders;
  }

  public void setMatchHeaders(final String matchHeaders) {
    this.matchHeaders = matchHeaders;
  }

  public String getProxyAll() {
    return proxyAll;
  }

  public void setProxyAll(final String proxyAll) {
    this.proxyAll = proxyAll;
  }

  public Boolean getPreserveHostHeader() {
    return preserveHostHeader;
  }

  public void setPreserveHostHeader(final Boolean preserveHostHeader) {
    this.preserveHostHeader = preserveHostHeader;
  }

  public String getProxyVia() {
    return proxyVia;
  }

  public void setProxyVia(final String proxyVia) {
    this.proxyVia = proxyVia;
  }

  public Integer getPort() {
    return port;
  }

  public void setPort(final Integer port) {
    this.port = port;
  }

  public String getBindAddress() {
    return bindAddress;
  }

  public void setBindAddress(final String bindAddress) {
    this.bindAddress = bindAddress;
  }

  public Integer getHttpsPort() {
    return httpsPort;
  }

  public void setHttpsPort(final Integer httpsPort) {
    this.httpsPort = httpsPort;
  }

  public String getHttpsKeyStore() {
    return httpsKeyStore;
  }

  public void setHttpsKeyStore(final String httpsKeyStore) {
    this.httpsKeyStore = httpsKeyStore;
  }

  public String getHttpsKeyStorePassowrd() {
    return httpsKeyStorePassowrd;
  }

  public void setHttpsKeyStorePassowrd(final String httpsKeyStorePassowrd) {
    this.httpsKeyStorePassowrd = httpsKeyStorePassowrd;
  }

  public String getHttpsTrustStore() {
    return httpsTrustStore;
  }

  public void setHttpsTrustStore(final String httpsTrustStore) {
    this.httpsTrustStore = httpsTrustStore;
  }

  public String getHttpsTrustStorePassowrd() {
    return httpsTrustStorePassowrd;
  }

  public void setHttpsTrustStorePassowrd(final String httpsTrustStorePassowrd) {
    this.httpsTrustStorePassowrd = httpsTrustStorePassowrd;
  }

  public boolean getHttpsRequireClientCertificate() {
    return hasValue(httpsRequireClientCertificate);
  }

  public void setHttpsRequireClientCertificate(final Boolean httpsRequireClientCertificate) {
    this.httpsRequireClientCertificate = httpsRequireClientCertificate;
  }

  public Boolean getVerbose() {
    return verbose;
  }

  public void setVerbose(final Boolean verbose) {
    this.verbose = verbose;
  }

  public Boolean getEnableBrowserProxying() {
    return enableBrowserProxying;
  }

  public void setEnableBrowserProxying(final Boolean enableBrowserProxying) {
    this.enableBrowserProxying = enableBrowserProxying;
  }

  public Boolean getDisableRequestJournal() {
    return disableRequestJournal;
  }

  public void setDisableRequestJournal(final Boolean disableRequestJournal) {
    this.disableRequestJournal = disableRequestJournal;
  }

  public String getExtensions() {
    return extensions;
  }

  public void setExtensions(final String extensions) {
    this.extensions = extensions;
  }

  public Integer getMaxEntriesRequestJournal() {
    return maxEntriesRequestJournal;
  }

  public void setMaxEntriesRequestJournal(final Integer maxEntriesRequestJournal) {
    this.maxEntriesRequestJournal = maxEntriesRequestJournal;
  }

  public Integer getJettyAcceptorThreads() {
    return jettyAcceptorThreads;
  }

  public void setJettyAcceptorThreads(final Integer jettyAcceptorThreads) {
    this.jettyAcceptorThreads = jettyAcceptorThreads;
  }

  public Integer getJettyAcceptQueueSize() {
    return jettyAcceptQueueSize;
  }

  public void setJettyAcceptQueueSize(final Integer jettyAcceptQueueSize) {
    this.jettyAcceptQueueSize = jettyAcceptQueueSize;
  }

  public Integer getJettyHeaderBufferSize() {
    return jettyHeaderBufferSize;
  }

  public void setJettyHeaderBufferSize(final Integer jettyHeaderBufferSize) {
    this.jettyHeaderBufferSize = jettyHeaderBufferSize;
  }

  public String getRootDir() {
    return rootDir;
  }

  public void setRootDir(final String rootDir) {
    this.rootDir = rootDir;
  }

  public int getContainerThreads() {
    return hasValue(containerThreads) ? containerThreads() : DEFAULT_CONTAINER_THREADS;
  }

  public void setContainerThreads(final Integer containerThreads) {
    this.containerThreads = containerThreads;
  }

  public Integer getSoapPort() {
    return soapPort;
  }

  public void setSoapPort(final Integer soapPort) {
    this.soapPort = soapPort;
  }

  public boolean isAddToProject() {
    return addToProject;
  }

  @Override
  public boolean addToProjectXml() {
    return addToProject;
  }

  public boolean isRunbackgrond() {
    return runbackgrond;
  }

  public void setRunbackgrond(final boolean runbackgrond) {
    this.runbackgrond = runbackgrond;
  }

}
