package com.mstt.qa.servicevirtualization.soapservice.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.eviware.soapui.SoapUI;
import com.eviware.soapui.impl.WsdlInterfaceFactory;
import com.eviware.soapui.impl.wsdl.WsdlInterface;
import com.eviware.soapui.impl.wsdl.WsdlOperation;
import com.eviware.soapui.impl.wsdl.WsdlProject;
import com.eviware.soapui.model.iface.Operation;
import com.eviware.soapui.settings.ProxySettings;
import com.eviware.soapui.settings.WsdlSettings;
import com.github.tomakehurst.wiremock.core.Options;

public class SoapUIUtil {

  private static Map<String, WsdlInterface> intrMap = new HashMap<String, WsdlInterface>();

  private static void setSoapUISettings() {
    SoapUI.getSettings().setBoolean(WsdlSettings.XML_GENERATION_TYPE_EXAMPLE_VALUE, false);
    SoapUI.getSettings().setBoolean(WsdlSettings.XML_GENERATION_SKIP_COMMENTS, false);
  }

  private static void setSoapUISettingsWithProxySettings(final String proxyHostString,
      final int proxyPort) {
    System.out.println("the host is" + proxyHostString);
    System.out.println("the host is" + proxyPort);
    SoapUI.getSettings().setBoolean(WsdlSettings.XML_GENERATION_TYPE_EXAMPLE_VALUE, false);
    SoapUI.getSettings().setBoolean(WsdlSettings.XML_GENERATION_SKIP_COMMENTS, false);
    SoapUI.getSettings().setBoolean(ProxySettings.ENABLE_PROXY, true);
    SoapUI.getSettings().setString(ProxySettings.HOST, proxyHostString);
    SoapUI.getSettings().setString(ProxySettings.PORT, "" + proxyPort);
  }

  private static void setSoapUISettingsWithProxySettingsWithUserNameAndPassword(
      final String proxyHostString, final String proxyPort, final String userName,
      final String password) {
    SoapUI.getSettings().setBoolean(WsdlSettings.XML_GENERATION_TYPE_EXAMPLE_VALUE, false);
    SoapUI.getSettings().setBoolean(WsdlSettings.XML_GENERATION_SKIP_COMMENTS, false);
    SoapUI.getSettings().setBoolean(ProxySettings.ENABLE_PROXY, true);
    SoapUI.getSettings().setString(ProxySettings.HOST, proxyHostString);
    SoapUI.getSettings().setString(ProxySettings.PORT, proxyPort);
    SoapUI.getSettings().setString(ProxySettings.USERNAME, userName);
    SoapUI.getSettings().setString(ProxySettings.PASSWORD, password);
  }

  private static WsdlInterface getWsdlInterface(final String wsdlUrl, final Options options) {
    System.out.println(options.browserProxyingEnabled());
    if (options.browserProxyingEnabled()) {
      setSoapUISettingsWithProxySettings(options.proxyVia().host(), +options.proxyVia().port());
    } else {
      setSoapUISettings();
    }
    WsdlInterface intr = intrMap.get(wsdlUrl);
    if (intr == null) {
      try {
        WsdlProject soapUIProject = new WsdlProject();
        WsdlInterface[] intrs =
            WsdlInterfaceFactory.importWsdl(soapUIProject, wsdlUrl.replaceAll(" ", "%20"), true);

        intr = intrs[0];
        intrMap.put(wsdlUrl, intr);
      } catch (Exception e) {
        e.printStackTrace();
        throw new MockException("Unable to import WSDL from " + wsdlUrl);
      }
    }
    return intr;
  }

  public static List<String> getOperationName(final String wsdlUrl, final Options options) {

    WsdlInterface intr = getWsdlInterface(wsdlUrl, options);
    List<String> operationList = new ArrayList<String>();
    for (Operation operation : intr.getOperationList()) {
      WsdlOperation op = (WsdlOperation) operation;
      operationList.add(op.getName());
    }
    return operationList;
  }

  public static String getServiceRequestString(final String wsdlUrl, final String operationName,
      final Options options) {
    WsdlInterface wsdlInterface = getWsdlInterface(wsdlUrl, options);
    for (Operation operation : wsdlInterface.getOperationList()) {
      WsdlOperation op = (WsdlOperation) operation;
      if (operationName.equalsIgnoreCase(op.getName())) {
        return op.createRequest(true);
      }
    }
    return null;
  }

  public static String getServiceResponseString(final String wsdlUrl, final String operationName,
      final Options options) {
    WsdlInterface wsdlInterface = getWsdlInterface(wsdlUrl, options);

    for (Operation operation : wsdlInterface.getOperationList()) {
      WsdlOperation op = (WsdlOperation) operation;
      if (operationName.equalsIgnoreCase(op.getName())) {
        return op.createResponse(true);
      }
    }
    return null;
  }

  public static List<String> getOperationName(final WsdlInterface intr) {
    List<String> operationList = new ArrayList<String>();
    for (Operation operation : intr.getOperationList()) {
      WsdlOperation op = (WsdlOperation) operation;
      operationList.add(op.getName());
    }
    return operationList;
  }

  public static String[] getEndPoint(final String wsdlUrl, final Options options) {
    WsdlInterface wsdlInterface = getWsdlInterface(wsdlUrl, options);
    return wsdlInterface.getEndpoints();
  }

}
