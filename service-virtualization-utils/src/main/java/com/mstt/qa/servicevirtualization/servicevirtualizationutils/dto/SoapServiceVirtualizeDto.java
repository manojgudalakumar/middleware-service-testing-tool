package com.mstt.qa.servicevirtualization.servicevirtualizationutils.dto;

import static com.mstt.qa.servicevirtualization.servicevirtualizationutils.utils.CommonMethodsUtils.isNotNull;
import static org.apache.commons.collections.CollectionUtils.isNotEmpty;

import java.net.URI;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.mstt.qa.servicevirtualization.PropertyMapping;
import com.mstt.qa.servicevirtualization.SoapServiceOverHttpDetails;
import com.mstt.qa.servicevirtualization.SoapServiceOverHttpDetails.VirtualizeSoapServicePropertiesList;
import com.mstt.qa.servicevirtualization.servicevirtualizationutils.exceptions.PropertyAlreadyExistsException;

public class SoapServiceVirtualizeDto {

  public String getSoapServiceVirtualizeName() {
    return soapServiceVirtualizeName;
  }

  public void setSoapServiceVirtualizeName(final String soapServiceVirtualizeName) {
    this.soapServiceVirtualizeName = soapServiceVirtualizeName;
  }

  public String getSoapServiceWsdlUrl() {
    return soapServiceWsdlUrl;
  }

  public void setSoapServiceWsdlUrl(final String soapServiceWsdlUrl) {
    this.soapServiceWsdlUrl = soapServiceWsdlUrl;
  }

  public String getSoapOperationName() {
    return soapOperationName;
  }

  public void setSoapOperationName(final String soapOperationName) {
    this.soapOperationName = soapOperationName;
  }

  public String getRequestXml() {
    return requestXml;
  }

  public void setRequestXml(final String requestXml) {
    this.requestXml = requestXml;
  }

  public String getResponseXml() {
    return responseXml;
  }

  public void setResponseXml(final String responseXml) {
    this.responseXml = responseXml;
  }

  public String getVirtualizeServiceMappingFileName() {
    return virtualizeServiceMappingFileName;
  }

  public void setVirtualizeServiceMappingFileName(final String virtualizeServiceMappingFileName) {
    this.virtualizeServiceMappingFileName = virtualizeServiceMappingFileName;
  }

  public Calendar getCreationTimeStamp() {
    return creationTimeStamp;
  }

  public void setCreationTimeStamp(final Calendar creationTimeStamp) {
    this.creationTimeStamp = creationTimeStamp;
  }

  public Calendar getLastUpdatedTimeStamp() {
    return lastUpdatedTimeStamp;
  }

