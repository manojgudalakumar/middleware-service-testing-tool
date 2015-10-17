package com.github.tomakehurst.wiremock.core;

import static com.google.common.collect.Lists.transform;
import static com.google.common.collect.Maps.newLinkedHashMap;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;

import java.util.List;
import java.util.Map;

import com.github.tomakehurst.wiremock.common.ClasspathFileSource;
import com.github.tomakehurst.wiremock.common.FileSource;
import com.github.tomakehurst.wiremock.common.HttpsSettings;
import com.github.tomakehurst.wiremock.common.JettySettings;
import com.github.tomakehurst.wiremock.common.Notifier;
import com.github.tomakehurst.wiremock.common.ProxySettings;
import com.github.tomakehurst.wiremock.common.SingleRootFileSource;
import com.github.tomakehurst.wiremock.common.Slf4jNotifier;
import com.github.tomakehurst.wiremock.extension.Extension;
import com.github.tomakehurst.wiremock.extension.ExtensionLoader;
import com.github.tomakehurst.wiremock.http.CaseInsensitiveKey;
import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.collect.Maps;
import com.google.common.io.Resources;

public class WireMockConfiguration implements Options {

  private int portNumber = DEFAULT_PORT;
  private final int soapPortNumber = DEFAULT_SOAP_PORT;
  private String bindAddress = DEFAULT_BIND_ADDRESS;

  private int containerThreads = DEFAULT_CONTAINER_THREADS;

  private int httpsPort = -1;
  private String keyStorePath = Resources.getResource("keystore").toString();
  private String keyStorePassword = "password";
  private String trustStorePath;
  private String trustStorePassword = "password";
  private boolean needClientAuth;

  private boolean browserProxyingEnabled = false;
  private ProxySettings proxySettings = ProxySettings.NO_PROXY;
  private FileSource filesRoot = new SingleRootFileSource("src/test/resources");
  private Notifier notifier = new Slf4jNotifier(false);
  private boolean requestJournalDisabled = false;
  private Optional<Integer> maxRequestJournalEntries = Optional.absent();
  private List<CaseInsensitiveKey> matchingHeaders = emptyList();

  private boolean preserveHostHeader;
  private String proxyHostHeader;
  private Integer jettyAcceptors;
  private Integer jettyAcceptQueueSize;
  private Integer jettyHeaderBufferSize;
  private final boolean addToProject = DEFAULT_ADD_TO_PROJECT;
  private final Map<String, Extension> extensions = newLinkedHashMap();

  public static WireMockConfiguration wireMockConfig() {
    return new WireMockConfiguration();
  }

  public WireMockConfiguration port(final int portNumber) {
    this.portNumber = portNumber;
    return this;
  }

  public WireMockConfiguration dynamicPort() {
    this.portNumber = DYNAMIC_PORT;
    return this;
  }

  public WireMockConfiguration httpsPort(final Integer httpsPort) {
    this.httpsPort = httpsPort;
    return this;
  }

  public WireMockConfiguration dynamicHttpsPort() {
    this.httpsPort = DYNAMIC_PORT;
    return this;
  }

  public WireMockConfiguration containerThreads(final Integer containerThreads) {
    this.containerThreads = containerThreads;
    return this;
  }

  public WireMockConfiguration jettyAcceptors(final Integer jettyAcceptors) {
    this.jettyAcceptors = jettyAcceptors;
    return this;
  }

  public WireMockConfiguration jettyAcceptQueueSize(final Integer jettyAcceptQueueSize) {
    this.jettyAcceptQueueSize = jettyAcceptQueueSize;
    return this;
  }

  public WireMockConfiguration jettyHeaderBufferSize(final Integer jettyHeaderBufferSize) {
    this.jettyHeaderBufferSize = jettyHeaderBufferSize;
    return this;
  }

  public WireMockConfiguration keystorePath(final String path) {
    this.keyStorePath = path;
    return this;
  }

  public WireMockConfiguration keystorePassword(final String keyStorePassword) {
    this.keyStorePassword = keyStorePassword;
    return this;
  }

  public WireMockConfiguration trustStorePath(final String truststorePath) {
    this.trustStorePath = truststorePath;
    return this;
  }

  public WireMockConfiguration trustStorePassword(final String trustStorePassword) {
    this.trustStorePassword = trustStorePassword;
    return this;
  }

  public WireMockConfiguration needClientAuth(final boolean needClientAuth) {
    this.needClientAuth = needClientAuth;
    return this;
  }

  public WireMockConfiguration enableBrowserProxying(final boolean enabled) {
    this.browserProxyingEnabled = enabled;
    return this;
  }

