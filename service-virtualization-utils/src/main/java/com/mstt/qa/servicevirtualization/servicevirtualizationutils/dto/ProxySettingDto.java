package com.mstt.qa.servicevirtualization.servicevirtualizationutils.dto;

import com.mstt.qa.servicevirtualization.ProxySettings;

public class ProxySettingDto {
  public String getProxyHostName() {
    return proxyHostName;
  }

  public void setProxyHostName(final String proxyHostName) {
    this.proxyHostName = proxyHostName;
  }

  public int getProxyPort() {
    return proxyPort;
  }

  public void setProxyPort(final int proxyPort) {
    this.proxyPort = proxyPort;
  }

  public String getProxyUserName() {
    return proxyUserName;
  }

  public void setProxyUserName(final String proxyUserName) {
    this.proxyUserName = proxyUserName;
  }

  public String getProxyPassword() {
    return proxyPassword;
  }

  public void setProxyPassword(final String proxyPassword) {
    this.proxyPassword = proxyPassword;
  }

  private String proxyHostName;
  private int proxyPort;
  private String proxyUserName;
  private String proxyPassword;

  public ProxySettingDto(final String proxyHostName, final int proxyPort,
      final String proxyUserName, final String proxyPassword) {
    this.proxyHostName = proxyHostName;
    this.proxyPort = proxyPort;
    this.proxyUserName = proxyUserName;
    this.proxyPassword = proxyPassword;
  }

  public static ProxySettingDto getProxySetingFromJaxb(final ProxySettings proxySettings) {
    ProxySettingDto prxyDto =
        new ProxySettingDto(proxySettings.getProxyHostname(), proxySettings.getProxyPort(),
            proxySettings.getProxyUserName(), proxySettings.getProxyPassword());
    return prxyDto;
  }

  public static ProxySettings getProxySeting(final ProxySettingDto proxySettings) {
    ProxySettings prxy = new ProxySettings();
    prxy.setProxyHostname(proxySettings.getProxyHostName());
    prxy.setProxyPort(proxySettings.getProxyPort());
    prxy.setProxyUserName(proxySettings.getProxyUserName());
    prxy.setProxyPassword(proxySettings.getProxyPassword());
    return prxy;
  }

}
