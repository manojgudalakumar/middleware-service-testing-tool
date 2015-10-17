package com.mstt.qa.servicevirtualization.servicevirtualizationutils.dto;

import static com.mstt.qa.servicevirtualization.servicevirtualizationutils.utils.CommonMethodsUtils.isNotNull;
import static com.mstt.qa.servicevirtualization.servicevirtualizationutils.utils.CommonMethodsUtils.listToString;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.mstt.qa.servicevirtualization.MiddleWareTestingTool.ServiceTestList;
import com.mstt.qa.servicevirtualization.PropertyMapping;
import com.mstt.qa.servicevirtualization.ServiceTestDetails;
import com.mstt.qa.servicevirtualization.ServiceTestDetails.ServiceTestChildDetailsList;
import com.mstt.qa.servicevirtualization.ServiceTestDetails.ServiceTestPropertiesList;
import com.mstt.qa.servicevirtualization.servicevirtualizationutils.exceptions.PropertyAlreadyExistsException;

public class ServiceTestDetailsDto {

  public String getServiceTestName() {
    return serviceTestName;
  }

  public void setServiceTestName(final String serviceTestName) {
    this.serviceTestName = serviceTestName;
  }

  public String getServiceTestDescription() {
    return serviceTestDescription;
  }

  public void setServiceTestDescription(final String serviceTestDescription) {
    this.serviceTestDescription = serviceTestDescription;
  }

  public String getServiceTestReferenceId() {
    return serviceTestReferenceId;
  }

  public void setServiceTestReferenceId(final String serviceTestReferenceId) {
    this.serviceTestReferenceId = serviceTestReferenceId;
  }

  public Calendar getLastUpdatedTimeStamp() {
    return lastUpdatedTimeStamp;
  }

  public void setLastUpdatedTimeStamp(final Calendar lastUpdatedTimeStamp) {
    this.lastUpdatedTimeStamp = lastUpdatedTimeStamp;
  }

  public Calendar getCreationTimeStamp() {
    return creationTimeStamp;
  }

  public void setCreationTimeStamp(final Calendar creationTimeStamp) {
    this.creationTimeStamp = creationTimeStamp;
  }

  public String getServiceTestParentReferenceId() {
    return serviceTestParentReferenceId;
  }

  public void setServiceTestParentReferenceId(final String serviceTestParentReferenceId) {
    this.serviceTestParentReferenceId = serviceTestParentReferenceId;
  }

  public String getServiceTestEndPointUrl() {
    return serviceTestEndPointUrl;
  }

  public void setServiceTestEndPointUrl(final String serviceTestEndPointUrl) {
    this.serviceTestEndPointUrl = serviceTestEndPointUrl;
  }

  public String getServiceTestWsdlUrl() {
    return serviceTestWsdlUrl;
  }

  public void setServiceTestWsdlUrl(final String serviceTestWsdlUrl) {
    this.serviceTestWsdlUrl = serviceTestWsdlUrl;
  }

  public String getServiceTestInformation() {
    return serviceTestInformation;
  }

  public void setServiceTestInformation(final String serviceTestInformation) {
    this.serviceTestInformation = serviceTestInformation;
  }

  public List<String> getReferenceTags() {
    return referenceTags;
  }

  public void setReferenceTags(final List<String> referenceTags) {
    this.referenceTags = referenceTags;
  }

  public List<ServiceTestDetailsDto> getServiceTestChildDetailslist() {
    return serviceTestChildDetailslist;
  }

  public void setServiceTestChildDetailslist(
      final List<ServiceTestDetailsDto> serviceTestChildDetailslist) {
    this.serviceTestChildDetailslist = serviceTestChildDetailslist;
  }

  public RequestDataPatternDto getRequestDataPattern() {
    return requestDataPattern;
  }

  public void setRequestDataPattern(final RequestDataPatternDto requestDataPattern) {
    this.requestDataPattern = requestDataPattern;
  }

  public ResponseDataPatternDto getResponseDataPattern() {
    return responseDataPattern;
  }

