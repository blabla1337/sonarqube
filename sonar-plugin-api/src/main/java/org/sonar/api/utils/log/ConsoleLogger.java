/*
 * SonarQube, open source software quality management tool.
 * Copyright (C) 2008-2014 SonarSource
 * mailto:contact AT sonarsource DOT com
 *
 * SonarQube is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * SonarQube is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package org.sonar.api.utils.log;

import org.apache.commons.lang.StringUtils;

import javax.annotation.Nullable;

import java.io.PrintStream;
import java.util.Objects;

/**
 * Slow implementation based on {@link java.lang.System#out}. It is not production-ready and it must be used
 * only for tests that do not have logback dependency.
 * <p/>Implementation of message patterns is naive. It does not support escaped '{' and '}'
 * arguments.
 */
class ConsoleLogger implements Logger {

  private final PrintStream stream;

  ConsoleLogger(String unusedName) {
    this.stream = System.out;
  }

  ConsoleLogger(PrintStream stream) {
    this.stream = stream;
  }

  @Override
  public boolean isDebugEnabled() {
    return Loggers.getFactory().isDebugEnabled();
  }

  @Override
  public void debug(String msg) {
    if (isDebugEnabled()) {
      log("DEBUG", msg);
    }
  }

  @Override
  public void debug(String pattern, @Nullable Object arg) {
    if (isDebugEnabled()) {
      debug(format(pattern, arg));
    }
  }

  @Override
  public void debug(String pattern, @Nullable Object arg1, @Nullable Object arg2) {
    if (isDebugEnabled()) {
      debug(format(pattern, arg1, arg2));
    }
  }

  @Override
  public void debug(String pattern, Object... args) {
    if (isDebugEnabled()) {
      debug(format(pattern, args));
    }
  }

  @Override
  public void info(String msg) {
    log("INFO ", msg);
  }

  @Override
  public void info(String pattern, @Nullable Object arg) {
    info(format(pattern, arg));
  }

  @Override
  public void info(String pattern, @Nullable Object arg1, @Nullable Object arg2) {
    info(format(pattern, arg1, arg2));
  }

  @Override
  public void info(String pattern, Object... args) {
    info(format(pattern, args));
  }

  @Override
  public void warn(String msg) {
    log("WARN ", msg);
  }

  @Override
  public void warn(String pattern, @Nullable Object arg) {
    warn(format(pattern, arg));
  }

  @Override
  public void warn(String pattern, @Nullable Object arg1, @Nullable Object arg2) {
    warn(format(pattern, arg1, arg2));
  }

  @Override
  public void warn(String pattern, Object... args) {
    warn(format(pattern, args));
  }

  @Override
  public void error(String msg) {
    log("ERROR", msg);
  }

  @Override
  public void error(String pattern, @Nullable Object arg) {
    error(format(pattern, arg));
  }

  @Override
  public void error(String pattern, @Nullable Object arg1, @Nullable Object arg2) {
    error(format(pattern, arg1, arg2));
  }

  @Override
  public void error(String pattern, Object... args) {
    error(format(pattern, args));
  }

  @Override
  public void error(String msg, Throwable thrown) {
    log("ERROR", msg);
    thrown.printStackTrace();
  }

  private void log(String level, String msg) {
    this.stream.println(String.format("%s %s", level, msg));
  }

  static String format(String pattern, Object... args) {
    String result = pattern;
    for (Object arg : args) {
      result = StringUtils.replaceOnce(result, "{}", Objects.toString(arg));
    }
    return result;
  }
}
