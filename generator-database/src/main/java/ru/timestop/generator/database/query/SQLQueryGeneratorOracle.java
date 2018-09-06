package ru.timestop.generator.database.query;


import java.util.Collection;

/**
 * @author t.i.m.e.s.t.o.p
 * @version 1.0.0
 * @since 29.06.2017
 */
public class SQLQueryGeneratorOracle implements SQLQueryGenerator {

    @Override
    public String createSelectQuery(String owner, String tableName, int rowCount) {
        return new StringBuilder()
                .append("select * from \"")
                .append(owner)
                .append("\".\"")
                .append(tableName)
                .append("\" where rownum<=")
                .append(rowCount)
                .toString();
    }

    @Override
    public String createSelectQuery(String owner, String tableName, Collection<String> pk_columns, int rowCount) {
        StringBuilder sb_select = new StringBuilder(255);
        sb_select.append("select ");
        boolean isFirst = true;
        for (String col : pk_columns) {
            if (isFirst) {
                isFirst = false;
                sb_select.append("\"");
                sb_select.append(col);
                sb_select.append("\"");
            } else {
                sb_select.append(", \"");
                sb_select.append(col);
                sb_select.append("\"");
            }
        }
        sb_select.append(" from \"");
        sb_select.append(owner);
        sb_select.append("\".\"");
        sb_select.append(tableName);
        sb_select.append("\" where rownum<=");
        sb_select.append(rowCount);
        return sb_select.toString();
    }

    @Override
    public String createDeleteQuery(String owner, String tableName, int rowCount) {
        return new StringBuilder()
                .append("delete from \"")
                .append(owner)
                .append("\".\"")
                .append(tableName)
                .append("\" where rownum<=")
                .append(rowCount)
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
                sb_update.append("\"")
                        .append(col)
                        .append("\"=?");
            } else {
                sb_update.append(", \"")
                        .append(col)
                        .append("\"=? ");
            }
        }
        sb_update.append(" where ");
        isFirst = true;
        for (String col : pk_columns) {
            if (isFirst) {
                isFirst = false;
                sb_update.append("\"")
                        .append(col)
                        .append("\"=?");
            } else {
                sb_update.append(" and \"")
                        .append(col)
                        .append("\"=?");
            }
        }
        return sb_update.toString();
    }

    @Override
    public String createUpdateQuery(String owner, String tableName, Collection<String> columns, int rowCount) {
        StringBuilder sb_update = new StringBuilder(255);
        sb_update.append("update \"")
                .append(owner)
                .append("\".\"")
                .append(tableName)
                .append("\" set ");
        boolean isFirst = true;
        for (String column : columns) {
            if (isFirst) {
                isFirst = false;
                sb_update.append("\"")
                        .append(column)
                        .append("\"=?");
            } else {
                sb_update.append(", \"")
                        .append(column)
                        .append("\"=?");
            }
        }
        sb_update.append(" where rownum<=");
        sb_update.append(rowCount);
        return sb_update.toString();
    }

    @Override
    public String createInsertQuery(String owner, String tableName, Collection<String> columns) {
        StringBuilder sb = new StringBuilder(255);
        sb.append("insert into \"")
                .append(owner)
                .append("\".\"")
                .append(tableName)
                .append("\"(");
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
