package com.mstt.qa.servicevirtualization.servicevirtualizationutils.dto;

import static com.mstt.qa.servicevirtualization.servicevirtualizationutils.utils.CommonMethodsUtils.isNotNull;
import static org.apache.commons.collections.MapUtils.isNotEmpty;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.mstt.qa.servicevirtualization.RequestAndResponseDataType;
import com.mstt.qa.servicevirtualization.ResponsePattern;
import com.mstt.qa.servicevirtualization.ResponsePattern.HeaderDetailVerificationList;
import com.mstt.qa.servicevirtualization.ResponsePattern.HeaderDetailVerificationList.HeaderDetailVerification;
import com.mstt.qa.servicevirtualization.ResponsePattern.ResponseDataVerificationsList;
import com.mstt.qa.servicevirtualization.ResponsePattern.ResponseDataVerificationsList.ElementTagCountList;
import com.mstt.qa.servicevirtualization.ResponsePattern.ResponseDataVerificationsList.ElementTagCountList.CountOfElementTag;
import com.mstt.qa.servicevirtualization.ResponsePattern.ResponseDataVerificationsList.PathVerificationsList;
import com.mstt.qa.servicevirtualization.ResponsePattern.ResponseDataVerificationsList.PathVerificationsList.XpathVerificationSet;

public class ResponseDataPatternDto {
  public String getExpectedresponseData() {
    return expectedresponseData;
  }

  public void setExpectedresponseData(final String expectedresponseData) {
    this.expectedresponseData = expectedresponseData;
  }

  public Map<String, String> getResponseHeaderDetails() {
    return responseHeaderDetails;
  }

  public void setResponseHeaderDetails(final Map<String, String> responseHeaderDetails) {
    this.responseHeaderDetails = responseHeaderDetails;
  }

  public Map<String, String> getPathVerificationDetailsList() {
    return pathVerificationDetailsList;
  }

  public void setPathVerificationDetailsList(final Map<String, String> pathVerificationDetailsList) {
    this.pathVerificationDetailsList = pathVerificationDetailsList;
  }

  public ResponseDataVerificationDto getResponseDataVerification() {
    return responseDataVerification;
  }

  public void setResponseDataVerification(final ResponseDataVerificationDto responseDataVerification) {
    this.responseDataVerification = responseDataVerification;
  }

  public ResponseComplianceDto getResponseCompliance() {
    return responseCompliance;
  }

  public void setResponseCompliance(final ResponseComplianceDto responseCompliance) {
    this.responseCompliance = responseCompliance;
  }

  public ResponseTimeVerificationDto getResponseTimeVerification() {
    return responseTimeVerification;
  }

  public void setResponseTimeVerification(final ResponseTimeVerificationDto responseTimeVerification) {
    this.responseTimeVerification = responseTimeVerification;
  }

  public ServiceDataType getResponseDataType() {
    return responseDataType;
  }

  public void setResponseDataType(final ServiceDataType responseDataType) {
    this.responseDataType = responseDataType;
  }

  public Boolean isCheckSoapFaults() {
    return checkSoapFaults;
  }

  public void setCheckSoapFaults(final Boolean checkSoapFaults) {
    this.checkSoapFaults = checkSoapFaults;
  }

  public Map<String, Long> getCountTagName() {
    return countTagName;
  }

  public void setCountTagName(final Map<String, Long> countTagName) {
    this.countTagName = countTagName;
  }

  public Boolean getCheckSoapFaults() {
    return checkSoapFaults;
  }

  private ServiceDataType responseDataType;
  private Map<String, String> responseHeaderDetails;
  private Map<String, String> pathVerificationDetailsList;
  private ResponseDataVerificationDto responseDataVerification;
  private ResponseComplianceDto responseCompliance;
  private Map<String, Long> countTagName;
  private ResponseTimeVerificationDto responseTimeVerification;
  private String expectedresponseData;
  private Boolean checkSoapFaults;

  public ResponseDataPatternDto() {
    responseHeaderDetails = new HashMap<String, String>();
    pathVerificationDetailsList = new HashMap<String, String>();
    countTagName = new HashMap<String, Long>();
  }

