package com.github.tomakehurst.wiremock.core;

import java.util.List;
import java.util.Map;

import com.github.tomakehurst.wiremock.common.FileSource;
import com.github.tomakehurst.wiremock.common.HttpsSettings;
import com.github.tomakehurst.wiremock.common.JettySettings;
import com.github.tomakehurst.wiremock.common.Notifier;
import com.github.tomakehurst.wiremock.common.ProxySettings;
import com.github.tomakehurst.wiremock.extension.Extension;
import com.github.tomakehurst.wiremock.http.CaseInsensitiveKey;
import com.google.common.base.Optional;

public interface Options {

  public static final int DEFAULT_PORT = 8080;
  public static final int DEFAULT_SOAP_PORT = 8081;
  public static final int DYNAMIC_PORT = 0;
  public static final int DEFAULT_CONTAINER_THREADS = 200;
  public static final String DEFAULT_BIND_ADDRESS = "0.0.0.0";
  public static final boolean DEFAULT_ADD_TO_PROJECT = false;

  int portNumber();

  int soapPortNumber();

  HttpsSettings httpsSettings();

  JettySettings jettySettings();

  int containerThreads();

  boolean browserProxyingEnabled();

  ProxySettings proxyVia();

  FileSource filesRoot();

  Notifier notifier();

  boolean requestJournalDisabled();

  Optional<Integer> maxRequestJournalEntries();

  public String bindAddress();

  List<CaseInsensitiveKey> matchingHeaders();

  public boolean shouldPreserveHostHeader();

  public boolean addToProjectXml();

  String proxyHostHeader();

  <T extends Extension> Map<String, T> extensionsOfType(Class<T> extensionType);
}
