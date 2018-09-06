package ru.timestop.generator.database.object;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.timestop.generator.GoalUtil;

import java.io.Serializable;

/**
 * @author t.i.m.e.s.t.o.p
 * @version 1.0.0
 * @since 06.06.2017
 */
public class Column implements Serializable {
    @JsonProperty
    private int index;
    @JsonProperty
    private String tableId;
    @JsonProperty
    private String columnName;
    @JsonProperty
    private String label;
    @JsonProperty
    private String name;
    @JsonProperty
    private DataType dataType;

    private final transient String id;

    private Column(String owner, String tableName, String columnName) {
        this.columnName = columnName;
        this.tableId = GoalUtil.getGoalId(owner, tableName);
        this.id = GoalUtil.getGoalId(owner, tableName, columnName);
    }

    public String getClassName() {
        return dataType.getClassName();
    }

    public int getIndex() {
        return index;
    }

    public String getLabel() {
        return label;
    }

    public String getName() {
        return name;
    }

    public Integer getType() {
        return dataType.getType();
    }

    public String getTypeName() {
        return dataType.getTypeName();
    }

    public Integer getSize() {
        return dataType.getSize();
    }

    public Integer getPrecision() {
        return dataType.getPrecision();
    }

    public Integer getScale() {
        return dataType.getScale();
    }

    public String getTableId() {
        return tableId;
    }

    private void setIndex(int index) {
        this.index = index;
    }

    private void setLabel(String label) {
        this.label = label;
    }

    private void setName(String name) {
        this.name = name;
    }

    private void setDataType(DataType type) {
        this.dataType = type;
    }

    public DataType getDataType() {
        return this.dataType;
    }

    public String toString() {
        return columnName;
    }

    public String getId() {
        return id;
    }

    public boolean isNullable() {
        return dataType.isNullable();
    }

    public boolean isAutoincremental() {
        return dataType.isAutoincrement();
    }

    public static class ColumnBuilder {
        private int index;
        private String owner;
        private String tableName;
        private String className;
        private String label;
        private String name;
        private Integer type;
        private String typeName;
        private Integer size;
        private Integer precision;
        private Integer scale;
        private boolean isAutoincremental = false;
        private boolean isNullable = false;

        public ColumnBuilder() {
        }

        public ColumnBuilder setAutoincremental(boolean isAutoincremental) {
            this.isAutoincremental = isAutoincremental;
            return this;
        }

        public ColumnBuilder setIndex(int index) {
            this.index = index;
            return this;
        }

        public ColumnBuilder setOwner(String owner) {
            this.owner = owner;
            return this;
        }

        public ColumnBuilder setNullable(boolean isNullable) {
            this.isNullable = isNullable;
            return this;
        }

        public ColumnBuilder setTableName(String tableName) {
            this.tableName = tableName;
            return this;
        }

        public ColumnBuilder setClassName(String className) {
            this.className = className;
            return this;
        }

        public ColumnBuilder setLabel(String label) {
            this.label = label;
            return this;
        }

        public ColumnBuilder setName(String name) {
            this.name = name;
            return this;
        }


        public ColumnBuilder setType(Integer type) {
            this.type = type;
            return this;
        }

        public ColumnBuilder setTypeName(String typeName) {
            this.typeName = typeName;
            return this;
        }

        public ColumnBuilder setSize(Integer size) {
            this.size = size;
            return this;
        }

        public ColumnBuilder setPrecision(Integer precision) {
            this.precision = precision;
            return this;
        }

        public ColumnBuilder setScale(Integer scale) {
            this.scale = scale;
            return this;
        }

        public Column build() {
            DataType dataType = new DataType();
            dataType.setAutoincremental(isAutoincremental)
                    .setNullable(isNullable)
                    .setScale(scale)
                    .setPrecision(precision)
                    .setSize(size)
                    .setTypeName(typeName)
                    .setType(type)
                    .setClassName(className);
            Column res = new Column(owner, tableName, name);
            res.setIndex(index);
            res.setLabel(label);
            res.setName(name);
            res.setDataType(dataType);
            return res;
        }
    }
}