  public static ResponsePattern getResponsePatterDto(final ResponseDataPatternDto resDataPtrnDto)
      throws IOException {
    ResponsePattern respPattern = new ResponsePattern();
    respPattern.setResponseDataType(RequestAndResponseDataType.fromValue(resDataPtrnDto
        .getResponseDataType().value()));
    respPattern.setResponseBody(resDataPtrnDto.getExpectedresponseData());
    respPattern.setHeaderDetailVerificationList(getHeaderList(resDataPtrnDto
        .getResponseHeaderDetails()));
    ResponseDataVerificationsList responseDataVerLst = new ResponseDataVerificationsList();
    responseDataVerLst.setCheckSoapFaults(resDataPtrnDto.isCheckSoapFaults());
    if (isNotNull(resDataPtrnDto.getResponseCompliance())) {
      responseDataVerLst.setComplianceAgainstSchemaOrWsdl(ResponseComplianceDto
          .getResponseCompliance(resDataPtrnDto.getResponseCompliance()));
    }
    responseDataVerLst.setPathVerificationsList(getPathList(resDataPtrnDto
        .getPathVerificationDetailsList()));
    if (isNotNull(resDataPtrnDto.getResponseTimeVerification())) {
      responseDataVerLst.setResponseTimeVerification(ResponseTimeVerificationDto
          .getResponseTime(resDataPtrnDto.getResponseTimeVerification()));
    }
    responseDataVerLst
        .setElementTagCountList(getCountElementTags(resDataPtrnDto.getCountTagName()));
    if (isNotNull(resDataPtrnDto.getResponseDataVerification())) {
      responseDataVerLst.setTotalResponseDataVerification(ResponseDataVerificationDto
          .getResponseDataVerifcation(resDataPtrnDto.getResponseDataVerification()));
    }
    respPattern.setResponseDataVerificationsList(responseDataVerLst);
    return respPattern;

  }

  public ResponsePattern getResponsePatterDtoFromJaxb() throws IOException {
    ResponsePattern respPattern = new ResponsePattern();
    respPattern.setResponseDataType(RequestAndResponseDataType.fromValue(getResponseDataType()
        .value()));
    respPattern.setResponseBody(getExpectedresponseData());
    respPattern.setHeaderDetailVerificationList(getHeaderList(getResponseHeaderDetails()));
    ResponseDataVerificationsList responseDataVerLst = new ResponseDataVerificationsList();
    responseDataVerLst.setCheckSoapFaults(isCheckSoapFaults());
    responseDataVerLst.setComplianceAgainstSchemaOrWsdl(ResponseComplianceDto
        .getResponseCompliance(getResponseCompliance()));
    responseDataVerLst.setPathVerificationsList(getPathList(getPathVerificationDetailsList()));
    responseDataVerLst.setResponseTimeVerification(ResponseTimeVerificationDto
        .getResponseTime(getResponseTimeVerification()));
    responseDataVerLst.setElementTagCountList(getCountElementTags(getCountTagName()));
    responseDataVerLst.setTotalResponseDataVerification(ResponseDataVerificationDto
        .getResponseDataVerifcation(getResponseDataVerification()));
    respPattern.setResponseDataVerificationsList(responseDataVerLst);
    return respPattern;

  }

  private static ElementTagCountList getCountElementTags(final Map<String, Long> countTagName2) {
    ElementTagCountList elecntLst = new ElementTagCountList();
    if (isNotEmpty(countTagName2)) {
      List<CountOfElementTag> lst = new ArrayList<CountOfElementTag>();
      for (Entry<String, Long> entry : countTagName2.entrySet()) {
        CountOfElementTag dt = new CountOfElementTag();
        dt.setElementTagName(entry.getKey());
        dt.setCountOfElementName(entry.getValue());
        lst.add(dt);
      }
      elecntLst.getCountOfElementTag().addAll(lst);
    }
    return elecntLst;
  }

  private static PathVerificationsList getPathList(
      final Map<String, String> pathVerificationDetailsList2) {
    PathVerificationsList hdrlst = new PathVerificationsList();
    if (isNotEmpty(pathVerificationDetailsList2)) {
      List<XpathVerificationSet> lst = new ArrayList<XpathVerificationSet>();
      for (Entry<String, String> entry : pathVerificationDetailsList2.entrySet()) {
        XpathVerificationSet dt = new XpathVerificationSet();
        dt.setXpathKey(entry.getKey());
        dt.setXpathValue(entry.getValue());
        lst.add(dt);
      }
      hdrlst.getXpathVerificationSet().addAll(lst);
    }
    return hdrlst;
  }

