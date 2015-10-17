package com.mstt.qa.servicevirtualization.utils.test;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;

import javax.xml.bind.JAXBException;

import org.junit.Before;
import org.junit.Test;

import com.mstt.qa.servicevirtualization.MiddleWareTestingTool;
import com.mstt.qa.servicevirtualization.servicevirtualizationutils.dto.PropertyDto;
import com.mstt.qa.servicevirtualization.servicevirtualizationutils.dto.ServiceTestDetailsDto;
import com.mstt.qa.servicevirtualization.servicevirtualizationutils.dto.UserDefinedProjectDetailsDto;
import com.mstt.qa.servicevirtualization.servicevirtualizationutils.exceptions.InvalidReferenceIdException;
import com.mstt.qa.servicevirtualization.servicevirtualizationutils.exceptions.PropertyAlreadyExistsException;
import com.mstt.qa.servicevirtualization.servicevirtualizationutils.userproject.HandleProjectServiceData;
import com.mstt.qa.servicevirtualization.servicevirtualizationutils.userproject.LoadAndSaveProjectXml;

public class CombinedPropsTest {
  MiddleWareTestingTool msttObj;
  HandleProjectServiceData handleProjectServiceData;
  UserDefinedProjectDetailsDto userDefindedPrjctDto;
  ServiceTestDetailsDto st;
  PropertyDto propdto;

  @Before
  public void Beforetest() throws JAXBException, IOException, PropertyAlreadyExistsException {
    msttObj = LoadAndSaveProjectXml.getMsttObject("src\\test\\resources\\NewFile1.xml");
    handleProjectServiceData = new HandleProjectServiceData();
    userDefindedPrjctDto = UserDefinedProjectDetailsDto.getUserDefinedProjectDetailsDto(msttObj);
    propdto = new PropertyDto();
    propdto.addProperty("adcd", "werr");
    propdto.addProperty("adcd1", "werr");
    propdto.addProperty("adcd2", "werr");
    propdto.addProperty("adcd3", "werr");
    st =
        new ServiceTestDetailsDto("abcds", "def", Calendar.getInstance(), Calendar.getInstance(),
            "3bb", propdto, "serviceTestEndPointUrl", "serviceTestWsdlUrl",
            "serviceTestInformation", new ArrayList<String>(), null, null, null);

  }

  @Test
  public void testServiceDetailsCombinedPropties() throws JAXBException,
      InvalidReferenceIdException {
    Map<String, String> mapUsr =
        handleProjectServiceData.getCombinedProperties("3ab", userDefindedPrjctDto);
    assertEquals(mapUsr.size(), 4);
    // assertEquals(st.getServiceTestReferenceId(), "3ab");
  }

  @Test
  public void testServiceDetailsCombinedPropties3b() throws JAXBException,
      InvalidReferenceIdException {
    Map<String, String> mapUsr =
        handleProjectServiceData.getCombinedProperties("3b", userDefindedPrjctDto);
    assertEquals(mapUsr.size(), 3);
    // assertEquals(st.getServiceTestReferenceId(), "3ab");
  }

  @Test
  public void testServiceDetailsCombinedPropties3bb() throws JAXBException,
      InvalidReferenceIdException {
    Map<String, String> mapUsr =
        handleProjectServiceData.getCombinedProperties("3bb", userDefindedPrjctDto);
    assertEquals(mapUsr.size(), 1);
    // assertEquals(st.getServiceTestReferenceId(), "3ab");
  }

  @Test
  public void testServiceDetailsCombinedPropties3() throws JAXBException,
      InvalidReferenceIdException {
    Map<String, String> mapUsr =
        handleProjectServiceData.getCombinedProperties("3", userDefindedPrjctDto);
    assertEquals(mapUsr.size(), 2);
    // assertEquals(st.getServiceTestReferenceId(), "3ab");
  }

  @Test
  public void testServiceDetailsCombinedProptiesbyAddig3() throws JAXBException,
      InvalidReferenceIdException, PropertyAlreadyExistsException {
    Map<String, String> mapUsr =
        handleProjectServiceData.getCombinedProperties("3", userDefindedPrjctDto);
    assertEquals(mapUsr.size(), 2);
    ServiceTestDetailsDto dts =
        handleProjectServiceData.getServiceTestDetails(userDefindedPrjctDto, "3");
    dts.getPropertyDto().addProperty("test1", "test2");
    Map<String, String> mapUsr1 =
        handleProjectServiceData.getCombinedProperties("3", userDefindedPrjctDto);
    dts.setServiceTestName("Changed service name");
    ServiceTestDetailsDto dts1 =
        handleProjectServiceData.getServiceTestDetails(userDefindedPrjctDto, "3");
    assertEquals(3, mapUsr1.size());
    assertEquals(dts1.getServiceTestName(), "Changed service name");
    // assertEquals(st.getServiceTestReferenceId(), "3ab");
  }

}
