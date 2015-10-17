package com.github.tomakehurst.wiremock.http;

import com.github.tomakehurst.wiremock.core.FaultInjector;
import com.mstt.qa.servicevirtualization.soapservice.utils.SoapUtil;

public enum Fault {

  EMPTY_RESPONSE {
    @Override
    public void apply(final FaultInjector faultInjector) {
      faultInjector.emptyResponseAndCloseConnection();
    }
  },

  MALFORMED_RESPONSE_CHUNK {
    @Override
    public void apply(final FaultInjector faultInjector) {
      faultInjector.malformedResponseChunk();
    }
  },
  NOT_A_VALID_SERVICE {
    @Override
    public void apply(final FaultInjector faultInjector) {
      faultInjector.returnFault(SoapUtil.getSoapFault("NOT_A_VALID_SERVICE",
          "the end point which you have provided is not valid one"));
    }
  },

  RANDOM_DATA_THEN_CLOSE {
    @Override
    public void apply(final FaultInjector faultInjector) {
      faultInjector.randomDataAndCloseConnection();
    }
  };

  public abstract void apply(FaultInjector faultInjector);
}
