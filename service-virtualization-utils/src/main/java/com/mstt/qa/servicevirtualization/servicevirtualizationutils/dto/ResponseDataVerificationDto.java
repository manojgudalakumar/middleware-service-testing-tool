package com.mstt.qa.servicevirtualization.servicevirtualizationutils.dto;

import static com.mstt.qa.servicevirtualization.servicevirtualizationutils.utils.CommonMethodsUtils.isNotNull;
import static com.mstt.qa.servicevirtualization.servicevirtualizationutils.utils.CommonMethodsUtils.listToString;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.FileUtils;

import com.mstt.qa.servicevirtualization.ResponsePattern.ResponseDataVerificationsList.TotalResponseDataVerification;
import com.mstt.qa.servicevirtualization.ResponsePattern.ResponseDataVerificationsList.TotalResponseDataVerification.CompareResponseAgainstFile;
import com.mstt.qa.servicevirtualization.ResponsePattern.ResponseDataVerificationsList.TotalResponseDataVerification.SubstringVerication;
import com.mstt.qa.servicevirtualization.ResponsePattern.ResponseDataVerificationsList.TotalResponseDataVerification.TotalResponseAsString;

public class ResponseDataVerificationDto {
  public String getResponseData() {
    return responseData;
  }

  public void setResponseData(final String responseData) {
    this.responseData = responseData;
  }

  public String getResponseFilePath() {
    return responseFilePath;
  }

  public void setResponseFilePath(final String responseFilePath) throws IOException {
    this.responseFilePath = responseFilePath;
    responseData = FileUtils.readFileToString(new File(responseFilePath));
    populatedFromFilePath = true;
  }

  public boolean isPopulatedFromFilePath() {
    return populatedFromFilePath;
  }

  public void setPopulatedFromFilePath(final boolean populatedFromFilePath) {
    this.populatedFromFilePath = populatedFromFilePath;
  }

  public String getStartsWith() {
    return startsWith;
  }

  public void setStartsWith(final String startsWith) {
    this.startsWith = startsWith;
  }

  public String getEndsWith() {
    return EndsWith;
  }

  public void setEndsWith(final String endsWith) {
    EndsWith = endsWith;
  }

  public String getContainsString() {
    return containsString;
  }

  public void setContainsString(final String containsString) {
    this.containsString = containsString;
  }

  public List<String> getExclusionTaglist() {
    return exclusionTaglist;
  }

  public void setExclusionTaglist(final List<String> exclusionTaglist) {
    this.exclusionTaglist = exclusionTaglist;
  }

  private String responseData;
  private String responseFilePath;
  private List<String> exclusionTaglist;
  private boolean populatedFromFilePath;
  private String startsWith;
  private String EndsWith;
  private String containsString;

  public ResponseDataVerificationDto() {
    exclusionTaglist = new ArrayList<String>();
  }

  public static ResponseDataVerificationDto responseDataVerificationDtoFromJaxb(
      final TotalResponseDataVerification toResponseDateVerification) throws IOException {
    ResponseDataVerificationDto responseDataVerificationDto = new ResponseDataVerificationDto();
    CompareResponseAgainstFile cpt = toResponseDateVerification.getCompareResponseAgainstFile();
    if (isNotNull(cpt)) {
      responseDataVerificationDto.setExclusionTaglist(Arrays.asList((cpt
          .getListOfExclusionElementTagName().split(","))));
      responseDataVerificationDto.setResponseFilePath(cpt.getResponseDataFile());

    }
    TotalResponseAsString totString = toResponseDateVerification.getTotalResponseAsString();
    if (isNotNull(totString)) {
      responseDataVerificationDto.setExclusionTaglist(Arrays.asList((totString
          .getListOfExclusionElementTagName().split(","))));
      responseDataVerificationDto.setResponseData(totString.getResponseData());
    }
    SubstringVerication subVerication = toResponseDateVerification.getSubstringVerication();
    if (isNotNull(subVerication)) {
      if (isNotNull(subVerication.getContainsThisContent())) {
        responseDataVerificationDto.setContainsString(subVerication.getContainsThisContent());
      } else if (isNotNull(subVerication.getEndsWithThisContent())) {
        responseDataVerificationDto.setEndsWith(subVerication.getEndsWithThisContent());
      } else if (isNotNull(subVerication.getStartsWithThisContent())) {
        responseDataVerificationDto.setStartsWith(subVerication.getStartsWithThisContent());

      }
    }
    return responseDataVerificationDto;
  }

  public TotalResponseDataVerification getResponseDataVerifcation() {
    TotalResponseDataVerification totaDateVerification = new TotalResponseDataVerification();
    if (isNotNull(getResponseData())) {
      SubstringVerication subVerication = new SubstringVerication();
      if (isNotNull(getContainsString())) {
        subVerication.setContainsThisContent(getContainsString());
      } else if (isNotNull(getEndsWith())) {
        subVerication.setEndsWithThisContent(getEndsWith());
      } else if (isNotNull(getStartsWith())) {
        subVerication.setStartsWithThisContent(getStartsWith());
      }
      totaDateVerification.setSubstringVerication(subVerication);
    } else if (isPopulatedFromFilePath()) {
      CompareResponseAgainstFile cpt = new CompareResponseAgainstFile();
      cpt.setListOfExclusionElementTagName(listToString(getExclusionTaglist()));
      cpt.setResponseDataFile(getResponseFilePath());
      totaDateVerification.setCompareResponseAgainstFile(cpt);
    } else {
      TotalResponseAsString totString = new TotalResponseAsString();
      totString.setListOfExclusionElementTagName(listToString(getExclusionTaglist()));
      totString.setResponseData(getResponseData());
      totaDateVerification.setTotalResponseAsString(totString);
    }
    return totaDateVerification;
  }

  public static TotalResponseDataVerification getResponseDataVerifcation(
      final ResponseDataVerificationDto rsDto) {
    TotalResponseDataVerification totaDateVerification = new TotalResponseDataVerification();
    if (isNotNull(rsDto)) {
      if (isNotNull(rsDto.getResponseData())) {
        SubstringVerication subVerication = new SubstringVerication();
        if (isNotNull(rsDto.getContainsString())) {
          subVerication.setContainsThisContent(rsDto.getContainsString());
        } else if (isNotNull(rsDto.getEndsWith())) {
          subVerication.setEndsWithThisContent(rsDto.getEndsWith());
        } else if (isNotNull(rsDto.getStartsWith())) {
          subVerication.setStartsWithThisContent(rsDto.getStartsWith());
        }
        totaDateVerification.setSubstringVerication(subVerication);
      } else if (rsDto.isPopulatedFromFilePath()) {
        CompareResponseAgainstFile cpt = new CompareResponseAgainstFile();
        cpt.setListOfExclusionElementTagName(listToString(rsDto.getExclusionTaglist()));
        cpt.setResponseDataFile(rsDto.getResponseFilePath());
        totaDateVerification.setCompareResponseAgainstFile(cpt);
      } else {
        TotalResponseAsString totString = new TotalResponseAsString();
        totString.setListOfExclusionElementTagName(listToString(rsDto.getExclusionTaglist()));
        totString.setResponseData(rsDto.getResponseData());
        totaDateVerification.setTotalResponseAsString(totString);
      }
    }
    return totaDateVerification;
  }
}
