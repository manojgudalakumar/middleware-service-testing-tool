package com.mstt.qa.servicevirtualization.servicevirtualizationutils.dto;

import static com.mstt.qa.servicevirtualization.servicevirtualizationutils.utils.CommonMethodsUtils.isNotNull;

import com.mstt.qa.servicevirtualization.ResponsePattern.ResponseDataVerificationsList.ComplianceAgainstSchemaOrWsdl;

public class ResponseComplianceDto {
  public String getSchemaLocalFile() {
    return schemaLocalFile;
  }

  public void setSchemaLocalFile(final String schemaLocalFile) {
    this.schemaLocalFile = schemaLocalFile;
  }

  public String getSchemaHttpUrl() {
    return schemaHttpUrl;
  }

  public void setSchemaHttpUrl(final String schemaHttpUrl) {
    this.schemaHttpUrl = schemaHttpUrl;
  }

  public String getSchemaWsdlUrl() {
    return schemaWsdlUrl;
  }

  public void setSchemaWsdlUrl(final String schemaWsdlUrl) {
    this.schemaWsdlUrl = schemaWsdlUrl;
  }

  private String schemaWsdlUrl;
  private String schemaLocalFile;
  private String schemaHttpUrl;

  public ResponseComplianceDto() {}

  public ComplianceAgainstSchemaOrWsdl getResponseCompliance() {
    ComplianceAgainstSchemaOrWsdl cmpwsdl = new ComplianceAgainstSchemaOrWsdl();
    if (isNotNull(getSchemaWsdlUrl())) {
      cmpwsdl.setValidateAgainstWsdl(getSchemaWsdlUrl());
    } else if (isNotNull(getSchemaHttpUrl())) {
      cmpwsdl.setValidateAgainstSchemaForUrl(getSchemaHttpUrl());
    } else if (isNotNull(getSchemaLocalFile())) {
      cmpwsdl.setValidateAgainstSchemaFromFile(getSchemaLocalFile());
    }

    return cmpwsdl;
  }

  public static ComplianceAgainstSchemaOrWsdl getResponseCompliance(
      final ResponseComplianceDto rsComplianceDto) {
    ComplianceAgainstSchemaOrWsdl cmpwsdl = new ComplianceAgainstSchemaOrWsdl();
    if (isNotNull(rsComplianceDto.getSchemaWsdlUrl())) {
      cmpwsdl.setValidateAgainstWsdl(rsComplianceDto.getSchemaWsdlUrl());
    } else if (isNotNull(rsComplianceDto.getSchemaHttpUrl())) {
      cmpwsdl.setValidateAgainstSchemaForUrl(rsComplianceDto.getSchemaHttpUrl());
    } else if (isNotNull(rsComplianceDto.getSchemaLocalFile())) {
      cmpwsdl.setValidateAgainstSchemaFromFile(rsComplianceDto.getSchemaLocalFile());
    }

    return cmpwsdl;
  }

  public static ResponseComplianceDto getResponseComplianceFromJaxb(
      final ComplianceAgainstSchemaOrWsdl cmpwsdl) {
    ResponseComplianceDto resComplianceDto = new ResponseComplianceDto();
    if (isNotNull(cmpwsdl.getValidateAgainstWsdl())) {
      resComplianceDto.setSchemaWsdlUrl(cmpwsdl.getValidateAgainstWsdl());
    } else if (isNotNull(cmpwsdl.getValidateAgainstSchemaForUrl())) {
      resComplianceDto.setSchemaHttpUrl(cmpwsdl.getValidateAgainstSchemaForUrl());
    } else if (isNotNull(cmpwsdl.getValidateAgainstSchemaFromFile())) {
      resComplianceDto.setSchemaLocalFile(cmpwsdl.getValidateAgainstSchemaFromFile());
    }

    return resComplianceDto;
  }
}
