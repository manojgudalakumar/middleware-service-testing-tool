package com.mstt.qa.servicevirtualization.servicevirtualizationutils.dto;

import static com.mstt.qa.servicevirtualization.servicevirtualizationutils.utils.CommonMethodsUtils.isNotNull;

import com.mstt.qa.servicevirtualization.MiddleWareTestingTool.DateTimeConfig;
import com.mstt.qa.servicevirtualization.servicevirtualizationutils.utils.ConstantsUtils;

public class DateTimeConfigDto {
  public String getDateFormat() {
    return DateFormat;
  }

  public void setDateFormat(final String dateFormat) {
    DateFormat = dateFormat;
  }

  public String getDateTimeFormat() {
    return DateTimeFormat;
  }

  public void setDateTimeFormat(final String dateTimeFormat) {
    DateTimeFormat = dateTimeFormat;
  }

  public String getTimeFormat() {
    return timeFormat;
  }

  public void setTimeFormat(final String timeFormat) {
    this.timeFormat = timeFormat;
  }

  private String DateTimeFormat;
  private String timeFormat;
  private String DateFormat;

  public static DateTimeConfigDto getDateTimeConfigDtofromJaxb(final DateTimeConfig dateConfig) {
    DateTimeConfigDto dtDto = new DateTimeConfigDto();
    if (isNotNull(dateConfig.getDateFormat())) {
      dtDto.setDateFormat(dateConfig.getDateFormat());
    } else {
      dtDto.setDateFormat(ConstantsUtils.DEFAULT_DATE_FORMAT);
    }
    if (isNotNull(dateConfig.getDateTimeFormat())) {
      dtDto.setDateTimeFormat(dateConfig.getDateTimeFormat());
    } else {
      dtDto.setDateTimeFormat(ConstantsUtils.DEFAULT_DATETIME_FORMAT);
    }
    if (isNotNull(dateConfig.getTimeFormat())) {
      dtDto.setTimeFormat(dateConfig.getTimeFormat());
    } else {
      dtDto.setTimeFormat(ConstantsUtils.DEFAULT_TIME_FORMAT);
    }
    return dtDto;
  }

  public static DateTimeConfig getDateTimeConfig(final DateTimeConfigDto dtDto) {
    DateTimeConfig dtconfig = new DateTimeConfig();
    if (isNotNull(dtDto.getDateFormat())) {
      dtconfig.setDateFormat(dtDto.getDateFormat());
    } else {
      dtconfig.setDateFormat(ConstantsUtils.DEFAULT_DATE_FORMAT);
    }
    if (isNotNull(dtDto.getDateTimeFormat())) {
      dtconfig.setDateTimeFormat(dtDto.getDateTimeFormat());
    } else {
      dtconfig.setDateTimeFormat(ConstantsUtils.DEFAULT_DATETIME_FORMAT);
    }
    if (isNotNull(dtDto.getTimeFormat())) {
      dtconfig.setTimeFormat(dtDto.getTimeFormat());
    } else {
      dtconfig.setTimeFormat(ConstantsUtils.DEFAULT_TIME_FORMAT);
    }
    return dtconfig;
  }
}
