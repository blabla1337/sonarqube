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

package org.sonar.server.user.index;

import org.apache.commons.dbutils.DbUtils;
import org.elasticsearch.action.update.UpdateRequest;
import org.sonar.core.persistence.DbSession;
import org.sonar.server.db.DbClient;
import org.sonar.server.es.BaseIndexer;
import org.sonar.server.es.BulkIndexer;
import org.sonar.server.es.EsClient;

import java.sql.Connection;
import java.util.Iterator;

public class UserIndexer extends BaseIndexer {

  private final DbClient dbClient;

  public UserIndexer(DbClient dbClient, EsClient esClient) {
    super(esClient, 300, UserIndexDefinition.INDEX, UserIndexDefinition.TYPE_USER);
    this.dbClient = dbClient;
  }

  @Override
  protected long doIndex(long lastUpdatedAt) {
    final BulkIndexer bulk = new BulkIndexer(esClient, UserIndexDefinition.INDEX);
    bulk.setLarge(lastUpdatedAt == 0L);

    DbSession dbSession = dbClient.openSession(false);
    Connection dbConnection = dbSession.getConnection();
    try {
      UserResultSetIterator rowIt = UserResultSetIterator.create(dbClient, dbConnection, lastUpdatedAt);
      long maxUpdatedAt = doIndex(bulk, rowIt);
      rowIt.close();
      return maxUpdatedAt;

    } finally {
      DbUtils.closeQuietly(dbConnection);
      dbSession.close();
    }
  }

  private long doIndex(BulkIndexer bulk, Iterator<UserDoc> users) {
    long maxUpdatedAt = 0L;
    bulk.start();
    while (users.hasNext()) {
      UserDoc user = users.next();
      bulk.add(newUpsertRequest(user));
      maxUpdatedAt = Math.max(maxUpdatedAt, user.updatedAt());
    }
    bulk.stop();
    return maxUpdatedAt;
  }

  private UpdateRequest newUpsertRequest(UserDoc user) {
    return new UpdateRequest(UserIndexDefinition.INDEX, UserIndexDefinition.TYPE_USER, user.login())
      .doc(user.getFields())
      .upsert(user.getFields());
  }

}
