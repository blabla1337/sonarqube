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
package org.sonar.core.activity.db;

import com.google.common.base.Preconditions;
import org.sonar.core.activity.Activity;

import java.io.Serializable;
import java.util.Date;

/**
 * @since 4.4
 */
public class ActivityKey implements Serializable {

  private Date createdAt;
  private Activity.Type type;
  private String author;

  public ActivityKey(Date createdAt, Activity.Type type, String author) {
    this.createdAt = createdAt;
    this.type = type;
    this.author = author;
  }

  /**
   * Create a key. Parameters are NOT null.
   */
  public static ActivityKey of(Date createdAt, Activity.Type type, String author) {
    Preconditions.checkArgument(createdAt != null, "Time must be set");
    Preconditions.checkArgument(type != null, "Type must be set");
    Preconditions.checkArgument(author != null, "Author must be set");
    return new ActivityKey(createdAt, type, author);
  }

  /**
   * Create a key from a string representation (see {@link #toString()}. An {@link IllegalArgumentException} is raised
   * if the format is not valid.
   */
  public static ActivityKey parse(String s) {
    String[] split = s.split(":");
    Preconditions.checkArgument(split.length == 3, "Invalid log key: " + s);
    return ActivityKey.of(new Date(Long.getLong(split[0])),
      Activity.Type.valueOf(split[1]), split[2]);
  }

  public Date getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
  }

  public String getAuthor() {
    return author;
  }

  public ActivityKey setAuthor(String author) {
    this.author = author;
    return this;
  }

  public Activity.Type getType() {
    return type;
  }

  public ActivityKey setType(Activity.Type type) {
    this.type = type;
    return this;
  }

  @Override
  public String toString() {
    return this.createdAt.getTime() +
      ":" + this.type +
      ":" + this.getAuthor();
  }
}