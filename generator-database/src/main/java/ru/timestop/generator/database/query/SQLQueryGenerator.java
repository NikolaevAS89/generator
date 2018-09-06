package ru.timestop.generator.database.query;

import java.util.Collection;

/**
 * @author t.i.m.e.s.t.o.p
 * @version 1.0.0
 * @since 29.06.2017
 */
public interface SQLQueryGenerator {

    String createSelectQuery(String owner, String tableName, int rowCount);

    String createSelectQuery(String owner, String tableName, Collection<String> pk_columns, int rowCount);

    String createDeleteQuery(String owner, String tableName, int rowCount);

    String createUpdateQuery(String owner, String tableName, Collection<String> pk_columns, Collection<String> columns);

    String createUpdateQuery(String owner, String tableName, Collection<String> columns, int rowCount);

    String createInsertQuery(String owner, String tableName, Collection<String> columns);
}
