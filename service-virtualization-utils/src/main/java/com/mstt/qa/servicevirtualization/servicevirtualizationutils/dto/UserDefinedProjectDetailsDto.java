package com.mstt.qa.servicevirtualization.servicevirtualizationutils.dto;

import static com.mstt.qa.servicevirtualization.servicevirtualizationutils.utils.CommonMethodsUtils.isNotNull;
import static org.apache.commons.collections.CollectionUtils.isNotEmpty;
import static org.apache.commons.collections.MapUtils.isNotEmpty;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.mstt.qa.servicevirtualization.FolderDetails;
import com.mstt.qa.servicevirtualization.MiddleWareTestingTool;
import com.mstt.qa.servicevirtualization.MiddleWareTestingTool.FolderList;
import com.mstt.qa.servicevirtualization.MiddleWareTestingTool.RootLevelPropertiesList;
import com.mstt.qa.servicevirtualization.MiddleWareTestingTool.ServiceVirtualization;
import com.mstt.qa.servicevirtualization.MiddleWareTestingTool.ServiceVirtualization.RestServicesList;
import com.mstt.qa.servicevirtualization.MiddleWareTestingTool.ServiceVirtualization.SoapServicesOverHttpList;
import com.mstt.qa.servicevirtualization.PropertyMapping;
import com.mstt.qa.servicevirtualization.RestServiceDetails;
import com.mstt.qa.servicevirtualization.SoapServiceOverHttpDetails;
import com.mstt.qa.servicevirtualization.servicevirtualizationutils.exceptions.PropertyAlreadyExistsException;

public class UserDefinedProjectDetailsDto {
  public List<FolderDto> getFolderList() {
    return folderList;
  }

  public void setFolderList(final List<FolderDto> folderList) {
    this.folderList = folderList;
  }

  public ProjectDetailsDto getProjectDetails() {
    return projectDetails;
  }

  public void setProjectDetails(final ProjectDetailsDto projectDetails) {
    this.projectDetails = projectDetails;
  }

  public ProxySettingDto getProxySettingDto() {
    return proxySettingDto;
  }

  public void setProxySettingDto(final ProxySettingDto proxySettingDto) {
    this.proxySettingDto = proxySettingDto;
  }

  public PropertyDto getPropertyDto() {
    return propertyDto;
  }

  public void setPropertyDto(final PropertyDto propertyDto) {
    this.propertyDto = propertyDto;
  }

  public List<SoapServiceVirtualizeDto> getSoapServiceVirtualizeDtolist() {
    return soapServiceVirtualizeDtolist;
  }

  public void setSoapServiceVirtualizeDtolist(
      final List<SoapServiceVirtualizeDto> soapServiceVirtualizeDtolist) {
    this.soapServiceVirtualizeDtolist = soapServiceVirtualizeDtolist;
  }

  public List<RestServiceVirtualizeDto> getRestServiceVirtualizeDtolist() {
    return restServiceVirtualizeDtolist;
  }

  public void setRestServiceVirtualizeDtolist(
      final List<RestServiceVirtualizeDto> restServiceVirtualizeDtolist) {
    this.restServiceVirtualizeDtolist = restServiceVirtualizeDtolist;
  }

  public List<ServiceTestDetailsDto> getServiceTestDetails() {
    return serviceTestDetails;
  }

  public void setServiceTestDetails(final List<ServiceTestDetailsDto> serviceTestDetails) {
    this.serviceTestDetails = serviceTestDetails;
  }

  public void addFolder(final FolderDto folderDto) {
    folderList.add(folderDto);
  }

  public void addSoapServiceVirtualize(final SoapServiceVirtualizeDto soapVirDto) {
    soapServiceVirtualizeDtolist.add(soapVirDto);
  }

  public void addRestServiceVirtualize(final RestServiceVirtualizeDto soapVirDto) {
    restServiceVirtualizeDtolist.add(soapVirDto);
  }

  public void serviceTestDetails(final ServiceTestDetailsDto serviceTestDto) {
    serviceTestDetails.add(serviceTestDto);
  }

  public boolean isProxyEnabled() {
    return isProxyEnabled;
  }

  public void setProxyEnabled(final boolean isProxyEnabled) {
    this.isProxyEnabled = isProxyEnabled;
  }

  public DateTimeConfigDto getDateTimconfig() {
    return dateTimconfig;
  }

  public void setDateTimconfig(final DateTimeConfigDto dateTimconfig) {
    this.dateTimconfig = dateTimconfig;
  }

