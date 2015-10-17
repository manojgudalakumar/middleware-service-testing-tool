package com.mstt.qa.servicevirtualization.servicevirtualizationutils.dto;

import static com.mstt.qa.servicevirtualization.servicevirtualizationutils.utils.CommonMethodsUtils.isNotNull;

import com.mstt.qa.servicevirtualization.ResponsePattern.ResponseDataVerificationsList.ResponseTimeVerification;

public class ResponseTimeVerificationDto {

  public Long getGreaterThanThis() {
    return greaterThanThis;
  }

  public void setGreaterThanThis(final Long greaterThanThis) {
    this.greaterThanThis = greaterThanThis;
  }

  public Long getEqualsToThis() {
    return equalsToThis;
  }

  public void setEqualsToThis(final Long equalsToThis) {
    this.equalsToThis = equalsToThis;
  }

  public Long getLesserThanThis() {
    return lesserThanThis;
  }

  public void setLesserThanThis(final Long lesserThanThis) {
    this.lesserThanThis = lesserThanThis;
  }

  private Long equalsToThis;
  private Long lesserThanThis;
  private Long greaterThanThis;

  public ResponseTimeVerificationDto() {}

  public static ResponseTimeVerificationDto getResponseTimeVerificationDtoFromJaxb(
      final ResponseTimeVerification responseTimeVerification) {
    ResponseTimeVerificationDto resDto = new ResponseTimeVerificationDto();
    if (isNotNull(responseTimeVerification.getEqualsToValue())) {
      resDto.equalsToThis = responseTimeVerification.getEqualsToValue();
    } else if (isNotNull(responseTimeVerification.getGreaterThanValue())) {
      resDto.greaterThanThis = responseTimeVerification.getGreaterThanValue();
    } else if (isNotNull(responseTimeVerification.getLesserThanValue())) {
      resDto.lesserThanThis = responseTimeVerification.getLesserThanValue();
    }
    return resDto;
  }

  public ResponseTimeVerification getResponseTime() {
    ResponseTimeVerification res = new ResponseTimeVerification();
    if (isNotNull(getEqualsToThis())) {
      res.setEqualsToValue(equalsToThis);
    }
    if (isNotNull(getGreaterThanThis())) {
      res.setGreaterThanValue(greaterThanThis);
    }
    if (isNotNull(getLesserThanThis())) {
      res.setLesserThanValue(lesserThanThis);
    }
    return res;
  }

  public static ResponseTimeVerification getResponseTime(final ResponseTimeVerificationDto resDto) {
    ResponseTimeVerification res = new ResponseTimeVerification();
    if (isNotNull(resDto.getEqualsToThis())) {
      res.setEqualsToValue(resDto.equalsToThis);
    }
    if (isNotNull(resDto.getGreaterThanThis())) {
      res.setGreaterThanValue(resDto.greaterThanThis);
    }
    if (isNotNull(resDto.getLesserThanThis())) {
      res.setLesserThanValue(resDto.lesserThanThis);
    }
    return res;
  }

}
