package com.mstt.qa.servicevirtualization.utils.test;

import java.io.IOException;
import java.util.List;

import javax.xml.bind.JAXBException;

import junit.framework.TestCase;

import org.junit.Test;

import com.mstt.qa.servicevirtualization.MiddleWareTestingTool;
import com.mstt.qa.servicevirtualization.MiddleWareTestingTool.ServiceTestList;
import com.mstt.qa.servicevirtualization.ServiceTestDetails;
import com.mstt.qa.servicevirtualization.servicevirtualizationutils.dto.ServiceTestDetailsDto;
import com.mstt.qa.servicevirtualization.servicevirtualizationutils.dto.UserDefinedProjectDetailsDto;
import com.mstt.qa.servicevirtualization.servicevirtualizationutils.exceptions.PropertyAlreadyExistsException;
import com.mstt.qa.servicevirtualization.servicevirtualizationutils.userproject.HandleProjectServiceData;
import com.mstt.qa.servicevirtualization.servicevirtualizationutils.userproject.LoadAndSaveProjectXml;

public class ServiceTestDetailsDtoTest extends TestCase {
  MiddleWareTestingTool msttObj;
  HandleProjectServiceData handleProjectServiceData;
  UserDefinedProjectDetailsDto userDefindedPrjctDto;

  @Override
  public void setUp() throws JAXBException, IOException, PropertyAlreadyExistsException {
    msttObj = LoadAndSaveProjectXml.getMsttObject("src\\test\\resources\\NewFile1.xml");
    handleProjectServiceData = new HandleProjectServiceData();
    userDefindedPrjctDto = UserDefinedProjectDetailsDto.getUserDefinedProjectDetailsDto(msttObj);
  }

  @Test
  public void testServiceDetailsBasedUponReferenceNumber() throws IOException,
      PropertyAlreadyExistsException {
    UserDefinedProjectDetailsDto usd =
        UserDefinedProjectDetailsDto.getUserDefinedProjectDetailsDto(msttObj);
    assertEquals("proxy-hostname", usd.getProxySettingDto().getProxyHostName());
  }

  @Test
  public void testServiceDetailsBasedUponReferenceNumber3abs() throws JAXBException, IOException {
    System.out.println("the first element is being added is "
        + userDefindedPrjctDto.getServiceTestDetails().get(0).getServiceTestReferenceId());
    for (ServiceTestDetailsDto stDtl : userDefindedPrjctDto.getServiceTestDetails()) {
      System.out.println("the list is being added are" + stDtl.getServiceTestReferenceId());

    }
    ServiceTestDetailsDto st =
        handleProjectServiceData.getServiceTestDetails(userDefindedPrjctDto, "3ab");
    System.out.println("the service test details returned is " + st.getServiceTestReferenceId());
    assertEquals(st.getServiceTestName(), "service-test-name-3ab");
    assertEquals(st.getServiceTestReferenceId(), "3ab");
    ServiceTestList servList =
        ServiceTestDetailsDto.getServiceDetails(userDefindedPrjctDto.getServiceTestDetails());
    System.out.println(servList.toString());
    List<ServiceTestDetails> st1 = servList.getServiceTestDetails();
    System.out.println("the size is " + st1.size());
    for (ServiceTestDetails stDtl : st1) {
      System.out.println(stDtl.getServiceTestName());
      System.out.println("the list is being added are" + stDtl.getServiceTestReferenceNumber());
      for (ServiceTestDetails serviceTestDetails : stDtl.getServiceTestChildDetailsList()
          .getServiceTestChildDetails()) {
        System.out.println(serviceTestDetails.getServiceTestName());
        System.out.println("the list is being added are"
            + serviceTestDetails.getServiceTestReferenceNumber());
      }
    }

  }
}
