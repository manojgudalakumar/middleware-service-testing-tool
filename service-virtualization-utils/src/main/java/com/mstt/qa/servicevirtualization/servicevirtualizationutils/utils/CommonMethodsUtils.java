package com.mstt.qa.servicevirtualization.servicevirtualizationutils.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Logger;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import com.mstt.qa.servicevirtualization.uicomponent.LoggerPanel;
import com.mstt.qa.servicevirtualization.uicomponent.WindowHandler;

public class CommonMethodsUtils {
  public static boolean isNull(final Object obj) {
    if (obj == null) {
      return true;
    }
    return false;
  }

  public static boolean isNotNull(final Object obj) {
    if (obj == null) {
      return false;
    }
    return true;
  }

  public static String calendarToString(final Calendar cl) {
    SimpleDateFormat sdt = new SimpleDateFormat(ConstantsUtils.DEFAULT_DATETIME_FORMAT);
    return sdt.format(cl.getTime());

  }

  public static Calendar StringToCalendar(final String st) throws ParseException {
    SimpleDateFormat sdt = new SimpleDateFormat(ConstantsUtils.DEFAULT_DATETIME_FORMAT);
    Calendar cal = Calendar.getInstance();
    cal.setTime(sdt.parse(st));
    return cal;

  }

  public static String listToString(final List<String> stringList) {
    StringBuffer str = new StringBuffer();
    if (isNull(stringList)) {
      return null;
    } else {
      for (String string : stringList) {
        str.append(string);
        str.append(",");
      }
    }
    return str.toString();
  }

  public static String IntegerlistToString(final List<Integer> intList) {
    StringBuffer str = new StringBuffer();
    if (isNull(intList)) {
      return null;
    } else {
      for (Integer intvalue : intList) {
        str.append("" + intvalue);
        str.append(",");
      }
    }
    return str.toString();
  }

  public static XMLGregorianCalendar getXMLGregorianCalendar(final Date dt) {
    GregorianCalendar c = new GregorianCalendar();
    c.setTime(dt);
    try {
      return DatatypeFactory.newInstance().newXMLGregorianCalendar(
          new SimpleDateFormat("yyyy-MM-dd").format(dt));
    } catch (DatatypeConfigurationException e) {
      e.printStackTrace();
    }
    return null;
  }

  public static Logger getLogger(final String className) {
    WindowHandler handler = WindowHandler.getInstance();
    Logger logger = Logger.getLogger(className);
    logger.addHandler(handler);
    return logger;
  }

  public static LoggerPanel getloggerPanel() {
    return WindowHandler.getInstance().getLoggerPanel();
  }
}
