package ru.timestop.generator.database.query;

import java.util.Collection;

/**
 * @author t.i.m.e.s.t.o.p
 * @version 1.0.0
 * @since 29.06.2017
 */
public class SQLQueryGeneratorMSSQL implements SQLQueryGenerator {
    @Override
    public String createSelectQuery(String owner, String tableName, int rowCount) {
        return new StringBuilder()
                .append("select TOP ")
                .append(rowCount)
                .append(" * from \"")
                .append(owner)
                .append("\".\"")
                .append(tableName)
                .append("\"")
                .toString();
    }

    @Override
    public String createSelectQuery(String owner, String tableName, Collection<String> pk_columns, int rowCount) {
        StringBuilder sb = new StringBuilder();
        sb.append("select TOP ");
        sb.append(rowCount);
        boolean isFirst = true;
        for (String col : pk_columns) {
            if (isFirst) {
                isFirst = false;
                sb.append(" \"").append(col).append("\"");
            } else {
                sb.append(", \"").append(col).append("\"");
            }
        }
        sb.append(" from \"").append(owner).append("\".\"").append(tableName).append("\"");
        return sb.toString();
    }

    @Override
    public String createDeleteQuery(String owner, String tableName, int rowCount) {
        return new StringBuilder()
                .append("delete TOP(")
                .append(rowCount)
                .append(") from \"")
                .append(owner)
                .append("\".\"")
                .append(tableName)
                .append("\"")
                .toString();
    }

    @Override
    public String createUpdateQuery(String owner, String tableName, Collection<String> pk_columns, Collection<String> columns) {
        StringBuilder sb_update = new StringBuilder(255);
        sb_update.append("update \"")
                .append(owner)
                .append("\".\"")
                .append(tableName)
                .append("\" set ");
        boolean isFirst = true;
        for (String col : columns) {
            if (isFirst) {
                isFirst = false;
                sb_update.append(col);
                sb_update.append("=?");
            } else {
                sb_update.append(", ");
                sb_update.append(col);
                sb_update.append("=? ");
            }
        }
        sb_update.append(" where ");
        isFirst = true;
        for (String col : pk_columns) {
            if (isFirst) {
                isFirst = false;
                sb_update.append(col);
                sb_update.append("=?");
            } else {
                sb_update.append(" and ");
                sb_update.append(col);
                sb_update.append("=?");
            }
        }
        return sb_update.toString();
    }

    @Override
    public String createUpdateQuery(String owner, String tableName, Collection<String> columns, int rowCount) {
        StringBuilder sb_update = new StringBuilder(255);
        sb_update.append("update TOP(");
        sb_update.append(rowCount);
        sb_update.append(") ");
        sb_update.append(tableName);
        sb_update.append(" set ");
        boolean isFirst = true;
        for (String column : columns) {
            if (isFirst) {
                isFirst = false;
                sb_update.append(column);
                sb_update.append("=?");
            } else {
                sb_update.append(", ");
                sb_update.append(column);
                sb_update.append("=? ");
            }
        }
        return sb_update.toString();
    }

    @Override
    public String createInsertQuery(String owner, String tableName, Collection<String> columns) {
        StringBuilder sb = new StringBuilder(255);
        sb.append("insert into ");
        sb.append(tableName);
        sb.append(" (");
        boolean isFirst = true;
        for (String colName : columns) {
            if (isFirst) {
                isFirst = false;
                sb.append("\"").append(colName).append("\"");
            } else {
                sb.append(",\"").append(colName).append("\"");
            }
        }
        sb.append(") values(");
        for (int i = 0; i < columns.size(); i++) {
            if (i == 0) {
                sb.append("?");
            } else {
                sb.append(",?");
            }
        }
        sb.append(")");
        return sb.toString();
    }
}
