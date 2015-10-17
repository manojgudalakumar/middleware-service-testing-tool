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
package com.github.tomakehurst.wiremock.global;

import static com.github.tomakehurst.wiremock.common.LocalNotifier.notifier;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadSafeRequestDelayControl implements RequestDelayControl {

  private final AtomicInteger delayMilliseconds = new AtomicInteger(0);
  private final ConcurrentHashMap<Thread, Object> threadsBeingDelayed =
      new ConcurrentHashMap<Thread, Object>();

  public void setDelay(final int milliseconds) {
    notifier().info("Setting request delay to " + milliseconds + "ms");
    delayMilliseconds.set(milliseconds);
  }

  public void clearDelay() {
    notifier().info("Clearing request delay");
    delayMilliseconds.set(0);
    cancelAllDelays();
  }

  public void delayIfRequired() throws InterruptedException {
    int millis = delayMilliseconds.get();
    if (millis != 0) {
      threadsBeingDelayed.put(Thread.currentThread(), new Object());
      Thread.sleep(millis);
    }
  }

  public void cancelAllDelays() {
    for (Thread thread : threadsBeingDelayed.keySet()) {
      thread.interrupt();
    }
  }
}