  private List<FolderDto> folderList;
  private ProjectDetailsDto projectDetails;
  private ProxySettingDto proxySettingDto;
  private PropertyDto propertyDto;
  private List<SoapServiceVirtualizeDto> soapServiceVirtualizeDtolist;
  private List<RestServiceVirtualizeDto> restServiceVirtualizeDtolist;
  private List<ServiceTestDetailsDto> serviceTestDetails;
  private boolean isProxyEnabled;
  private DateTimeConfigDto dateTimconfig;

  public UserDefinedProjectDetailsDto() {
    soapServiceVirtualizeDtolist = new ArrayList<SoapServiceVirtualizeDto>();
    restServiceVirtualizeDtolist = new ArrayList<RestServiceVirtualizeDto>();
    serviceTestDetails = new ArrayList<ServiceTestDetailsDto>();
    folderList = new ArrayList<FolderDto>();
    dateTimconfig = new DateTimeConfigDto();
    projectDetails = new ProjectDetailsDto();
    propertyDto = new PropertyDto();
  }

  public static UserDefinedProjectDetailsDto getUserDefinedProjectDetailsDto(
      final MiddleWareTestingTool msstObj) throws IOException, PropertyAlreadyExistsException {
    UserDefinedProjectDetailsDto userDto = new UserDefinedProjectDetailsDto();
    if (isNotNull(msstObj.getProxySettings())) {
      userDto
          .setProxySettingDto(ProxySettingDto.getProxySetingFromJaxb((msstObj.getProxySettings())));
      userDto.setProxyEnabled(true);
    } else {
      userDto.setProxyEnabled(false);
    }
    if (isNotNull(msstObj.getFolderList())) {
      userDto.setFolderList(getMyFolderList(msstObj.getFolderList().getFolder()));
    }
    if (isNotEmpty(msstObj.getServiceVirtualization().getRestServicesList().getRestService())) {
      userDto.setRestServiceVirtualizeDtolist(getRestVirtual(msstObj.getServiceVirtualization()
          .getRestServicesList().getRestService()));
    }
    if (isNotNull(msstObj.getServiceVirtualization().getSoapServicesOverHttpList()
        .getSoapServiceOverHttp())) {
      userDto.setSoapServiceVirtualizeDtolist(getSoapVirtual(msstObj.getServiceVirtualization()
          .getSoapServicesOverHttpList().getSoapServiceOverHttp()));
    }
    userDto.setProjectDetails(ProjectDetailsDto.getProjectDetailsDtoFromJaxb(msstObj
        .getProjectDetails()));
    if (isNotNull(msstObj.getRootLevelPropertiesList().getPropertyDetails())) {
      userDto.setPropertyDto(getMyPropertyDto(msstObj.getRootLevelPropertiesList()
          .getPropertyDetails()));
    }
    if (isNotNull(msstObj.getServiceTestList().getServiceTestDetails())) {
      userDto.setServiceTestDetails(ServiceTestDetailsDto.getserviceTestDetailsDtoFromJaxb(msstObj
          .getServiceTestList().getServiceTestDetails()));
    }
    userDto.setDateTimconfig(DateTimeConfigDto.getDateTimeConfigDtofromJaxb(msstObj
        .getDateTimeConfig()));
    return userDto;
  }

  private static PropertyDto getMyPropertyDto(final List<PropertyMapping> propertyDetails)
      throws PropertyAlreadyExistsException {
    PropertyDto propDot = new PropertyDto();
    propDot.setLevel("Root");
    for (PropertyMapping propertyMapping : propertyDetails) {
      propDot.addProperty(propertyMapping.getPropertyKey(), propertyMapping.getPropetyValue());
    }
    return propDot;
  }

  private static List<SoapServiceVirtualizeDto> getSoapVirtual(
      final List<SoapServiceOverHttpDetails> soapServiceOverHttplst) {
    List<SoapServiceVirtualizeDto> soapSerLst = new ArrayList<SoapServiceVirtualizeDto>();
    for (SoapServiceOverHttpDetails soapHttp : soapServiceOverHttplst) {
      soapSerLst.add(SoapServiceVirtualizeDto.getSoapServiceFromJaxb(soapHttp));
    }
    return soapSerLst;
  }

  private static List<RestServiceVirtualizeDto> getRestVirtual(
      final List<RestServiceDetails> restServiceLst) {
    List<RestServiceVirtualizeDto> restList = new ArrayList<RestServiceVirtualizeDto>();
    for (RestServiceDetails restServiceVirtualizeDto : restServiceLst) {
      restList.add(RestServiceVirtualizeDto.getRestVirtualizeDto(restServiceVirtualizeDto));

    }
    return restList;
  }