  public void setResponseDataPattern(final ResponseDataPatternDto responseDataPattern) {
    this.responseDataPattern = responseDataPattern;
  }

  public PropertyDto getPropertyDto() {
    return propertyDto;
  }

  public void setPropertyDto(final PropertyDto propertyDto) {
    this.propertyDto = propertyDto;
  }

  private String serviceTestName;
  private String serviceTestDescription;
  private String serviceTestReferenceId;
  private Calendar lastUpdatedTimeStamp;
  private Calendar creationTimeStamp;
  private String serviceTestParentReferenceId;
  private PropertyDto propertyDto;
  private String serviceTestEndPointUrl;
  private String serviceTestWsdlUrl;
  private String serviceTestInformation;
  private List<String> referenceTags;
  private List<ServiceTestDetailsDto> serviceTestChildDetailslist;
  private RequestDataPatternDto requestDataPattern;
  private ResponseDataPatternDto responseDataPattern;

  public ServiceTestDetailsDto() {
    referenceTags = new ArrayList<String>();
    serviceTestChildDetailslist = new ArrayList<ServiceTestDetailsDto>();
  }

  public ServiceTestDetailsDto(final String serviceTestName, final String serviceTestDescription,
      final String serviceTestReferenceId, final Calendar lastUpdatedTimeStamp,
      final Calendar creationTimeStamp, final String serviceTestParentReferenceId,
      final PropertyDto propertyDto, final String serviceTestEndPointUrl,
      final String serviceTestWsdlUrl, final String serviceTestInformation,
      final List<String> referenceTags,
      final List<ServiceTestDetailsDto> serviceTestChildDetailslist,
      final RequestDataPatternDto requestDataPattern,
      final ResponseDataPatternDto responseDataPattern) {
    this.serviceTestName = serviceTestName;
    this.serviceTestDescription = serviceTestDescription;
    this.serviceTestReferenceId = serviceTestReferenceId;
    this.lastUpdatedTimeStamp = lastUpdatedTimeStamp;
    this.creationTimeStamp = creationTimeStamp;
    this.serviceTestParentReferenceId = serviceTestParentReferenceId;
    this.propertyDto = propertyDto;
    this.serviceTestEndPointUrl = serviceTestEndPointUrl;
    this.serviceTestWsdlUrl = serviceTestWsdlUrl;
    this.serviceTestInformation = serviceTestInformation;
    this.referenceTags = referenceTags;
    this.serviceTestChildDetailslist = serviceTestChildDetailslist;
    this.requestDataPattern = requestDataPattern;
    this.responseDataPattern = responseDataPattern;
  }

  public ServiceTestDetailsDto(final String serviceTestName, final String serviceTestDescription,
      final Calendar lastUpdatedTimeStamp, final Calendar creationTimeStamp,
      final String serviceTestParentReferenceId, final PropertyDto propertyDto,
      final String serviceTestEndPointUrl, final String serviceTestWsdlUrl,
      final String serviceTestInformation, final List<String> referenceTags,
      final List<ServiceTestDetailsDto> serviceTestChildDetailslist,
      final RequestDataPatternDto requestDataPattern,
      final ResponseDataPatternDto responseDataPattern) {
    this.serviceTestName = serviceTestName;
    this.serviceTestDescription = serviceTestDescription;
    serviceTestReferenceId = UUID.randomUUID().toString();
    this.lastUpdatedTimeStamp = lastUpdatedTimeStamp;
    this.creationTimeStamp = creationTimeStamp;
    this.serviceTestParentReferenceId = serviceTestParentReferenceId;
    this.propertyDto = propertyDto;
    this.serviceTestEndPointUrl = serviceTestEndPointUrl;
    this.serviceTestWsdlUrl = serviceTestWsdlUrl;
    this.serviceTestInformation = serviceTestInformation;
    this.referenceTags = referenceTags;
    this.serviceTestChildDetailslist = serviceTestChildDetailslist;
    this.requestDataPattern = requestDataPattern;
    this.responseDataPattern = responseDataPattern;
  }

