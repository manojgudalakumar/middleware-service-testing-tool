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

import java.util.Comparator;
import java.util.Iterator;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.atomic.AtomicLong;

public class SortedConcurrentMappingSet implements Iterable<StubMapping> {

  private final AtomicLong insertionCount;
  private final ConcurrentSkipListSet<StubMapping> mappingSet;

  public SortedConcurrentMappingSet() {
    insertionCount = new AtomicLong();
    mappingSet =
        new ConcurrentSkipListSet<StubMapping>(sortedByPriorityThenReverseInsertionOrder());
  }

  private Comparator<StubMapping> sortedByPriorityThenReverseInsertionOrder() {
    return new Comparator<StubMapping>() {
      public int compare(final StubMapping one, final StubMapping two) {
        int priorityComparison = one.comparePriorityWith(two);
        if (priorityComparison != 0) {
          return priorityComparison;
        }

        return (two.getInsertionIndex() > one.getInsertionIndex()) ? 1 : -1;
      }
    };
  }

  public Iterator<StubMapping> iterator() {
    return mappingSet.iterator();
  }

  public void add(final StubMapping mapping) {
    mapping.setInsertionIndex(insertionCount.getAndIncrement());
    mappingSet.add(mapping);
  }

  public void clear() {
    mappingSet.clear();
  }


  public String toString() {
    return mappingSet.toString();
  }
}
