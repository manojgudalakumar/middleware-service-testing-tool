package com.mstt.qa.servicevirtualization.uicomponent.utils;

import static com.mstt.qa.servicevirtualization.servicevirtualizationutils.utils.CommonMethodsUtils.isNotNull;
import static org.apache.commons.collections.CollectionUtils.isNotEmpty;
import static org.apache.commons.collections.MapUtils.isNotEmpty;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.github.tomakehurst.wiremock.http.HttpHeader;
import com.github.tomakehurst.wiremock.http.HttpHeaders;
import com.github.tomakehurst.wiremock.http.ResponseDefinition;
import com.github.tomakehurst.wiremock.matching.RequestPattern;
import com.github.tomakehurst.wiremock.matching.ValuePattern;
import com.mstt.qa.servicevirtualization.servicevirtualizationutils.dto.MsttRequestDefinition;
import com.mstt.qa.servicevirtualization.servicevirtualizationutils.dto.MsttResponseDefinition;
import com.mstt.qa.servicevirtualization.servicevirtualizationutils.dto.PatternMatcherDto;

public class ConvertWireMockStubToMsttStub {
  public static MsttRequestDefinition getRequestDefination(final RequestPattern reptr,
      final Integer priority) {
    MsttRequestDefinition msttRequestDefinition = new MsttRequestDefinition();
    msttRequestDefinition.setBodyPatterns(getBodypatternMatcherList(reptr.getBodyPatterns()));
    msttRequestDefinition.setHeaderPatterns(getPatternListDetails(reptr.getHeaders()));
    msttRequestDefinition.setRequestBody(reptr.getMethod().value());
    msttRequestDefinition.setQueryParamPatterns(getPatternListDetails(reptr.getQueryParameters()));
    msttRequestDefinition.setRequestMethod(reptr.getMethod().value());
    msttRequestDefinition.setPriority(priority);
    msttRequestDefinition.setUrl(reptr.getUrl());
    msttRequestDefinition.setUrlPath(reptr.getUrlPath());
    msttRequestDefinition.setUrlPattern(reptr.getUrlPattern());
    return msttRequestDefinition;
  }

  private static List<PatternMatcherDto> getPatternListDetails(
      final Map<String, ValuePattern> valuePatternMap) {
    List<PatternMatcherDto> bodyPatternMatcher = new ArrayList<PatternMatcherDto>();
    if (isNotEmpty(valuePatternMap)) {
      for (Entry<String, ValuePattern> entryset : valuePatternMap.entrySet()) {
        try {
          PatternMatcherDto ptrnmtchdto = new PatternMatcherDto();
          ptrnmtchdto.setKey(entryset.getKey());
          String pattern = getnonnullattribute(entryset.getValue());
          ptrnmtchdto.setPattern(pattern);
          ptrnmtchdto.setValue(entryset.getValue().getClass().getMethod("get" + pattern)
              .invoke(entryset.getValue()).toString());
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException
            | NoSuchMethodException | SecurityException e) {
          e.printStackTrace();
        }
      }
    }
    return bodyPatternMatcher;
  }

  private static List<PatternMatcherDto> getBodypatternMatcherList(
      final List<ValuePattern> bodyPatternslist) {
    List<PatternMatcherDto> bodyPatternMatcher = new ArrayList<PatternMatcherDto>();
    if (isNotEmpty(bodyPatternslist)) {
      for (ValuePattern valuePattern : bodyPatternslist) {
        try {
          PatternMatcherDto ptrnmtchdto = new PatternMatcherDto();
          ptrnmtchdto.setKey("bodyPatterns");
          String pattern = getnonnullattribute(valuePattern);
          ptrnmtchdto.setPattern(pattern);
          ptrnmtchdto.setValue(valuePattern.getClass().getMethod("get" + pattern)
              .invoke(valuePattern).toString());
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException
            | NoSuchMethodException | SecurityException e) {
          e.printStackTrace();
        }
      }
    }
    return bodyPatternMatcher;
  }

  private static String getnonnullattribute(final ValuePattern valuePattern) {
    if (isNotNull(valuePattern.getContains()))
      return "Contains";
    else if (isNotNull(valuePattern.getDoesNotMatch()))
      return "DoesNotMatch";
    else if (isNotNull(valuePattern.getEqualTo()))
      return "EqualTo";
    else if (isNotNull(valuePattern.getEqualToJson()))
      return "DoesNotMatch";
    else if (isNotNull(valuePattern.getDoesNotMatch()))
      return "EqualToJson";
    else if (isNotNull(valuePattern.getEqualToXml()))
      return "EqualToXml";
    else if (isNotNull(valuePattern.getMatches()))
      return "Matches";
    else if (isNotNull(valuePattern.getDoesNotMatch()))
      return "DoesNotMatch";
    else if (isNotNull(valuePattern.getMatchesJsonPath()))
      return "MatchesJsonPath";
    else if (isNotNull(valuePattern.getMatchesXPath())) {
      return "MatchesXPath";
    }
    return null;
  }

  public static MsttResponseDefinition getResponeDefination(final ResponseDefinition reptr) {
    MsttResponseDefinition pkmwRes = new MsttResponseDefinition();
    pkmwRes.setBodyFileName(reptr.getBodyFileName());
    pkmwRes.setFixedDelayMilliseconds(reptr.getFixedDelayMilliseconds());
    pkmwRes.setHeaders(getHeadersList(reptr.getHeaders()));
    pkmwRes.setProxyAdditionalHeaders(getHeadersList(reptr.getAdditionalProxyRequestHeaders()));
    pkmwRes.setProxyBaseUrl(reptr.getProxyBaseUrl());
    pkmwRes.setResponseTransformerNames(reptr.getTransformers());
    pkmwRes.setStatus(reptr.getStatus());
    return pkmwRes;
  }

  private static Map<String, String> getHeadersList(final HttpHeaders headers) {
    Map<String, String> headMap = new HashMap<String, String>();
    if (isNotNull(headers) && isNotEmpty(headers.all())) {
      for (HttpHeader httpHeader : headers.all()) {
        headMap.put(httpHeader.key(), httpHeader.firstValue());
      }
    }
    return headMap;
  }
}
