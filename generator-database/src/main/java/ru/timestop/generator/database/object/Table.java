package ru.timestop.generator.database.object;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.timestop.generator.GoalUtil;

import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author t.i.m.e.s.t.o.p
 * @version 1.0.0
 * @since 06.06.2017
 */
public class Table {
    @JsonProperty
    private String tableName;
    @JsonProperty
    private Map<String, Column> columns;
    private final transient String id;

    private void setColumns(Map<String, Column> columns) {
        this.columns = columns;
    }

    private Table(String schemaName, String tableName) {
        this.id = GoalUtil.getGoalId(schemaName, tableName);
        this.tableName = schemaName + "." + tableName;
    }

    public Set<String> getColumns() {
        return columns.keySet();
    }

    public Column getColumn(String columnId) {
        return columns.get(columnId);
    }

    public int getColumnCount() {
        return columns.size();
    }

    public String toString() {
        return tableName;
    }

    public String getID() {
        return id;
    }

    public static class Builder {
        private String schemaName;
        private String tableName;
        private Map<String, Column> columns;

        public Builder(String schemaName, String tableName) {
            this.schemaName = schemaName;
            this.tableName = tableName;
            this.columns = new HashMap<>();
        }

        public Builder addColumn(int i, ResultSetMetaData metadata) throws SQLException {
            String colName = metadata.getColumnName(i);
            Column.ColumnBuilder builder = new Column.ColumnBuilder();

            Column column = builder
                    .setClassName(metadata.getColumnClassName(i))
                    .setIndex(i)
                    .setLabel(metadata.getColumnLabel(i))
                    .setName(colName)
                    .setOwner(schemaName)
                    .setPrecision(metadata.getPrecision(i))
                    .setScale(metadata.getScale(i))
                    .setSize(metadata.getColumnDisplaySize(i))
                    .setType(metadata.getColumnType(i))
                    .setTypeName(metadata.getColumnTypeName(i))
                    .setTableName(tableName)
                    .setNullable(metadata.isNullable(i) == ResultSetMetaData.columnNullable)
                    .setAutoincremental(metadata.isAutoIncrement(i))
                    .build();

            columns.put(column.getId(), column);
            return this;
        }

        public Table build() {
            Table res = new Table(schemaName, tableName);
            res.setColumns(columns);
            return res;
        }
    }

}
