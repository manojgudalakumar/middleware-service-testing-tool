package com.mstt.qa.servicevirtualization.servicevirtualizationutils.dto;

import static com.mstt.qa.servicevirtualization.servicevirtualizationutils.utils.CommonMethodsUtils.isNotNull;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.mstt.qa.servicevirtualization.PropertyMapping;
import com.mstt.qa.servicevirtualization.RequestAndResponseDataType;
import com.mstt.qa.servicevirtualization.RestServiceDetails;
import com.mstt.qa.servicevirtualization.RestServiceDetails.VirtualizeRestServicePropertiesList;
import com.mstt.qa.servicevirtualization.servicevirtualizationutils.exceptions.PropertyAlreadyExistsException;

public class RestServiceVirtualizeDto {
  public String getRestServiceVirtualName() {
    return restServiceVirtualName;
  }

  public void setRestServiceVirtualName(final String restServiceVirtualName) {
    this.restServiceVirtualName = restServiceVirtualName;
  }

  public String getRestServiceVirtualUrl() {
    return restServiceVirtualUrl;
  }

  public void setRestServiceVirtualUrl(final String restServiceVirtualUrl) {
    this.restServiceVirtualUrl = restServiceVirtualUrl;
  }

  public String getRestVirtualMappingFileName() {
    return restVirtualMappingFileName;
  }

  public void setRestVirtualMappingFileName(final String restVirtualMappingFileName) {
    this.restVirtualMappingFileName = restVirtualMappingFileName;
  }

  public String getRestVirutalwadlUrl() {
    return restVirutalwadlUrl;
  }

  public void setRestVirutalwadlUrl(final String restVirutalwadlUrl) {
    this.restVirutalwadlUrl = restVirutalwadlUrl;
  }

  public ServiceDataType getRestVirtualResponseDataType() {
    return restVirtualResponseDataType;
  }

  public void setRestVirtualResponseDataType(final ServiceDataType restVirtualResponseDataType) {
    this.restVirtualResponseDataType = restVirtualResponseDataType;
  }

  public String getRequestData() {
    return requestData;
  }

  public void setRequestData(final String requestData) {
    this.requestData = requestData;
  }

  public String getResponseData() {
    return responseData;
  }

  public void setResponseData(final String responseData) {
    this.responseData = responseData;
  }

  public ServiceDataType getRestVirtualRequestDataType() {
    return restVirtualRequestDataType;
  }

