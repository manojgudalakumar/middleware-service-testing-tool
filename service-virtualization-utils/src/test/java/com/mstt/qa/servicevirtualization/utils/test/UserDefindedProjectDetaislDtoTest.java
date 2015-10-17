package com.mstt.qa.servicevirtualization.utils.test;

import java.io.IOException;

import javax.xml.bind.JAXBException;

import junit.framework.TestCase;

import org.junit.Test;

import com.mstt.qa.servicevirtualization.MiddleWareTestingTool;
import com.mstt.qa.servicevirtualization.servicevirtualizationutils.dto.UserDefinedProjectDetailsDto;
import com.mstt.qa.servicevirtualization.servicevirtualizationutils.exceptions.PropertyAlreadyExistsException;
import com.mstt.qa.servicevirtualization.servicevirtualizationutils.userproject.HandleProjectServiceData;
import com.mstt.qa.servicevirtualization.servicevirtualizationutils.userproject.LoadAndSaveProjectXml;

public class UserDefindedProjectDetaislDtoTest extends TestCase {
  MiddleWareTestingTool msttObj;
  MiddleWareTestingTool convertedmsttObj;

  HandleProjectServiceData handleProjectServiceData;
  UserDefinedProjectDetailsDto userDefindedPrjctDto;
  UserDefinedProjectDetailsDto convertedUserDefindedPrjctDto;

  @Override
  public void setUp() throws JAXBException, IOException, PropertyAlreadyExistsException {
    msttObj = LoadAndSaveProjectXml.getMsttObject("src\\test\\resources\\NewFile1.xml");
    handleProjectServiceData = new HandleProjectServiceData();
    userDefindedPrjctDto = UserDefinedProjectDetailsDto.getUserDefinedProjectDetailsDto(msttObj);
  }

  @Test
  public void testFullConversion() throws IOException, PropertyAlreadyExistsException {
    convertedUserDefindedPrjctDto =
        UserDefinedProjectDetailsDto.getUserDefinedProjectDetailsDto(msttObj);
    convertedmsttObj = UserDefinedProjectDetailsDto.getMiddleWareTesting(userDefindedPrjctDto);

  }

  @Test
  public void testFullConversion3a() throws IOException, PropertyAlreadyExistsException {
    convertedUserDefindedPrjctDto =
        UserDefinedProjectDetailsDto.getUserDefinedProjectDetailsDto(msttObj);
    convertedmsttObj = UserDefinedProjectDetailsDto.getMiddleWareTesting(userDefindedPrjctDto);

  }

  @Test
  public void testFullConversion3aba() throws IOException, PropertyAlreadyExistsException {
    convertedUserDefindedPrjctDto =
        UserDefinedProjectDetailsDto.getUserDefinedProjectDetailsDto(msttObj);
    convertedmsttObj = UserDefinedProjectDetailsDto.getMiddleWareTesting(userDefindedPrjctDto);

  }

  @Test
  public void testGeneratedXmlFrommsttObj() throws IOException, JAXBException {
    // convertedUserDefindedPrjctDto = UserDefinedProjectDetailsDto
    // .getUserDefinedProjectDetailsDto(msttObj);
    convertedmsttObj = UserDefinedProjectDetailsDto.getMiddleWareTesting(userDefindedPrjctDto);
    LoadAndSaveProjectXml.saveProjectXml("target", userDefindedPrjctDto);
    LoadAndSaveProjectXml.saveProjectXmlWithFullPath("target\\adef.mstt", userDefindedPrjctDto);
  }

  @Test
  public void testGeneratedXmlNewUserObj() throws IOException, JAXBException,
      PropertyAlreadyExistsException {
    UserDefinedProjectDetailsDto usr = new UserDefinedProjectDetailsDto();
    MiddleWareTestingTool conpkrmObj = new MiddleWareTestingTool();
    convertedmsttObj = UserDefinedProjectDetailsDto.getMiddleWareTesting(usr);
    UserDefinedProjectDetailsDto convertedUserObj =
        UserDefinedProjectDetailsDto.getUserDefinedProjectDetailsDto(convertedmsttObj);
    LoadAndSaveProjectXml.saveProjectXmlWithFullPath("target\\saveProjectXmlWithFullPath1.mstt",
        convertedUserObj);
    LoadAndSaveProjectXml.saveProjectXmlWithFullPath("target\\testGeneratedXmlNewUserObj.mstt",
        convertedUserObj);
    conpkrmObj = LoadAndSaveProjectXml.getMsttObject("target\\testGeneratedXmlNewUserObj.mstt");
    convertedUserObj = UserDefinedProjectDetailsDto.getUserDefinedProjectDetailsDto(conpkrmObj);
    LoadAndSaveProjectXml.saveProjectXmlWithFullPath("target\\testGeneratedXmlNewUserObj2.mstt",
        convertedUserObj);
  }

  public static UserDefinedProjectDetailsDto getPopulatedUsrDefinedObj() throws JAXBException,
      IOException, PropertyAlreadyExistsException {
    MiddleWareTestingTool msttObj1 =
        LoadAndSaveProjectXml.getMsttObject("src\\test\\resources\\NewFile1.xml");
    return UserDefinedProjectDetailsDto.getUserDefinedProjectDetailsDto(msttObj1);
  }
}