  public void setLastUpdatedTimeStamp(final Calendar lastUpdatedTimeStamp) {
    this.lastUpdatedTimeStamp = lastUpdatedTimeStamp;
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

  public URI getWsdlURI() {
    return URI.create(soapServiceWsdlUrl);
  }

  public String getWsdlURL() {
    return soapServiceWsdlUrl;
  }

  public String getSoapEndPointUrl() {
    return soapEndPointUrl;
  }

  public void setSoapEndPointUrl(final String soapEndPointUrl) {
    this.soapEndPointUrl = soapEndPointUrl;
  }

  public String getSoapVirtualReferenceId() {
    return soapVirtualReferenceId;
  }

  public void setSoapVirtualReferenceId(final String soapVirtualReferenceId) {
    this.soapVirtualReferenceId = soapVirtualReferenceId;
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

  private String soapServiceWsdlUrl;
  private String soapOperationName;
  private String requestXml;
  private String responseXml;
  private String virtualizeServiceMappingFileName;
  private Calendar creationTimeStamp;
  private Calendar lastUpdatedTimeStamp;
  private Map<String, String> propertiesMap;
  private String soapEndPointUrl;
  private String soapServiceVirtualizeName;
  private String soapVirtualReferenceId;
  private MsttRequestDefinition requesStub;
  private MsttResponseDefinition responseStub;

  public SoapServiceVirtualizeDto(final String soapVirtualName, final String soapServiceWsdlUrl,
      final String soapOperationName, final String requestXml, final String responseXml,
      final String virtualizeServiceMappingFileName, final Calendar creationTimeStamp,
      final Calendar lastUpdatedTimeStamp, final Map<String, String> propertiesMap) {
    soapServiceVirtualizeName = soapVirtualName;
    this.soapOperationName = soapOperationName;
    this.soapServiceWsdlUrl = soapServiceWsdlUrl;
    this.requestXml = requestXml;
    this.responseXml = responseXml;
    this.virtualizeServiceMappingFileName = virtualizeServiceMappingFileName;
    this.creationTimeStamp = creationTimeStamp;
    this.lastUpdatedTimeStamp = lastUpdatedTimeStamp;
    this.propertiesMap = propertiesMap;
    soapVirtualReferenceId = UUID.randomUUID().toString();

  }

  public SoapServiceVirtualizeDto() {
    propertiesMap = new HashMap<String, String>();
    soapVirtualReferenceId = UUID.randomUUID().toString();
    soapServiceVirtualizeName = "New Soap Virtual Name";
    creationTimeStamp = Calendar.getInstance();
    lastUpdatedTimeStamp = Calendar.getInstance();
    requesStub = new MsttRequestDefinition();
    responseStub = new MsttResponseDefinition();

  }

  public static SoapServiceVirtualizeDto getSoapServiceFromJaxb(
      final SoapServiceOverHttpDetails soapHtt) {
    SoapServiceVirtualizeDto spVrDto = new SoapServiceVirtualizeDto();
    spVrDto.setCreationTimeStamp(soapHtt.getCreatedTimestamp());
    spVrDto.setLastUpdatedTimeStamp(soapHtt.getLastUpdatedTimestamp());
    if (isNotEmpty(soapHtt.getVirtualizeSoapServicePropertiesList().getServiceTestProperty())) {
      spVrDto.setPropertiesMap(getSoapProperties(soapHtt.getVirtualizeSoapServicePropertiesList()
          .getServiceTestProperty()));
    }
    // spVrDto.setSoapServiceVirtualizeName(soapHtt.getSoapVirtualizeName());
    spVrDto.setRequestXml(soapHtt.getSoapRequestData());
    spVrDto.setResponseXml(soapHtt.getSoapResponseData());
    spVrDto.setSoapOperationName(soapHtt.getSoapOperationName());
    spVrDto.setSoapServiceVirtualizeName(soapHtt.getSoapVirtualizeName());
    spVrDto.setSoapServiceWsdlUrl(soapHtt.getSoapVirtualizeWsdlUrl());
    spVrDto.setVirtualizeServiceMappingFileName(soapHtt.getSoapVirtualMappingDataFileName());
    spVrDto.setSoapEndPointUrl(soapHtt.getSoapVirtualEndPointUrl());
    spVrDto.setSoapVirtualReferenceId(soapHtt.getSoapVirtualReferenceId());
    return spVrDto;
  }

  private static Map<String, String> getSoapProperties(final List<PropertyMapping> list) {
    Map<String, String> propMap = new HashMap<String, String>();
    for (PropertyMapping propertyMapping : list) {
      propMap.put(propertyMapping.getPropertyKey(), propertyMapping.getPropetyValue());

    }
    return propMap;
  }

  public static SoapServiceOverHttpDetails getSoapService(
      final SoapServiceVirtualizeDto soapServiceDTo) {
    SoapServiceOverHttpDetails sophttp = new SoapServiceOverHttpDetails();
    sophttp.setCreatedTimestamp(soapServiceDTo.getCreationTimeStamp());
    sophttp.setLastUpdatedTimestamp(soapServiceDTo.getLastUpdatedTimeStamp());
    sophttp.setSoapOperationName(soapServiceDTo.getSoapOperationName());
    sophttp.setSoapRequestData(soapServiceDTo.getRequestXml());
    sophttp.setSoapResponseData(soapServiceDTo.getResponseXml());
    sophttp.setSoapVirtualizeName(soapServiceDTo.getSoapServiceVirtualizeName());
    sophttp.setSoapVirtualizeWsdlUrl(soapServiceDTo.getSoapServiceWsdlUrl());
    sophttp.setSoapVirtualMappingDataFileName(soapServiceDTo.getVirtualizeServiceMappingFileName());
    if (isNotNull(soapServiceDTo.getPropertiesMap())) {
      sophttp.setVirtualizeSoapServicePropertiesList(getProperties(soapServiceDTo
          .getPropertiesMap()));
    }
    sophttp.setSoapVirtualReferenceId(soapServiceDTo.getSoapVirtualReferenceId());
    sophttp.setSoapVirtualEndPointUrl(soapServiceDTo.getSoapEndPointUrl());
    return sophttp;
  }

  private static VirtualizeSoapServicePropertiesList getProperties(
      final Map<String, String> propertiesMap2) {
    List<PropertyMapping> propLst = new ArrayList<PropertyMapping>();
    for (Map.Entry<String, String> entryMap : propertiesMap2.entrySet()) {
      PropertyMapping propMpn = new PropertyMapping();
      propMpn.setPropertyKey(entryMap.getKey());
      propMpn.setPropetyValue(entryMap.getValue());
      propLst.add(propMpn);
    }
    VirtualizeSoapServicePropertiesList vstlst = new VirtualizeSoapServicePropertiesList();
    vstlst.getServiceTestProperty().addAll(propLst);
    return vstlst;
  }

  public void convertToString() {
    StringBuffer str = new StringBuffer();
    str.append("Virtual service name is " + getSoapOperationName());
    str.append("Virtual service URL is " + getSoapEndPointUrl());
    str.append("virtual service rest request data type" + getSoapServiceVirtualizeName());
    str.append("virtual service rest reponse data type" + getSoapServiceVirtualizeName());
  }

  @Override
  public String toString() {
    return soapServiceVirtualizeName;
  }

}