  public void setRestVirtualRequestDataType(final ServiceDataType restVirtualRequestDataType) {
    this.restVirtualRequestDataType = restVirtualRequestDataType;
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

  public Map<String, String> getPropertiesMap() {
    return propertiesMap;
  }

  public void setPropertiesMap(final Map<String, String> propertiesMap) {
    this.propertiesMap = propertiesMap;
  }

  public void addProperty(final String key, final String value)
      throws PropertyAlreadyExistsException {
    if (propertiesMap.containsKey(key)) {
      throw new PropertyAlreadyExistsException(key, value);
    } else {
      propertiesMap.put(key, value);
    }
  }

  public void removeProperty(final String key) {
    propertiesMap.remove(key);
  }

  public String getRestVirtualReferenceId() {
    return restVirtualReferenceId;
  }

  public void setRestVirtualReferenceId(final String restVirtualReferenceId) {
    this.restVirtualReferenceId = restVirtualReferenceId;
  }

  public MsttRequestDefinition getRequesStub() {
    return requesStub;
  }

  public void setRequesStub(final MsttRequestDefinition requesStub) {
    this.requesStub = requesStub;
  }

  public MsttResponseDefinition getResponseStub() {
    return responseStub;
  }

  public void setResponseStub(final MsttResponseDefinition responseStub) {
    this.responseStub = responseStub;
  }

  private String restServiceVirtualName;
  private String restServiceVirtualUrl;
  private String restVirtualMappingFileName;
  private String restVirutalwadlUrl;
  private ServiceDataType restVirtualResponseDataType;
  private String requestData;
  private String responseData;
  private ServiceDataType restVirtualRequestDataType;
  private Calendar lastUpdatedTimeStamp;
  private Calendar creationTimeStamp;
  private Map<String, String> propertiesMap;
  private String restVirtualReferenceId;
  private MsttRequestDefinition requesStub;
  private MsttResponseDefinition responseStub;

  public RestServiceVirtualizeDto() {
    propertiesMap = new HashMap<String, String>();
    restServiceVirtualName = "New Rest Service Virtual";
    restVirtualReferenceId = UUID.randomUUID().toString();
    lastUpdatedTimeStamp = Calendar.getInstance();
    creationTimeStamp = Calendar.getInstance();
    requesStub = new MsttRequestDefinition();
    responseStub = new MsttResponseDefinition();
  }

  public RestServiceVirtualizeDto(final String restServiceVirtualName,
      final String restServiceVirtualUrl, final String restVirtualMappingFileName,
      final String restVirutalwadlUrl, final String restVirtualResponseDataType,
      final String requestData, final String responseData, final String restVirtualRequestDataType,
      final Calendar lastUpdatedTimeStamp, final Calendar creationTimeStamp,
      final Map<String, String> propertiesMap) {
    this.restServiceVirtualName = restServiceVirtualName;
    this.restServiceVirtualUrl = restServiceVirtualUrl;
    this.restVirtualMappingFileName = restVirtualMappingFileName;
    this.restVirutalwadlUrl = restVirutalwadlUrl;
    this.restVirtualRequestDataType = ServiceDataType.valueOf(restVirtualRequestDataType);
    this.restVirtualResponseDataType = ServiceDataType.valueOf(restVirtualResponseDataType);
    this.requestData = requestData;
    this.responseData = responseData;
    this.lastUpdatedTimeStamp = lastUpdatedTimeStamp;
    this.creationTimeStamp = creationTimeStamp;
    this.propertiesMap = propertiesMap;
    restVirtualReferenceId = UUID.randomUUID().toString();
  }

  @Override
  public String toString() {
    return restServiceVirtualName;
  }

  public static RestServiceVirtualizeDto getRestVirtualizeDto(final RestServiceDetails reDetails) {
    RestServiceVirtualizeDto resVirtDto = new RestServiceVirtualizeDto();
    resVirtDto.setCreationTimeStamp(reDetails.getCreatedTimestamp());
    resVirtDto.setLastUpdatedTimeStamp(reDetails.getLastUpdatedTimestamp());
    if (isNotNull(reDetails.getVirtualizeRestServicePropertiesList().getServiceTestProperty())) {
      resVirtDto.setPropertiesMap(getProperties(reDetails.getVirtualizeRestServicePropertiesList()
          .getServiceTestProperty()));
    }
    resVirtDto.setRequestData(reDetails.getRestRequestData());
    resVirtDto.setResponseData(reDetails.getRestResponseData());
    resVirtDto.setRestServiceVirtualName(reDetails.getRestVirtualizeName());
    resVirtDto.setRestServiceVirtualUrl(reDetails.getRestVirtualizeUrl());
    resVirtDto.setRestVirtualMappingFileName(reDetails.getRestVirtualMappingDataFileName());
    if (isNotNull(reDetails.getRequestDataType())) {
      resVirtDto.setRestVirtualRequestDataType(ServiceDataType.fromString(reDetails
          .getRequestDataType().value()));
    }
    if (isNotNull(reDetails.getResponseDataType())) {
      resVirtDto.setRestVirtualResponseDataType(ServiceDataType.fromString(reDetails
          .getResponseDataType().value()));
    }
    resVirtDto.setRestVirutalwadlUrl(reDetails.getRestVirtualizeWadlUrl());
    resVirtDto.setRestVirtualReferenceId(reDetails.getRestVirtualReferenceId());
    return resVirtDto;

  }

  private static Map<String, String> getProperties(final List<PropertyMapping> serviceTestProperty) {
    Map<String, String> propMap = new HashMap<String, String>();
    for (PropertyMapping propertyMapping : serviceTestProperty) {
      propMap.put(propertyMapping.getPropertyKey(), propertyMapping.getPropetyValue());

    }
    return propMap;
  }

  public static RestServiceDetails getRestService(final RestServiceVirtualizeDto rstDto) {
    RestServiceDetails restDtl = new RestServiceDetails();
    restDtl.setCreatedTimestamp(rstDto.getCreationTimeStamp());
    restDtl.setLastUpdatedTimestamp(rstDto.getLastUpdatedTimeStamp());
    if (isNotNull(rstDto.getRestVirtualRequestDataType())) {
      restDtl.setRequestDataType(RequestAndResponseDataType.fromValue(rstDto
          .getRestVirtualRequestDataType().value()));
    }
    if (isNotNull(rstDto.getRestVirtualResponseDataType())) {
      restDtl.setResponseDataType(RequestAndResponseDataType.fromValue(rstDto
          .getRestVirtualResponseDataType().value()));
    }
    restDtl.setRestRequestData(rstDto.getRequestData());
    restDtl.setRestResponseData(rstDto.getResponseData());
    restDtl.setRestVirtualizeName(rstDto.getRestServiceVirtualName());
    restDtl.setRestVirtualizeUrl(rstDto.getRestServiceVirtualUrl());
    restDtl.setRestVirtualizeWadlUrl(rstDto.getRestVirutalwadlUrl());
    restDtl.setRestVirtualMappingDataFileName(rstDto.getRestVirtualMappingFileName());
    restDtl.setVirtualizeRestServicePropertiesList(getProp(rstDto.getPropertiesMap()));
    restDtl.setRestVirtualReferenceId(rstDto.getRestVirtualReferenceId());
    return restDtl;
  }

  private static VirtualizeRestServicePropertiesList getProp(
      final Map<String, String> propertiesMap2) {
    List<PropertyMapping> prolst = new ArrayList<PropertyMapping>();
    for (Map.Entry<String, String> entry : propertiesMap2.entrySet()) {
      PropertyMapping propMapping = new PropertyMapping();
      propMapping.setPropertyKey(entry.getKey());
      propMapping.setPropetyValue(entry.getValue());
      prolst.add(propMapping);
    }
    VirtualizeRestServicePropertiesList vstrst = new VirtualizeRestServicePropertiesList();
    vstrst.getServiceTestProperty().addAll(prolst);
    return vstrst;
  }
}
