package com.mstt.qa.servicevirtualization.uicomponent.utils;

import com.github.tomakehurst.wiremock.common.BinaryFile;
import com.github.tomakehurst.wiremock.common.FileSource;
import com.github.tomakehurst.wiremock.common.IdGenerator;
import com.github.tomakehurst.wiremock.common.Json;
import com.github.tomakehurst.wiremock.common.UniqueFilenameGenerator;
import com.github.tomakehurst.wiremock.common.VeryShortIdGenerator;
import com.github.tomakehurst.wiremock.stubbing.StubMapping;
import com.mstt.qa.servicevirtualization.servicevirtualizationutils.dto.MsttRequestDefinition;
import com.mstt.qa.servicevirtualization.servicevirtualizationutils.dto.MsttResponseDefinition;
import com.mstt.qa.servicevirtualization.utils.StaticValues;

public class MsttStubHandler {
  private final FileSource mappingsFileSource;
  private final FileSource filesFileSource;
  private final IdGenerator idGenerator;

  public MsttStubHandler(final FileSource mappingsFileSource, final FileSource filesFileSource) {
    this.mappingsFileSource = mappingsFileSource;
    this.filesFileSource = filesFileSource;
    idGenerator = new VeryShortIdGenerator();
  }

  public String storeStub(final MsttRequestDefinition msttReqPattern,
      final MsttResponseDefinition msttResponseDefination, final String serviceType) {
    String fileId = idGenerator.generate();
    String mappingFileName =
        UniqueFilenameGenerator.generateIdFromUrl(StaticValues.RECORD_MAPPING_FILE_PREFIX, fileId,
            msttReqPattern.getUrl());
    String bodyFileName =
        UniqueFilenameGenerator.generateIdFromUrl(StaticValues.RECORD_BODY_FILE_PREFIX, fileId,
            msttReqPattern.getUrl());
    StubMapping stubmapping =
        ConvertMsttToWireMockStub.convertToStubMapping(msttReqPattern, msttResponseDefination,
            serviceType, msttResponseDefination.getBodyFileName());
    wirteToBodyFile(msttResponseDefination.getResponse(), bodyFileName);
    stubmapping.getResponse().setBodyFileName(bodyFileName);
    wirteToMappingFile(stubmapping, mappingFileName);
    return mappingFileName;
  }

  public MsttRequestDefinition readRequestDefinitionFromMappingFile(
      final String virutalMappingFileName) {
    if (virutalMappingFileName == null) {
      return new MsttRequestDefinition();
    } else {
      String fileContent = getFileContent(virutalMappingFileName);
      StubMapping stubMapping = Json.read(fileContent, StubMapping.class);
      return ConvertWireMockStubToMsttStub.getRequestDefination(stubMapping.getRequest(),
          stubMapping.getPriority());
    }

  }

  private String getFileContent(final String virutalMappingFileName) {
    BinaryFile bny = mappingsFileSource.getBinaryFileNamed(virutalMappingFileName);
    return new String(bny.readContents());

  }

  public MsttResponseDefinition readResponseDefinitionFromMappingFile(
      final String virutalMappingFileName) {
    if (virutalMappingFileName == null) {
      return new MsttResponseDefinition();
    } else {
      String fileContent = getFileContent(virutalMappingFileName);
      StubMapping stubMapping = Json.read(fileContent, StubMapping.class);
      return ConvertWireMockStubToMsttStub.getResponeDefination(stubMapping.getResponse());
    }
  }

  public String storeStub(final MsttRequestDefinition msttReqPattern,
      final MsttResponseDefinition msttResponseDefination, final String serviceType,
      final String mappingFileName) {
    StubMapping stubmapping =
        ConvertMsttToWireMockStub.convertToStubMapping(msttReqPattern, msttResponseDefination,
            serviceType, msttResponseDefination.getBodyFileName());
    wirteToMappingFile(stubmapping, mappingFileName);
    wirteToBodyFile(msttResponseDefination.getResponse(),
        msttResponseDefination.getBodyFileName());
    return mappingFileName;
  }

  private void wirteToMappingFile(final StubMapping mapping, final String mappingFileName) {
    mappingsFileSource.writeTextFile(mappingFileName, Json.write(mapping));
  }

  private void wirteToBodyFile(final String responseBody, final String bodyFileName) {
    filesFileSource.writeTextFile(bodyFileName, responseBody);
  }
}
