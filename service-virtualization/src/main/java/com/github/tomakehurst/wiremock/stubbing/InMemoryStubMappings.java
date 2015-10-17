/*
 * Copyright (C) 2011 Thomas Akehurst
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package com.github.tomakehurst.wiremock.stubbing;

import static com.github.tomakehurst.wiremock.common.LocalNotifier.notifier;
import static com.github.tomakehurst.wiremock.http.ResponseDefinition.copyOf;
import static com.github.tomakehurst.wiremock.stubbing.StubMapping.NOT_CONFIGURED;
import static com.google.common.collect.Iterables.find;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import com.github.tomakehurst.wiremock.http.Request;
import com.github.tomakehurst.wiremock.http.ResponseDefinition;
import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableList;

public class InMemoryStubMappings implements StubMappings {

  private final SortedConcurrentMappingSet mappings = new SortedConcurrentMappingSet();
  private final ConcurrentHashMap<String, Scenario> scenarioMap =
      new ConcurrentHashMap<String, Scenario>();

  public ResponseDefinition serveFor(final Request request) {
    StubMapping matchingMapping =
        find(mappings, mappingMatchingAndInCorrectScenarioState(request),
            StubMapping.NOT_CONFIGURED);

    notifyIfResponseNotConfigured(request, matchingMapping);
    matchingMapping.updateScenarioStateIfRequired();
    return copyOf(matchingMapping.getResponse());
  }

  private void notifyIfResponseNotConfigured(final Request request,
      final StubMapping matchingMapping) {
    if (matchingMapping == NOT_CONFIGURED) {
      notifier().info("No mapping found matching URL " + request.getUrl());
    }
  }

  public void addMapping(final StubMapping mapping) {
    if (mapping.isInScenario()) {
      scenarioMap.putIfAbsent(mapping.getScenarioName(), Scenario.inStartedState());
      Scenario scenario = scenarioMap.get(mapping.getScenarioName());
      mapping.setScenario(scenario);
    }

    mappings.add(mapping);
  }

  public void reset() {
    mappings.clear();
    scenarioMap.clear();
  }

  public void resetScenarios() {
    for (Scenario scenario : scenarioMap.values()) {
      scenario.reset();
    }
  }

  public List<StubMapping> getAll() {
    return ImmutableList.copyOf(mappings);
  }

  private Predicate<StubMapping> mappingMatchingAndInCorrectScenarioState(final Request request) {
    return new Predicate<StubMapping>() {
      public boolean apply(final StubMapping mapping) {
        return mapping.getRequest().isMatchedBy(request)
            && (mapping.isIndependentOfScenarioState() || mapping.requiresCurrentScenarioState());
      }
    };
  }
}
