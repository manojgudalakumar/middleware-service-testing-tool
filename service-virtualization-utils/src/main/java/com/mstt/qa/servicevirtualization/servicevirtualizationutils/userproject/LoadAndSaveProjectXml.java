package com.mstt.qa.servicevirtualization.servicevirtualizationutils.userproject;

import java.io.File;
import java.io.IOException;

import javax.xml.bind.JAXBException;

import org.apache.commons.io.FileUtils;

import com.mstt.qa.servicevirtualization.MiddleWareTestingTool;
import com.mstt.qa.servicevirtualization.servicevirtualizationutils.dto.UserDefinedProjectDetailsDto;
import com.mstt.qa.servicevirtualization.servicevirtualizationutils.exceptions.PropertyAlreadyExistsException;
import com.mstt.qa.servicevirtualization.servicevirtualizationutils.utils.ConstantsUtils;

public final class LoadAndSaveProjectXml extends HandleProjectXmlJaxb {
  public static MiddleWareTestingTool getMsttObject(final String filepath) throws JAXBException {
    return HandleProjectXmlJaxb.getJavaObjectFromXmlFile(filepath);
  }

  public static UserDefinedProjectDetailsDto getMsttObject(final File file) throws JAXBException,
      IOException, PropertyAlreadyExistsException {
    MiddleWareTestingTool msttObj = HandleProjectXmlJaxb.getJavaObjectFromXmlFile(file);
    return UserDefinedProjectDetailsDto.getUserDefinedProjectDetailsDto(msttObj);
  }

  public UserDefinedProjectDetailsDto getMsttObjectFromString(final String xmlContent)
      throws JAXBException, IOException, PropertyAlreadyExistsException {
    MiddleWareTestingTool msttObj = HandleProjectXmlJaxb.getJavaObjectFromXmlString(xmlContent);
    return UserDefinedProjectDetailsDto.getUserDefinedProjectDetailsDto(msttObj);
  }

  public static void saveProjectXml(final String filePath, final UserDefinedProjectDetailsDto usrDto)
      throws IOException, JAXBException {
    File file =
        new File(filePath + "\\" + usrDto.getProjectDetails().getProjectName()
            + ConstantsUtils.PROJECT_FILE_EXTENSION);
    MiddleWareTestingTool msttObj = UserDefinedProjectDetailsDto.getMiddleWareTesting(usrDto);
    FileUtils.writeStringToFile(file, HandleProjectXmlJaxb.getXmlFromMsttObject(msttObj));
  }

  public static void saveProjectXmlWithFullPath(final String filePath,
      final UserDefinedProjectDetailsDto usrDto) throws IOException, JAXBException {
    File file = new File(filePath);
    MiddleWareTestingTool msttObj = UserDefinedProjectDetailsDto.getMiddleWareTesting(usrDto);
    FileUtils.writeStringToFile(file, HandleProjectXmlJaxb.getXmlFromMsttObject(msttObj));
  }
}
