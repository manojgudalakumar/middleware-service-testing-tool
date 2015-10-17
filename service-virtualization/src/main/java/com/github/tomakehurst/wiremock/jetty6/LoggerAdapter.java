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
package com.github.tomakehurst.wiremock.jetty6;

import org.mortbay.log.Logger;

import com.github.tomakehurst.wiremock.common.ConsoleNotifier;
import com.github.tomakehurst.wiremock.common.Notifier;

public class LoggerAdapter implements Logger {

  private final Notifier notifier;

  public LoggerAdapter() {
    this(new ConsoleNotifier(false));
  }

  public LoggerAdapter(final Notifier notifier) {
    this.notifier = notifier;
  }

  public boolean isDebugEnabled() {
    return false;
  }

  public void setDebugEnabled(final boolean enabled) {
    // Nothing
  }

  public void info(final String msg, final Object arg0, final Object arg1) {
    // Nothing
  }

  public void debug(final String msg, final Throwable th) {
    // Nothing
  }

  public void debug(final String msg, final Object arg0, final Object arg1) {
    // Nothing
  }

  public void warn(final String msg, final Object arg0, final Object arg1) {
    notifier.error(msg);
  }

  public void warn(final String msg, final Throwable th) {
    notifier.error(msg, th);
  }

  public Logger getLogger(final String name) {
    return new LoggerAdapter(notifier);
  }
}