  private static List<FolderDto> getMyFolderList(final List<FolderDetails> list) {
    List<FolderDto> fldDtolst = new ArrayList<FolderDto>();
    for (FolderDetails foldDtl : list) {
      fldDtolst.add(FolderDto.getFolderDtoFromJaxb(foldDtl));
    }
    return fldDtolst;
  }

  public static MiddleWareTestingTool getMiddleWareTesting(final UserDefinedProjectDetailsDto usrDto)
      throws IOException {
    MiddleWareTestingTool msstObj = new MiddleWareTestingTool();
    msstObj.setDateTimeConfig(DateTimeConfigDto.getDateTimeConfig(usrDto.getDateTimconfig()));
    msstObj.setFolderList(getMyFolderDetailsList(usrDto.getFolderList()));

    if (isNotNull(usrDto.getProxySettingDto())) {
      msstObj.setProxySettings(ProxySettingDto.getProxySeting(usrDto.getProxySettingDto()));
    }
    msstObj.setProjectDetails(ProjectDetailsDto.getProjectDetailsDto(usrDto.getProjectDetails()));

    msstObj.setRootLevelPropertiesList(getProperties(usrDto.getPropertyDto()));
    msstObj.setServiceTestList(ServiceTestDetailsDto.getServiceDetails(usrDto
        .getServiceTestDetails()));
    ServiceVirtualization svlst = new ServiceVirtualization();
    svlst.setSoapServicesOverHttpList(getSoapSvLst(usrDto.getSoapServiceVirtualizeDtolist()));
    svlst.setRestServicesList(getRestSvlst(usrDto.getRestServiceVirtualizeDtolist()));
    msstObj.setServiceVirtualization(svlst);
    return msstObj;

  }

  private static RestServicesList getRestSvlst(
      final List<RestServiceVirtualizeDto> restServiceVirtualizeDtolist2) {
    RestServicesList rstlst = new RestServicesList();
    if (isNotEmpty(restServiceVirtualizeDtolist2)) {
      List<RestServiceDetails> rstsvlst = new ArrayList<RestServiceDetails>();
      for (RestServiceVirtualizeDto restServiceVirtualizeDto : restServiceVirtualizeDtolist2) {
        rstsvlst.add(RestServiceVirtualizeDto.getRestService(restServiceVirtualizeDto));
      }
      rstlst.getRestService().addAll(rstsvlst);
    }
    return rstlst;
  }

  private static SoapServicesOverHttpList getSoapSvLst(
      final List<SoapServiceVirtualizeDto> soapServiceVirtualizeDtolist2) {
    SoapServicesOverHttpList soapServiceLst = new SoapServicesOverHttpList();
    if (isNotEmpty(soapServiceVirtualizeDtolist2)) {
      List<SoapServiceOverHttpDetails> soapSvlst = new ArrayList<SoapServiceOverHttpDetails>();
      for (SoapServiceVirtualizeDto soapServiceVirtualizeDto : soapServiceVirtualizeDtolist2) {
        soapSvlst.add(SoapServiceVirtualizeDto.getSoapService(soapServiceVirtualizeDto));
      }
      soapServiceLst.getSoapServiceOverHttp().addAll(soapSvlst);
    }
    return soapServiceLst;
  }

  private static RootLevelPropertiesList getProperties(final PropertyDto propertyDto2) {
    RootLevelPropertiesList rtlvlProp = new RootLevelPropertiesList();
    if (isNotEmpty(propertyDto2.getPropertyMap())) {
      List<PropertyMapping> propMpnglst = new ArrayList<PropertyMapping>();
      for (Map.Entry<String, String> entryty : propertyDto2.getPropertyMap().entrySet()) {
        PropertyMapping propMpg = new PropertyMapping();
        propMpg.setPropertyKey(entryty.getKey());
        propMpg.setPropetyValue(entryty.getValue());
        propMpnglst.add(propMpg);
      }
      rtlvlProp.getPropertyDetails().addAll(propMpnglst);
    }
    return rtlvlProp;
  }

  private static FolderList getMyFolderDetailsList(final List<FolderDto> folderList2) {
    FolderList fldrLst = new FolderList();
    if (isNotEmpty(folderList2)) {
      List<FolderDetails> fldDtl = new ArrayList<FolderDetails>();
      for (FolderDto folderDto : folderList2) {
        fldDtl.add(FolderDto.getFolderDtoFrom(folderDto));
      }
      fldrLst.getFolder().addAll(fldDtl);
    }
    return fldrLst;
  }
}
