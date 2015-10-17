package com.prokarma.qa.servicevirtualization.uicomponent.test;

import static com.github.tomakehurst.wiremock.WireMockServer.FILES_ROOT;
import static com.github.tomakehurst.wiremock.WireMockServer.MAPPINGS_ROOT;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.configuration.ConfigurationException;
import org.testng.Assert;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.github.tomakehurst.wiremock.common.FileSource;
import com.mstt.qa.servicevirtualization.servicevirtualizationutils.dto.MsttRequestDefinition;
import com.mstt.qa.servicevirtualization.servicevirtualizationutils.dto.MsttResponseDefinition;
import com.mstt.qa.servicevirtualization.servicevirtualizationutils.dto.PatternMatcherDto;
import com.mstt.qa.servicevirtualization.uicomponent.utils.MsttStubHandler;
import com.mstt.qa.servicevirtualization.uicomponent.utils.PopulateWireMockProperties;
import com.mstt.qa.servicevirtualization.uicomponent.utils.VirtualizationUiOptions;

public class TestWireMockStub {
  private static final String REQUEST_METHOD = "GET";
  private static final int PRIORITY = 3;
  MsttRequestDefinition pkmrDefinition;
  MsttResponseDefinition pkmrs;
  private static final Integer fixedDelayinMilliSeconds = 1000;
  FileSource filesFileSource;
  FileSource mappingsFileSource;
  MsttStubHandler st;
  String mappingFilename;
  String serviceType = "SOAP";
  int status = 200;

  @BeforeSuite
  public void setUp() throws ConfigurationException, IOException {
    pkmrDefinition = new MsttRequestDefinition();
    pkmrs = new MsttResponseDefinition();
    VirtualizationUiOptions wireMockOptions = new PopulateWireMockProperties().getOptions();
    FileSource fileSource = wireMockOptions.filesRoot();
    filesFileSource = fileSource.child(FILES_ROOT);
    filesFileSource.createIfNecessary();
    mappingsFileSource = fileSource.child(MAPPINGS_ROOT);
    mappingsFileSource.createIfNecessary();
    st = new MsttStubHandler(mappingsFileSource, filesFileSource);
  }

  @Test
  public void StoreStubTest() {
    pkmrDefinition.setUrl("http://localhost:8086/IrisUpdateOperatingTrainTerminationTest?test");

    PatternMatcherDto ptrn = new PatternMatcherDto();
    ptrn.setKey("Key");
    ptrn.setPattern("isEqualJson");
    ptrn.setValue("Value");
    List<PatternMatcherDto> ptrnlst = new ArrayList<PatternMatcherDto>();
    pkmrDefinition.setBodyPatterns(ptrnlst);
    pkmrDefinition.setHeaderPatterns(ptrnlst);
    pkmrDefinition.setRequestMethod(REQUEST_METHOD);
    pkmrDefinition.setHeaderPatterns(ptrnlst);
    pkmrDefinition.setRequestBody("this is the request body");
    Map<String, String> mpa = new HashMap<String, String>();
    mpa.put("key", "value");
    pkmrDefinition.setHeaderPatterns(ptrnlst);
    pkmrs.setProxyAdditionalHeaders(mpa);
    pkmrs.setHeaders(mpa);
    pkmrs.setResponse("This is the response");
    pkmrs.setStatus(status);
    pkmrs.setFixedDelayMilliseconds(fixedDelayinMilliSeconds);
    pkmrDefinition.setPriority(PRIORITY);
    mappingFilename = st.storeStub(pkmrDefinition, pkmrs, serviceType);
  }

  @Test
  public void convertStub() {
    MsttRequestDefinition msttreq = st.readRequestDefinitionFromMappingFile(mappingFilename);
    MsttResponseDefinition pkmres = st.readResponseDefinitionFromMappingFile(mappingFilename);
    Assert.assertEquals(pkmres.getStatus(), status);
    Assert.assertEquals(pkmres.getFixedDelayMilliseconds(), fixedDelayinMilliSeconds);
    Assert.assertEquals(msttreq.getRequestMethod(), REQUEST_METHOD);

  }
}