  private static HeaderDetailVerificationList getHeaderList(
      final Map<String, String> responseHeaderDetails2) {
    List<HeaderDetailVerification> lst = new ArrayList<HeaderDetailVerification>();
    for (Entry<String, String> entry : responseHeaderDetails2.entrySet()) {
      HeaderDetailVerification dt = new HeaderDetailVerification();
      dt.setHeaderElementName(entry.getKey());
      dt.setHeaderElementValue(entry.getValue());
      lst.add(dt);
    }
    HeaderDetailVerificationList hdrlst = new HeaderDetailVerificationList();
    hdrlst.getHeaderDetailVerification().addAll(lst);
    return hdrlst;
  }

  public static ResponseDataPatternDto getResponsePatterDtoFromJaxb(
      final ResponsePattern responseDataPatternJaxb) throws IOException {
    ResponseDataPatternDto respPattern = new ResponseDataPatternDto();
    if (isNotNull(responseDataPatternJaxb.getResponseDataType())) {
      respPattern.setResponseDataType(ServiceDataType.fromString(responseDataPatternJaxb
          .getResponseDataType().value()));
    }
    respPattern.setExpectedresponseData(responseDataPatternJaxb.getResponseBody());
    if (isNotNull(responseDataPatternJaxb.getResponseDataVerificationsList())) {
      if (isNotNull(responseDataPatternJaxb.getResponseDataVerificationsList().isCheckSoapFaults())) {
        respPattern.setCheckSoapFaults(responseDataPatternJaxb.getResponseDataVerificationsList()
            .isCheckSoapFaults());
      }
      if (isNotNull(responseDataPatternJaxb.getResponseDataVerificationsList()
          .getTotalResponseDataVerification())) {
        respPattern.setResponseDataVerification(ResponseDataVerificationDto
            .responseDataVerificationDtoFromJaxb(responseDataPatternJaxb
                .getResponseDataVerificationsList().getTotalResponseDataVerification()));
      }
      if (isNotNull(responseDataPatternJaxb.getResponseDataVerificationsList()
          .getResponseTimeVerification())) {
        respPattern.setResponseTimeVerification(ResponseTimeVerificationDto
            .getResponseTimeVerificationDtoFromJaxb(responseDataPatternJaxb
                .getResponseDataVerificationsList().getResponseTimeVerification()));
      }
      if (isNotNull(responseDataPatternJaxb.getResponseDataVerificationsList()
          .getComplianceAgainstSchemaOrWsdl())) {
        respPattern.setResponseCompliance(ResponseComplianceDto
            .getResponseComplianceFromJaxb(responseDataPatternJaxb
                .getResponseDataVerificationsList().getComplianceAgainstSchemaOrWsdl()));
      }
      if (isNotNull(responseDataPatternJaxb.getHeaderDetailVerificationList()
          .getHeaderDetailVerification())) {
        populateHeaderMap(respPattern.getResponseHeaderDetails(), responseDataPatternJaxb
            .getHeaderDetailVerificationList().getHeaderDetailVerification());
      }
      if (isNotNull(responseDataPatternJaxb.getResponseDataVerificationsList()
          .getPathVerificationsList())) {
        populatePathMap(respPattern.getPathVerificationDetailsList(), responseDataPatternJaxb
            .getResponseDataVerificationsList().getPathVerificationsList()
            .getXpathVerificationSet());
      }
      respPattern.setCountTagName(populateTagCount(responseDataPatternJaxb
          .getResponseDataVerificationsList().getElementTagCountList()));
    }
    return respPattern;
  }

  private static Map<String, Long> populateTagCount(final ElementTagCountList elementTagCountList) {
    Map<String, Long> elementCountTag = new HashMap<String, Long>();
    if (isNotNull(elementTagCountList)) {
      if (isNotNull(elementTagCountList.getCountOfElementTag())) {
        for (CountOfElementTag countElementTag : elementTagCountList.getCountOfElementTag()) {
          elementCountTag.put(countElementTag.getElementTagName(),
              countElementTag.getCountOfElementName());
        }
      }
    }
    return elementCountTag;
  }

  private static void populatePathMap(final Map<String, String> pathVerDtlsList,
      final List<XpathVerificationSet> pathVerificationList) {
    for (XpathVerificationSet pathVerificationSet : pathVerificationList) {
      pathVerDtlsList.put(pathVerificationSet.getXpathKey(), pathVerificationSet.getXpathValue());
    }

  }

  private static void populateHeaderMap(final Map<String, String> responseHdrDetails,
      final List<HeaderDetailVerification> list) {
    for (HeaderDetailVerification headerDetailVerification : list) {
      responseHdrDetails.put(headerDetailVerification.getHeaderElementName(),
          headerDetailVerification.getHeaderElementValue());
    }

  }

}
