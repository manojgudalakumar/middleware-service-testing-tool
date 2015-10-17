package com.github.tomakehurst.wiremock.admin;

import static com.github.tomakehurst.wiremock.common.Json.write;
import static com.github.tomakehurst.wiremock.http.HttpHeader.httpHeader;
import static com.github.tomakehurst.wiremock.matching.RequestPattern.buildRequestPatternFrom;
import static java.net.HttpURLConnection.HTTP_OK;

import com.github.tomakehurst.wiremock.core.Admin;
import com.github.tomakehurst.wiremock.http.HttpHeaders;
import com.github.tomakehurst.wiremock.http.Request;
import com.github.tomakehurst.wiremock.http.ResponseDefinition;
import com.github.tomakehurst.wiremock.matching.RequestPattern;
import com.github.tomakehurst.wiremock.verification.VerificationResult;

public class GetRequestCountTask implements AdminTask {

  public ResponseDefinition execute(final Admin admin, final Request request) {
    RequestPattern requestPattern = buildRequestPatternFrom(request.getBodyAsString());
    VerificationResult result = admin.countRequestsMatching(requestPattern);
    ResponseDefinition response = new ResponseDefinition(HTTP_OK, write(result));
    response.setHeaders(new HttpHeaders(httpHeader("Content-Type", "application/json")));
    return response;
  }
}
