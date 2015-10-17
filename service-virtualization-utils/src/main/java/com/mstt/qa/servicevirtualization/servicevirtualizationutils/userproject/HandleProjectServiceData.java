package com.mstt.qa.servicevirtualization.servicevirtualizationutils.userproject;

import static com.mstt.qa.servicevirtualization.servicevirtualizationutils.utils.CommonMethodsUtils.isNotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mstt.qa.servicevirtualization.servicevirtualizationutils.dto.RestServiceVirtualizeDto;
import com.mstt.qa.servicevirtualization.servicevirtualizationutils.dto.ServiceTestDetailsDto;
import com.mstt.qa.servicevirtualization.servicevirtualizationutils.dto.SoapServiceVirtualizeDto;
import com.mstt.qa.servicevirtualization.servicevirtualizationutils.dto.UserDefinedProjectDetailsDto;
import com.mstt.qa.servicevirtualization.servicevirtualizationutils.exceptions.InvalidReferenceIdException;
import com.mstt.qa.servicevirtualization.servicevirtualizationutils.exceptions.NoParentExistsException;

public class HandleProjectServiceData {
  ServiceTestDetailsDto st = null;

  public ServiceTestDetailsDto getServiceTestDetails(
      final UserDefinedProjectDetailsDto userDefinedDto, final String referenceNumber) {

    return getServiceTest(userDefinedDto.getServiceTestDetails(), referenceNumber);
  }

  public ServiceTestDetailsDto getParentServiceTestDetails(
      final UserDefinedProjectDetailsDto userDefinedDto,
      final ServiceTestDetailsDto serviceTestDetails) throws NoParentExistsException {
    if (serviceTestDetails.getServiceTestParentReferenceId() == null) {
      throw new NoParentExistsException("There is no parent for test"
          + serviceTestDetails.getServiceTestName());
    }

    ServiceTestDetailsDto st =
        getServiceTest(userDefinedDto.getServiceTestDetails(),
            serviceTestDetails.getServiceTestParentReferenceId());
    return st;
  }

  public Map<String, String> getCombinedProperties(final String referenceId,
      final UserDefinedProjectDetailsDto usrDto) throws InvalidReferenceIdException {
    Map<String, String> mapUser = new HashMap<String, String>();
    ServiceTestDetailsDto srvcDto = getServiceTestDetails(usrDto, referenceId);
    if (isNotNull(srvcDto)) {
      for (; srvcDto.getServiceTestParentReferenceId() == null;) {
        if (isNotNull(srvcDto.getPropertyDto())) {
          mapUser.putAll(srvcDto.getPropertyDto().getPropertyMap());
        }
        srvcDto = getServiceTestDetails(usrDto, srvcDto.getServiceTestParentReferenceId());
      }
      return mapUser;
    } else {
      throw new InvalidReferenceIdException(referenceId);
    }
  }

  private ServiceTestDetailsDto getServiceTest(final List<ServiceTestDetailsDto> list,
      final String referenceNumber) {
    for (ServiceTestDetailsDto serviceTestDetails : list) {
      System.out.println("the ref number is " + serviceTestDetails.getServiceTestReferenceId());
      if (referenceNumber.equalsIgnoreCase(serviceTestDetails.getServiceTestReferenceId())) {
        st = serviceTestDetails;
        break;
      }
      if (serviceTestDetails.getServiceTestChildDetailslist().size() == 0) {
        continue;

      }
      getServiceTest(serviceTestDetails.getServiceTestChildDetailslist(), referenceNumber);
    }
    return st;
  }

  public Object getUserObject(final String referenceId, final UserDefinedProjectDetailsDto usrDto) {
    Object obj = checkInSoapVirtualList(referenceId, usrDto.getSoapServiceVirtualizeDtolist());
    if (obj != null) {
      return obj;
    }
    obj = checkInRestvirtualList(referenceId, usrDto.getRestServiceVirtualizeDtolist());
    if (obj != null) {
      return obj;
    }
    obj = checkInServiceTestDetailList(referenceId, usrDto.getServiceTestDetails());
    return obj;
  }

  private Object checkInServiceTestDetailList(final String referenceId,
      final List<ServiceTestDetailsDto> soapServiceVirtualizeDtolist) {
    return getServiceTest(soapServiceVirtualizeDtolist, referenceId);
  }

  private static Object checkInRestvirtualList(final String referenceId,
      final List<RestServiceVirtualizeDto> restServiceVirtualizeDtolist) {
    for (RestServiceVirtualizeDto restVirtualizeDto : restServiceVirtualizeDtolist) {
      if (restVirtualizeDto.getRestVirtualReferenceId().equals(referenceId)) {
        return restVirtualizeDto;
      }
    }
    return null;
  }

  private static Object checkInSoapVirtualList(final String referenceId,
      final List<SoapServiceVirtualizeDto> list) {
    for (SoapServiceVirtualizeDto soapServiceVirtualizeDto : list) {
      if (soapServiceVirtualizeDto.getSoapVirtualReferenceId().equals(referenceId)) {
        return soapServiceVirtualizeDto;
      }
    }
    return null;
  }

}
