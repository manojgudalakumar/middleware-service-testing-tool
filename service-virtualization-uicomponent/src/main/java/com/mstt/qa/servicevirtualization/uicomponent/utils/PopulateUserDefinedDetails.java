package com.mstt.qa.servicevirtualization.uicomponent.utils;

import java.io.IOException;

import javax.xml.bind.JAXBException;

import com.mstt.qa.servicevirtualization.MiddleWareTestingTool;
import com.mstt.qa.servicevirtualization.servicevirtualizationutils.dto.UserDefinedProjectDetailsDto;
import com.mstt.qa.servicevirtualization.servicevirtualizationutils.exceptions.PropertyAlreadyExistsException;
import com.mstt.qa.servicevirtualization.servicevirtualizationutils.userproject.LoadAndSaveProjectXml;

public class PopulateUserDefinedDetails {
  public static UserDefinedProjectDetailsDto getPopulatedUsrDefinedObj() throws JAXBException,
      IOException, PropertyAlreadyExistsException {
    MiddleWareTestingTool msttObj1 =
        LoadAndSaveProjectXml.getMsttObject("src\\test\\resources\\NewFile1.xml");
    return UserDefinedProjectDetailsDto.getUserDefinedProjectDetailsDto(msttObj1);
  }
}