  public void addChild(final ServiceTestDetailsDto serviceTestDetails) {
    if (isNotNull(serviceTestDetails.getServiceTestReferenceId())) {
      serviceTestDetails.setServiceTestReferenceId(UUID.randomUUID().toString());
    }
    if (isNotNull(serviceTestDetails.getServiceTestParentReferenceId())) {
      serviceTestDetails.setServiceTestParentReferenceId(serviceTestParentReferenceId);
    }
    serviceTestChildDetailslist.add(serviceTestDetails);
  }

  public static List<ServiceTestDetailsDto> getserviceTestDetailsDtoFromJaxb(
      final List<ServiceTestDetails> serTesDtlList) throws IOException,
      PropertyAlreadyExistsException {
    List<ServiceTestDetailsDto> serDetDtolst = new ArrayList<ServiceTestDetailsDto>();
    for (ServiceTestDetails servtd : serTesDtlList) {
      serDetDtolst.add(getserviceTestDetailsDtoFromJaxb(servtd));
    }
    return serDetDtolst;
  }

  private static PropertyDto getProperty(final List<PropertyMapping> list,
      final ServiceTestDetails serTesDtl) throws PropertyAlreadyExistsException {
    PropertyDto propDto = new PropertyDto();
    propDto
        .setLevel(serTesDtl.getServiceTestChildDetailsList().getServiceTestChildDetails().size() == 0 ? "TestCase"
            : "TestSuite");
    propDto.setParentTestCaseReferenceId(serTesDtl.getServiceTestParentReferenceNumber());
    propDto.setTestCaseReferenceId(serTesDtl.getServiceTestReferenceNumber());
    for (PropertyMapping propertyMapping : list) {
      propDto.addProperty(propertyMapping.getPropertyKey(), propertyMapping.getPropetyValue());
    }
    return propDto;
  }

  public static ServiceTestList getServiceDetails(final List<ServiceTestDetailsDto> dtoList)
      throws IOException {
    ServiceTestList stList = new ServiceTestList();
    List<ServiceTestDetails> str = new ArrayList<ServiceTestDetails>();
    for (ServiceTestDetailsDto serviceTestDetails : dtoList) {
      ServiceTestDetails myServiceDetails = getMyServiceDetails(serviceTestDetails);
      str.add(myServiceDetails);
    }
    stList.getServiceTestDetails().addAll(str);
    return stList;
  }

  private static ServiceTestDetails getMyServiceDetails(
      final ServiceTestDetailsDto serviceTestDetailsDto) throws IOException {
    ServiceTestDetails serTstDtl = new ServiceTestDetails();
    serTstDtl.setCreatedTimestamp(serviceTestDetailsDto.getCreationTimeStamp());
    serTstDtl.setExtraInformationProvided(serviceTestDetailsDto.getServiceTestInformation());
    serTstDtl.setLastUpdatedTimestamp(serviceTestDetailsDto.getLastUpdatedTimeStamp());
    serTstDtl.setRequestPattern(RequestDataPatternDto.getRequestDataPattern(serviceTestDetailsDto
        .getRequestDataPattern()));
    if (isNotNull(serviceTestDetailsDto.getRequestDataPattern())) {
      serTstDtl.setResponsePattern(ResponseDataPatternDto
          .getResponsePatterDto(serviceTestDetailsDto.getResponseDataPattern()));
    }
    serTstDtl.setServiceTestDescription(serviceTestDetailsDto.getServiceTestDescription());
    serTstDtl.setServiceTestEndpointUrl(serviceTestDetailsDto.getServiceTestEndPointUrl());
    serTstDtl.setServiceTestName(serviceTestDetailsDto.getServiceTestName());
    serTstDtl.setServiceTestParentReferenceNumber(serviceTestDetailsDto
        .getServiceTestParentReferenceId());
    serTstDtl.setServiceTestReferenceNumber(serviceTestDetailsDto.getServiceTestReferenceId());
    if (isNotNull(serviceTestDetailsDto.getPropertyDto())) {
      serTstDtl.setServiceTestPropertiesList(getProperty(serviceTestDetailsDto.getPropertyDto()));
    }
    serTstDtl.setServiceTestTags(listToString(serviceTestDetailsDto.getReferenceTags()));
    serTstDtl.setServiceTestWsdlUrl(serviceTestDetailsDto.getServiceTestWsdlUrl());
    ServiceTestChildDetailsList serviceChldLst = new ServiceTestChildDetailsList();
    serTstDtl.setServiceTestChildDetailsList(serviceChldLst);
    for (ServiceTestDetailsDto servicetstDetailsDto : serviceTestDetailsDto
        .getServiceTestChildDetailslist()) {
      serviceChldLst.getServiceTestChildDetails().add(getMyServiceDetails(servicetstDetailsDto));
      if (servicetstDetailsDto.getServiceTestChildDetailslist().size() == 0) {
        continue;
      }
    }
    return serTstDtl;
  }

