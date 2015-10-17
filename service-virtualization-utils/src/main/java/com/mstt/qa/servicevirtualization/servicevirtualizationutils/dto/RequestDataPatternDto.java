package com.mstt.qa.servicevirtualization.servicevirtualizationutils.dto;

import static com.mstt.qa.servicevirtualization.servicevirtualizationutils.utils.CommonMethodsUtils.isNotNull;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import com.mstt.qa.servicevirtualization.RequestAndResponseDataType;
import com.mstt.qa.servicevirtualization.RequestPattern;
import com.mstt.qa.servicevirtualization.RequestPattern.RequestBody;

public class RequestDataPatternDto {
  public String getRequestDataFilePath() {
    return requestDataFilePath;
  }

  public void setRequestDataFilePath(final String requestDataFilePath) throws IOException {
    setPopulatedFromFilePath(true);
    this.requestDataFilePath = requestDataFilePath;
    requestData = FileUtils.readFileToString(new File(requestDataFilePath));
  }

  public String getRequestData() {
    return requestData;
  }

  public void setRequestData(final String requestData) {
    this.requestData = requestData;
  }

  public ServiceDataType getRequestDataType() {
    return requestDataType;
  }

  public void setRequestDataType(final ServiceDataType requestDataType) {
    this.requestDataType = requestDataType;
  }

  public boolean isPopulatedFromFilePath() {
    return populatedFromFilePath;
  }

  public void setPopulatedFromFilePath(final boolean populatedFromFilePath) {
    this.populatedFromFilePath = populatedFromFilePath;
  }

  private String requestData;
  private String requestDataFilePath;
  private ServiceDataType requestDataType;
  private boolean populatedFromFilePath;

  public static RequestDataPatternDto getRequestDataPatternfromJaxb(final RequestPattern reqptrn)
      throws IOException {
    RequestDataPatternDto reqDtPtrn = new RequestDataPatternDto();
    if (isNotNull(reqptrn.getRequestBody().getPullRequestAsString())) {
      reqDtPtrn.setRequestData(reqptrn.getRequestBody().getPullRequestAsString());
    }
    if (isNotNull(reqptrn.getRequestBody().getPullRequestAgainstFile())) {
      reqDtPtrn.setRequestDataFilePath(reqptrn.getRequestBody().getPullRequestAgainstFile());
    }
    if (isNotNull(reqptrn.getRequestDataType().value())) {
      reqDtPtrn
          .setRequestDataType(ServiceDataType.fromString(reqptrn.getRequestDataType().value()));
    }
    return reqDtPtrn;
  }

  public static RequestPattern getRequestDataPattern(final RequestDataPatternDto reqDtPtrn)
      throws IOException {
    RequestPattern reqptrn = new RequestPattern();
    RequestBody reqBody = new RequestBody();
    if (reqDtPtrn.isPopulatedFromFilePath()) {
      reqBody.setPullRequestAgainstFile(reqDtPtrn.getRequestDataFilePath());
    } else {
      reqBody.setPullRequestAsString(reqDtPtrn.getRequestData());
    }
    reqptrn.setRequestBody(reqBody);
    if (isNotNull(reqDtPtrn.getRequestDataType())) {
      reqptrn.setRequestDataType(RequestAndResponseDataType.fromValue(reqDtPtrn
          .getRequestDataType().value()));
    }
    return reqptrn;
  }

  public RequestPattern getRequestDataPatternfromJaxb() throws IOException {
    RequestPattern reqptrn = new RequestPattern();
    RequestBody reqBody = new RequestBody();
    if (isPopulatedFromFilePath()) {
      reqBody.setPullRequestAgainstFile(getRequestDataFilePath());
    } else {
      reqBody.setPullRequestAsString(getRequestData());
    }
    reqptrn.setRequestBody(reqBody);
    if (isNotNull(getRequestDataType())) {
      reqptrn
          .setRequestDataType(RequestAndResponseDataType.fromValue(getRequestDataType().value()));
    }
    return reqptrn;
  }
}
