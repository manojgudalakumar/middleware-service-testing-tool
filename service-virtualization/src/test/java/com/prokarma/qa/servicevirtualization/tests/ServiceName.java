package com.prokarma.qa.servicevirtualization.tests;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.tomakehurst.wiremock.common.Json;
import com.github.tomakehurst.wiremock.http.HttpHeader;
import com.github.tomakehurst.wiremock.http.HttpHeaders;
import com.github.tomakehurst.wiremock.http.RequestMethod;
import com.github.tomakehurst.wiremock.http.ResponseDefinition;
import com.github.tomakehurst.wiremock.matching.RequestPattern;
import com.github.tomakehurst.wiremock.matching.ValuePattern;
import com.github.tomakehurst.wiremock.stubbing.StubMapping;
import com.mstt.qa.servicevirtualization.servicevirtualizationutils.dto.PatternMatcherDto;
import com.mstt.qa.servicevirtualization.servicevirtualizationutils.dto.MsttRequestDefinition;
import com.mstt.qa.servicevirtualization.servicevirtualizationutils.dto.MsttResponseDefinition;
import com.mstt.qa.servicevirtualization.uicomponent.utils.MsttStubHandler;

public class ServiceName {

  public static void main(final String[] args) {
    // WireMockConfiguration options = new WireMockConfiguration();
    // options.proxyVia("proxysg.swg.apps.uprr.com", 8080);
    // System.out
    // .println("the service name is "
    // + SoapUIUtil
    // .getOperationName(
    // "http://www.webservicex.net/CurrencyConvertor.asmx?WSDL",
    // options));
    StubMapping stub = new StubMapping();
    stub.setPriority(10);
    stub.setInsertionIndex(2);
    stub.setNewScenarioState("NewScenario");
    stub.setRequiredScenarioState("RequiredStateScenario");
    stub.setScenarioName("ScenarioName");
    stub.setServiceType("Soap");
    stub.setTransient(true);
    RequestPattern req = new RequestPattern();
    req.setMethod(RequestMethod.POST);
    req.setUrl("This/is/url/which");
    // req.setUrlPath("This/is/url/path");
    // req.setUrlPattern("this/url/Pattern*");
    Map<String, ValuePattern> yt = new HashMap<String, ValuePattern>();
    ValuePattern vt = ValuePattern.equalToXPath("This is the xpath matcher in headers and query");
    ValuePattern vt1 = ValuePattern.equalToXml("This is the xml");
    yt.put("Accept", vt);
    yt.put("Accept-Charset", vt1);
    req.setHeaders(yt);
    Map<String, ValuePattern> yt1 = new HashMap<String, ValuePattern>();
    ValuePattern vt11 = ValuePattern.equalToXPath("This is the xpath matcher in headers and query");
    ValuePattern vt111 = ValuePattern.equalToXml("This is the xml");
    yt1.put("paramter1", vt11);
    yt1.put("paramter2", vt111);
    req.setQueryParameters(yt1);

    List<ValuePattern> lt = new ArrayList<ValuePattern>();
    ValuePattern vt2 = ValuePattern.equalToXPath("This is the xpath in body");
    ValuePattern vt3 = ValuePattern.equalToXml("This is the xml in body");
    ValuePattern vt4 = ValuePattern.equalToXPath("This is the xpath in body");
    ValuePattern vt5 = ValuePattern.containing("This is the xpath in body");
    lt.add(vt4);
    lt.add(vt3);
    lt.add(vt2);
    lt.add(vt5);
    req.setBodyPatterns(lt);
    stub.setRequest(req);

    HttpHeader httpHeader = new HttpHeader("Accept", "test");
    HttpHeaders https = new HttpHeaders(httpHeader);
    // https.all().add(httpHeader);
    ResponseDefinition rs = new ResponseDefinition();
    rs.setAdditionalProxyRequestHeaders(https);
    rs.setHeaders(https);

    stub.setResponse(rs);
    System.out.println(Json.write(stub));
    System.out
        .println("*******************************************************************************");
    System.out
        .println("*******************************************************************************");
    System.out
        .println("*******************************************************************************");
    System.out
        .println("*******************************************************************************");
    System.out
        .println("*******************************************************************************");
    System.out
        .println("*******************************************************************************");
    ServiceName.trick();

  }

  public static void trick() {
    MsttRequestDefinition pkmrDefinition = new MsttRequestDefinition();
    pkmrDefinition
        .setUrl("http://localhost:8086/IrisUpdateOperatingTrainScheduleSuite.IrisProcessTedMessageSuite.ExtendedTerminationSuite.TedMessageInNetControlPlannedTrainExtendTerminationTest?test");
    MsttResponseDefinition pkmrs = new MsttResponseDefinition();
    MsttStubHandler st = new MsttStubHandler(null, null);
    PatternMatcherDto ptrn = new PatternMatcherDto();
    ptrn.setKey("Key");
    ptrn.setPattern("isEqualJson");
    ptrn.setValue("Value");
    List<PatternMatcherDto> ptrnlst = new ArrayList<PatternMatcherDto>();
    pkmrDefinition.setBodyPatterns(ptrnlst);
    pkmrDefinition.setHeaderPatterns(ptrnlst);
    pkmrDefinition.setRequestMethod("GET");
    pkmrDefinition.setHeaderPatterns(ptrnlst);
    pkmrDefinition.setRequestBody("this is the request body");
    Map<String, String> mpa = new HashMap<String, String>();
    mpa.put("key", "value");
    pkmrDefinition.setHeaderPatterns(ptrnlst);
    pkmrs.setProxyAdditionalHeaders(mpa);
    pkmrs.setHeaders(mpa);
    pkmrs.setResponse("This is the response");
    st.storeStub(pkmrDefinition, pkmrs, "SOAP");

  }
}
