package com.mstt.qa.servicevirtualization.uicomponent.utils;

import java.io.IOException;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

import com.mstt.qa.servicevirtualization.uicomponent.utils.VirtualizationUiOptions;

public class PopulateWireMockProperties {
  private final VirtualizationUiOptions options;
  private final String RECORD_MAPPINGS = "record-mappings";
  private final String MATCH_HEADERS = "match-headers";
  private final String PROXY_ALL = "proxy-all";
  private final String PRESERVE_HOST_HEADER = "preserve-host-header";
  private final String PROXY_VIA = "proxy-via";
  private final String PORT = "port";
  private static final String SOAP_PORT = "soapPort";
  private final String BIND_ADDRESS = "bind-address";
  private final String HTTPS_PORT = "https-port";
  private final String HTTPS_KEYSTORE = "https-keystore";
  private final String HTTPS_KEYSTORE_PASSWORD = "keystore-password";
  private final String HTTPS_TRUSTSTORE = "https-truststore";
  private final String HTTPS_TRUSTSTORE_PASSWORD = "truststore-password";
  private final String REQUIRE_CLIENT_CERT = "https-require-client-cert";
  private final String VERBOSE = "verbose";
  private final String ENABLE_BROWSER_PROXYING = "enable-browser-proxying";
  private final String DISABLE_REQUEST_JOURNAL = "no-request-journal";
  private final String EXTENSIONS = "extensions";
  private final String MAX_ENTRIES_REQUEST_JOURNAL = "max-request-journal-entries";
  private final String JETTY_ACCEPTOR_THREAD_COUNT = "jetty-acceptor-threads";
  private final String JETTY_ACCEPT_QUEUE_SIZE = "jetty-accept-queue-size";
  private final String JETTY_HEADER_BUFFER_SIZE = "jetty-header-buffer-size";
  private final String ROOT_DIR = "root-dir";
  private final String CONTAINER_THREADS = "container-threads";
  private final String propFileName = "wiremockprops.properties";
  private final String RUN_BACKGROUND = "run-background";

  public PopulateWireMockProperties() {
    options = new VirtualizationUiOptions();
  }

  public VirtualizationUiOptions getOptions() throws IOException, ConfigurationException {
    Configuration prop = new PropertiesConfiguration(propFileName);
    options.setBindAddress(prop.getString(BIND_ADDRESS));
    options.setContainerThreads(prop.getInteger(CONTAINER_THREADS, null));
    options.setDisableRequestJournal(prop.getBoolean(DISABLE_REQUEST_JOURNAL, false));
    options.setEnableBrowserProxying(prop.getBoolean(ENABLE_BROWSER_PROXYING, null));
    options.setExtensions(prop.getString(EXTENSIONS));
    options.setHttpsKeyStore(prop.getString(HTTPS_KEYSTORE));
    options.setHttpsKeyStorePassowrd(prop.getString(HTTPS_KEYSTORE_PASSWORD));
    options.setHttpsPort(prop.getInteger(HTTPS_PORT, null));
    options.setHttpsRequireClientCertificate(prop.getBoolean(REQUIRE_CLIENT_CERT, null));
    options.setHttpsTrustStore(prop.getString(HTTPS_TRUSTSTORE));
    options.setHttpsTrustStorePassowrd(prop.getString(HTTPS_TRUSTSTORE_PASSWORD));
    options.setJettyAcceptorThreads(prop.getInteger(JETTY_ACCEPTOR_THREAD_COUNT, null));
    options.setJettyAcceptQueueSize(prop.getInteger(JETTY_ACCEPT_QUEUE_SIZE, null));
    options.setJettyHeaderBufferSize(prop.getInteger(JETTY_HEADER_BUFFER_SIZE, null));
    options.setMatchHeaders(prop.getString(MATCH_HEADERS));
    options.setMaxEntriesRequestJournal(prop.getInteger(MAX_ENTRIES_REQUEST_JOURNAL, null));
    options.setPort(prop.getInteger(PORT, null));
    options.setSoapPort(prop.getInteger(SOAP_PORT, null));
    options.setPreserveHostHeader(prop.getBoolean(PRESERVE_HOST_HEADER, null));
    options.setProxyAll(prop.getString(PROXY_ALL));
    options.setProxyVia(prop.getString(PROXY_VIA));
    options.setRecordMapping(prop.getBoolean(RECORD_MAPPINGS, null));
    options.setRootDir(prop.getString(ROOT_DIR));
    options.setVerbose(prop.getBoolean(VERBOSE, null));
    options.setRunbackgrond(prop.getBoolean(RUN_BACKGROUND));
    return options;
  }
}
