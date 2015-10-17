package com.mstt.qa.servicevirtualization.servicevirtualizationutils.utils;

import static org.apache.commons.collections.CollectionUtils.isEmpty;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

import com.mstt.qa.servicevirtualization.servicevirtualizationutils.dto.PatternMatcherDto;
import com.mstt.qa.servicevirtualization.servicevirtualizationutils.dto.PatternType;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;

public class URLUtils {
  public static String getPathFromURL(final String url) throws MalformedURLException {
    URL urlObj = new URL(url);
    return urlObj.getPath();
  }

  public static List<NameValuePair> extractParameters(final String url) {
    List<NameValuePair> params = null;
    try {
      params = URLEncodedUtils.parse(new URI(url), "UTF-8");
    } catch (URISyntaxException e) {
      e.printStackTrace();
    }
    return params;
  }

  public static void copyNameValuePaitListToPatternMatcherList(final List<NameValuePair> params,
      final List<PatternMatcherDto> patternList) {
    if (isEmpty(patternList)) {
      for (NameValuePair param : params) {
        PatternMatcherDto patternMatch = new PatternMatcherDto();
        patternMatch.setKey(param.getName());
        patternMatch.setValue(param.getValue());
        patternMatch.setPattern(PatternType.equalTo.toString());
        System.out.println(param.getName() + " : " + param.getValue());
        patternList.add(patternMatch);
      }
    }
  }
}
