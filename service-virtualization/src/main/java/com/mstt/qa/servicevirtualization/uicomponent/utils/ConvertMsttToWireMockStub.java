package com.mstt.qa.servicevirtualization.uicomponent.utils;

import static com.mstt.qa.servicevirtualization.servicevirtualizationutils.utils.CommonMethodsUtils.isNotNull;
import static org.apache.commons.collections.CollectionUtils.isNotEmpty;
import static org.apache.commons.collections.MapUtils.isNotEmpty;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.tomakehurst.wiremock.http.HttpHeader;
import com.github.tomakehurst.wiremock.http.HttpHeaders;
import com.github.tomakehurst.wiremock.http.RequestMethod;
import com.github.tomakehurst.wiremock.http.ResponseDefinition;
import com.github.tomakehurst.wiremock.matching.RequestPattern;
import com.github.tomakehurst.wiremock.matching.ValuePattern;
import com.github.tomakehurst.wiremock.stubbing.StubMapping;
import com.mstt.qa.servicevirtualization.servicevirtualizationutils.dto.MsttRequestDefinition;
import com.mstt.qa.servicevirtualization.servicevirtualizationutils.dto.MsttResponseDefinition;
import com.mstt.qa.servicevirtualization.servicevirtualizationutils.dto.PatternMatcherDto;

public class ConvertMsttToWireMockStub {
  public static StubMapping convertToStubMapping(final MsttRequestDefinition msttReqPattern,
      final MsttResponseDefinition msttResponseDefination, final String serviceType,
      final String bodyFileName) {
    RequestPattern requestPattern = buildRequestPatternFrom(msttReqPattern);
    ResponseDefinition responseToWrite =
        buildResponseDefination(msttResponseDefination, bodyFileName,
            msttResponseDefination.getFixedDelayMilliseconds(), msttReqPattern);
    StubMapping mapping = new StubMapping(requestPattern, responseToWrite);
    mapping.setPriority(msttReqPattern.getPriority());
    mapping.setServiceType(serviceType);
    return mapping;
  }

  private static ResponseDefinition buildResponseDefination(
      final MsttResponseDefinition msttResponseDefination, final String bodyFileName,
      final Integer fixedDelayMilliseconds, final MsttRequestDefinition msttReqPattern) {
    ResponseDefinition responseDef = new ResponseDefinition();
    responseDef.setAdditionalProxyRequestHeaders(getAdditionalProxyReqHds(msttResponseDefination
        .getProxyAdditionalHeaders()));
    responseDef.setBodyFileName(bodyFileName);
    responseDef.setFixedDelayMilliseconds(fixedDelayMilliseconds);
    responseDef.setHeaders(getHeaderss(msttResponseDefination.getHeaders()));
    responseDef.setProxyBaseUrl(msttResponseDefination.getProxyBaseUrl());
    responseDef.setStatus(msttResponseDefination.getStatus());
    responseDef.setTransformers(msttResponseDefination.getResponseTransformerNames());
    return responseDef;
  }

  private static RequestPattern buildRequestPatternFrom(final MsttRequestDefinition msttReqPattern) {
    RequestPattern rqstPatrn = new RequestPattern();
    rqstPatrn.setBodyPatterns(getBodyPatternsLst(msttReqPattern.getBodyPatterns()));
    rqstPatrn.setHeaders(getHeaderslist(msttReqPattern.getHeaderPatterns()));
    rqstPatrn.setMethod(RequestMethod.fromString(msttReqPattern.getRequestMethod()));
    rqstPatrn.setQueryParameters(getQueryPatternList(msttReqPattern.getQueryParamPatterns()));
    rqstPatrn.setUrl(msttReqPattern.getUrl());
    rqstPatrn.setUrlPath(msttReqPattern.getUrlPath());
    rqstPatrn.setUrlPattern(msttReqPattern.getUrlPattern());
    return rqstPatrn;
  }

  private static List<ValuePattern> getBodyPatternsLst(final List<PatternMatcherDto> bodyPatterns) {
    if (isNotNull(bodyPatterns) && isNotEmpty(bodyPatterns)) {
      List<ValuePattern> valuLst = new ArrayList<ValuePattern>();
      for (PatternMatcherDto ptrnDto : bodyPatterns) {
        ValuePattern vt = new ValuePattern();
        try {
          vt =
              (ValuePattern) ValuePattern.class.getMethod(ptrnDto.getPattern(), String.class)
                  .invoke(vt, ptrnDto.getValue());
          valuLst.add(vt);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException
            | NoSuchMethodException | SecurityException e) {
          e.printStackTrace();
        }
      }
      return valuLst;
    }
    return null;
  }

  private static Map<String, ValuePattern> getHeaderslist(
      final List<PatternMatcherDto> headerPatterns) {
    if (isNotNull(headerPatterns) && isNotEmpty(headerPatterns)) {
      Map<String, ValuePattern> headerMap = new HashMap<String, ValuePattern>();
      for (PatternMatcherDto ptrnDto : headerPatterns) {
        ValuePattern vt = new ValuePattern();
        try {
          vt =
              (ValuePattern) ValuePattern.class.getMethod(ptrnDto.getPattern(), String.class)
                  .invoke(vt, ptrnDto.getValue());
          headerMap.put(ptrnDto.getKey(), vt);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException
            | NoSuchMethodException | SecurityException e) {
          e.printStackTrace();
        }

      }
      return headerMap;
    }
    return null;
  }

  private static Map<String, ValuePattern> getQueryPatternList(
      final List<PatternMatcherDto> queryPatterns) {
    if (isNotNull(queryPatterns) && isNotEmpty(queryPatterns)) {
      Map<String, ValuePattern> queryParamPttrnMap = new HashMap<String, ValuePattern>();
      for (PatternMatcherDto ptrnDto : queryPatterns) {
        ValuePattern vt = new ValuePattern();
        try {
          vt =
              (ValuePattern) vt.getClass().getMethod(ptrnDto.getPattern(), String.class)
                  .invoke(vt, ptrnDto.getValue());
          queryParamPttrnMap.put(ptrnDto.getKey(), vt);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException
            | NoSuchMethodException | SecurityException e) {
          e.printStackTrace();
        }
      }
      return queryParamPttrnMap;
    }
    return null;
  }

  private static HttpHeaders getAdditionalProxyReqHds(
      final Map<String, String> proxyAdditionalHeaders) {
    return getHttpHdrs(proxyAdditionalHeaders);
  }

  private static HttpHeaders getHeaderss(final Map<String, String> headers) {
    return getHttpHdrs(headers);
  }

  private static HttpHeaders getHttpHdrs(final Map<String, String> headerMap) {
    if (isNotEmpty(headerMap)) {
      List<HttpHeader> httpHdrLst = new ArrayList<HttpHeader>();
      for (Map.Entry<String, String> entry : headerMap.entrySet()) {
        HttpHeader httpHdr = new HttpHeader(entry.getKey(), entry.getValue());
        httpHdrLst.add(httpHdr);
      }
      return new HttpHeaders(httpHdrLst);
    }
    return null;

  }
}