  public WireMockConfiguration proxyVia(final String host, final int port) {
    this.proxySettings = new ProxySettings(host, port);
    return this;
  }

  public WireMockConfiguration proxyVia(final ProxySettings proxySettings) {
    this.proxySettings = proxySettings;
    return this;
  }

  public WireMockConfiguration withRootDirectory(final String path) {
    this.filesRoot = new SingleRootFileSource(path);
    return this;
  }

  public WireMockConfiguration usingFilesUnderDirectory(final String path) {
    return withRootDirectory(path);
  }

  public WireMockConfiguration usingFilesUnderClasspath(final String path) {
    this.filesRoot = new ClasspathFileSource(path);
    return this;
  }

  public WireMockConfiguration fileSource(final FileSource fileSource) {
    this.filesRoot = fileSource;
    return this;
  }

  public WireMockConfiguration notifier(final Notifier notifier) {
    this.notifier = notifier;
    return this;
  }

  public WireMockConfiguration bindAddress(final String bindAddress) {
    this.bindAddress = bindAddress;
    return this;
  }

  public WireMockConfiguration disableRequestJournal() {
    requestJournalDisabled = true;
    return this;
  }

  public WireMockConfiguration maxRequestJournalEntries(
      final Optional<Integer> maxRequestJournalEntries) {
    this.maxRequestJournalEntries = maxRequestJournalEntries;
    return this;
  }

  public WireMockConfiguration recordRequestHeadersForMatching(final List<String> headers) {
    this.matchingHeaders = transform(headers, CaseInsensitiveKey.TO_CASE_INSENSITIVE_KEYS);
    return this;
  }

  public WireMockConfiguration preserveHostHeader(final boolean preserveHostHeader) {
    this.preserveHostHeader = preserveHostHeader;
    return this;
  }

  public WireMockConfiguration proxyHostHeader(final String hostHeaderValue) {
    this.proxyHostHeader = hostHeaderValue;
    return this;
  }

  public WireMockConfiguration extensions(final String... classNames) {
    extensions.putAll(ExtensionLoader.load(classNames));
    return this;
  }

  public WireMockConfiguration extensions(final Extension... extensionInstances) {
    extensions.putAll(ExtensionLoader.asMap(asList(extensionInstances)));
    return this;
  }

  public WireMockConfiguration extensions(final Class<? extends Extension>... classes) {
    extensions.putAll(ExtensionLoader.load(classes));
    return this;
  }

  public int portNumber() {
    return portNumber;
  }

  public int containerThreads() {
    return containerThreads;
  }

  public HttpsSettings httpsSettings() {
    return new HttpsSettings.Builder().port(httpsPort).keyStorePath(keyStorePath)
        .keyStorePassword(keyStorePassword).trustStorePath(trustStorePath)
        .trustStorePassword(trustStorePassword).needClientAuth(needClientAuth).build();
  }

  public JettySettings jettySettings() {
    return JettySettings.Builder.aJettySettings().withAcceptors(jettyAcceptors)
        .withAcceptQueueSize(jettyAcceptQueueSize).withRequestHeaderSize(jettyHeaderBufferSize)
        .build();
  }

  public boolean browserProxyingEnabled() {
    return browserProxyingEnabled;
  }

  public ProxySettings proxyVia() {
    return proxySettings;
  }

  public FileSource filesRoot() {
    return filesRoot;
  }

  public Notifier notifier() {
    return notifier;
  }

  public boolean requestJournalDisabled() {
    return requestJournalDisabled;
  }

  public Optional<Integer> maxRequestJournalEntries() {
    return maxRequestJournalEntries;
  }

  public String bindAddress() {
    return bindAddress;
  }

  public List<CaseInsensitiveKey> matchingHeaders() {
    return matchingHeaders;
  }

  public boolean shouldPreserveHostHeader() {
    return preserveHostHeader;
  }

  public String proxyHostHeader() {
    return proxyHostHeader;
  }

  @SuppressWarnings("unchecked")
  public <T extends Extension> Map<String, T> extensionsOfType(final Class<T> extensionType) {
    return (Map<String, T>) Maps.filterEntries(extensions,
        new Predicate<Map.Entry<String, Extension>>() {
          public boolean apply(final Map.Entry<String, Extension> input) {
            return extensionType.isAssignableFrom(input.getValue().getClass());
          }
        });
  }

  public int soapPortNumber() {
    return soapPortNumber;
  }

  public boolean addToProjectXml() {
    return addToProject;
  }
}
