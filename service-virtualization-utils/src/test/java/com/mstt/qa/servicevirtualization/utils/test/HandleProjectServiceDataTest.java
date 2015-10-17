package com.mstt.qa.servicevirtualization.utils.test;

import java.io.IOException;

import javax.xml.bind.JAXBException;

import junit.framework.TestCase;

import org.junit.Test;

import com.mstt.qa.servicevirtualization.MiddleWareTestingTool;
import com.mstt.qa.servicevirtualization.servicevirtualizationutils.dto.ServiceTestDetailsDto;
import com.mstt.qa.servicevirtualization.servicevirtualizationutils.dto.UserDefinedProjectDetailsDto;
import com.mstt.qa.servicevirtualization.servicevirtualizationutils.exceptions.PropertyAlreadyExistsException;
import com.mstt.qa.servicevirtualization.servicevirtualizationutils.userproject.HandleProjectServiceData;
import com.mstt.qa.servicevirtualization.servicevirtualizationutils.userproject.LoadAndSaveProjectXml;

public class HandleProjectServiceDataTest extends TestCase {
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
  public void testServiceDetailsBasedUponReferenceNumber() throws JAXBException {
    ServiceTestDetailsDto st =
        handleProjectServiceData.getServiceTestDetails(userDefindedPrjctDto, "1");
    assertEquals(st.getServiceTestName(), "service-test-name-1");
    assertEquals(st.getServiceTestReferenceId(), "1");
  }

  @Test
  public void testServiceDetailsBasedUponReferenceNumber1a() throws JAXBException {
    ServiceTestDetailsDto st =
        handleProjectServiceData.getServiceTestDetails(userDefindedPrjctDto, "1a");
    assertEquals(st.getServiceTestName(), "service-test-name-1a");
    assertEquals(st.getServiceTestReferenceId(), "1a");
  }

  @Test
  public void testServiceDetailsBasedUponReferenceNumber2() throws JAXBException {
    ServiceTestDetailsDto st =
        handleProjectServiceData.getServiceTestDetails(userDefindedPrjctDto, "2");
    assertEquals(st.getServiceTestName(), "service-test-name-2");
    assertEquals(st.getServiceTestReferenceId(), "2");
  }

  @Test
  public void testServiceDetailsBasedUponReferenceNumber2a() throws JAXBException {
    ServiceTestDetailsDto st =
        handleProjectServiceData.getServiceTestDetails(userDefindedPrjctDto, "2a");
    assertEquals(st.getServiceTestName(), "service-test-name-2a");
    assertEquals(st.getServiceTestReferenceId(), "2a");
  }

  @Test
  public void testServiceDetailsBasedUponReferenceNumber2b() throws JAXBException {
    ServiceTestDetailsDto st =
        handleProjectServiceData.getServiceTestDetails(userDefindedPrjctDto, "2b");
    assertEquals(st.getServiceTestName(), "service-test-name-2b");
    assertEquals(st.getServiceTestReferenceId(), "2b");
  }

  public void testServiceDetailsBasedUponReferenceNumber3() throws JAXBException {
    ServiceTestDetailsDto st =
        handleProjectServiceData.getServiceTestDetails(userDefindedPrjctDto, "3");
    assertEquals(st.getServiceTestName(), "service-test-name-3");
    assertEquals(st.getServiceTestReferenceId(), "3");
  }

  @Test
  public void testServiceDetailsBasedUponReferenceNumber3a() throws JAXBException {
    ServiceTestDetailsDto st =
        handleProjectServiceData.getServiceTestDetails(userDefindedPrjctDto, "3a");
    assertEquals(st.getServiceTestName(), "service-test-name-3a");
    assertEquals(st.getServiceTestReferenceId(), "3a");
  }

  @Test
  public void testServiceDetailsBasedUponReferenceNumber3aa() throws JAXBException {
    ServiceTestDetailsDto st =
        handleProjectServiceData.getServiceTestDetails(userDefindedPrjctDto, "3aa");
    assertEquals(st.getServiceTestName(), "service-test-name-3aa");
    assertEquals(st.getServiceTestReferenceId(), "3aa");
  }

  @Test
  public void testServiceDetailsBasedUponReferenceNumber3ab() throws JAXBException {
    ServiceTestDetailsDto st =
        handleProjectServiceData.getServiceTestDetails(userDefindedPrjctDto, "3ab");
    assertEquals(st.getServiceTestName(), "service-test-name-3ab");
    assertEquals(st.getServiceTestReferenceId(), "3ab");
  }

  @Test
  public void testServiceDetailsBasedUponReferenceNumber3b() throws JAXBException {
    ServiceTestDetailsDto st =
        handleProjectServiceData.getServiceTestDetails(userDefindedPrjctDto, "3b");
    assertEquals(st.getServiceTestName(), "service-test-name-3b");
    assertEquals(st.getServiceTestReferenceId(), "3b");
  }

  @Test
  public void testServiceDetailsBasedUponReferenceNumber3ba() throws JAXBException {
    ServiceTestDetailsDto st =
        handleProjectServiceData.getServiceTestDetails(userDefindedPrjctDto, "3ba");
    assertEquals(st.getServiceTestName(), "service-test-name-3ba");
    assertEquals(st.getServiceTestReferenceId(), "3ba");
  }

}