  public static ServiceTestDetailsDto getserviceTestDetailsDtoFromJaxb(
      final ServiceTestDetails serTesDtl) throws IOException, PropertyAlreadyExistsException {
    ServiceTestDetailsDto serDetDto = new ServiceTestDetailsDto();
    serDetDto.setCreationTimeStamp(serTesDtl.getCreatedTimestamp());
    serDetDto.setLastUpdatedTimeStamp(serTesDtl.getLastUpdatedTimestamp());
    serDetDto.setRequestDataPattern(RequestDataPatternDto.getRequestDataPatternfromJaxb(serTesDtl
        .getRequestPattern()));
    serDetDto.setResponseDataPattern(ResponseDataPatternDto.getResponsePatterDtoFromJaxb(serTesDtl
        .getResponsePattern()));
    if (isNotNull(serTesDtl.getServiceTestPropertiesList().getServiceTestProperty())) {
      serDetDto.setPropertyDto(getProperty(serTesDtl.getServiceTestPropertiesList()
          .getServiceTestProperty(), serTesDtl));
    }
    serDetDto.setServiceTestDescription(serTesDtl.getServiceTestDescription());
    serDetDto.setServiceTestEndPointUrl(serTesDtl.getServiceTestEndpointUrl());
    serDetDto.setServiceTestInformation(serTesDtl.getExtraInformationProvided());
    serDetDto.setServiceTestName(serTesDtl.getServiceTestName());
    serDetDto.setServiceTestParentReferenceId(serTesDtl.getServiceTestParentReferenceNumber());
    serDetDto.setServiceTestReferenceId(serTesDtl.getServiceTestReferenceNumber());
    serDetDto.setServiceTestWsdlUrl(serTesDtl.getServiceTestWsdlUrl());
    serDetDto.setReferenceTags(Arrays.asList(serTesDtl.getServiceTestTags().split(",")));
    for (ServiceTestDetails serviceTestDtl : serTesDtl.getServiceTestChildDetailsList()
        .getServiceTestChildDetails()) {
      serDetDto.getServiceTestChildDetailslist().add(
          getserviceTestDetailsDtoFromJaxb(serviceTestDtl));
      if (serviceTestDtl.getServiceTestChildDetailsList().getServiceTestChildDetails().size() == 0) {
        continue;
      }
    }
    return serDetDto;
  }

  private static ServiceTestPropertiesList getProperty(final PropertyDto propertyDto2) {
    ServiceTestPropertiesList sp = new ServiceTestPropertiesList();
    List<PropertyMapping> propertyMappings = null;
    if (propertyDto2.getPropertyMap().size() > 0) {
      propertyMappings = new ArrayList<PropertyMapping>();
    }
    for (Map.Entry<String, String> entryMap : propertyDto2.getPropertyMap().entrySet()) {
      PropertyMapping propMpng = new PropertyMapping();
      propMpng.setPropertyKey(entryMap.getKey());
      propMpng.setPropetyValue(entryMap.getValue());
      propertyMappings.add(propMpng);
    }
    sp.getServiceTestProperty().addAll(propertyMappings);
    return sp;
  }

  @Override
  public String toString() {
    return serviceTestName;
  }
}
