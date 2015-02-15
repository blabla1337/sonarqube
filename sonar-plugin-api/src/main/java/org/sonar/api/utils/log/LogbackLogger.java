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

import javax.annotation.Nullable;

/**
 * Note that logback is accessed through SLF4J
 */
class LogbackLogger implements Logger {

  private final transient org.slf4j.Logger slf4j;

  LogbackLogger(org.slf4j.Logger slf4j) {
    this.slf4j = slf4j;
  }

  @Override
  public boolean isDebugEnabled() {
    return slf4j.isDebugEnabled();
  }

  @Override
  public void debug(String msg) {
    slf4j.debug(msg);
  }

  @Override
  public void debug(String msg, @Nullable Object arg) {
    slf4j.debug(msg, arg);
  }

  @Override
  public void debug(String msg, @Nullable Object arg1, @Nullable Object arg2) {
    slf4j.debug(msg, arg1, arg2);
  }

  @Override
  public void debug(String msg, Object... args) {
    slf4j.debug(msg, args);
  }

  @Override
  public void info(String msg) {
    slf4j.info(msg);
  }

  @Override
  public void info(String msg, @Nullable Object arg) {
    slf4j.info(msg, arg);
  }

  @Override
  public void info(String msg, @Nullable Object arg1, @Nullable Object arg2) {
    slf4j.info(msg, arg1, arg2);
  }

  @Override
  public void info(String msg, Object... args) {
    slf4j.info(msg, args);
  }

  @Override
  public void warn(String msg) {
    slf4j.warn(msg);
  }

  @Override
  public void warn(String msg, @Nullable Object arg) {
    slf4j.warn(msg, arg);
  }

  @Override
  public void warn(String msg, @Nullable Object arg1, @Nullable Object arg2) {
    slf4j.warn(msg, arg1, arg2);
  }

  @Override
  public void warn(String msg, Object... args) {
    slf4j.warn(msg, args);
  }

  @Override
  public void error(String msg) {
    slf4j.error(msg);
  }

  @Override
  public void error(String msg, @Nullable Object arg) {
    slf4j.error(msg, arg);
  }

  @Override
  public void error(String msg, @Nullable Object arg1, @Nullable Object arg2) {
    slf4j.error(msg, arg1, arg2);
  }

  @Override
  public void error(String msg, Object... args) {
    slf4j.error(msg, args);
  }

  @Override
  public void error(String msg, Throwable thrown) {
    slf4j.error(msg, thrown);
  }
}
