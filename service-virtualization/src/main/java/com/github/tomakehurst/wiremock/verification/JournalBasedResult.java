package com.github.tomakehurst.wiremock.verification;

import com.fasterxml.jackson.annotation.JsonProperty;

public class JournalBasedResult {
  protected final boolean requestJournalDisabled;

  public JournalBasedResult(
      @JsonProperty("requestJournalDisabled") final boolean requestJournalDisabled) {
    this.requestJournalDisabled = requestJournalDisabled;
  }

  @JsonProperty("requestJournalDisabled")
  public boolean requestJournalIsDisabled() {
    return requestJournalDisabled;
  }

  public void assertRequestJournalEnabled() {
    if (requestJournalDisabled) {
      throw new RequestJournalDisabledException();
    }
  }
}
