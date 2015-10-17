package com.github.tomakehurst.wiremock.junit;

import java.util.List;

import com.github.tomakehurst.wiremock.client.MappingBuilder;
import com.github.tomakehurst.wiremock.client.RequestPatternBuilder;
import com.github.tomakehurst.wiremock.verification.LoggedRequest;

public interface Stubbing {

  void givenThat(MappingBuilder mappingBuilder);

  void stubFor(MappingBuilder mappingBuilder);

  void verify(RequestPatternBuilder requestPatternBuilder);

  void verify(int count, RequestPatternBuilder requestPatternBuilder);

  List<LoggedRequest> findAll(RequestPatternBuilder requestPatternBuilder);

  void setGlobalFixedDelay(int milliseconds);

  void addRequestProcessingDelay(int milliseconds);
}
