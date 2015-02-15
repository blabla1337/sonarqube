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

import org.junit.rules.ExternalResource;

/**
 * <b>For tests only</b>
 * <p/>
 * This JUnit rule allows to enable or disable debug logs in tests. By default
 * debug logs are enabled.
 * <p/>
 * A future improvement could be to cache logs so they can
 * verified by assertions.
 * <p/>
 * Of course this class must be used in JUnit tests only, not in production code.
 *
 * @since 5.1
 */
public class LogTester extends ExternalResource {

  private boolean initialDebugMode;

  @Override
  protected void before() throws Throwable {
    initialDebugMode = Loggers.getFactory().isDebugEnabled();
    enableDebug(true);
  }

  @Override
  protected void after() {
    enableDebug(initialDebugMode);
  }

  public boolean isDebugEnabled() {
    return Loggers.getFactory().isDebugEnabled();
  }

  public LogTester enableDebug(boolean b) {
    Loggers.getFactory().enableDebug(b);
    return this;
  }
}
