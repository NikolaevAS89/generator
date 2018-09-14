package ru.timestop.generator.database.object;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * @author t.i.m.e.s.t.o.p
 * @version 1.0.0
 * @since 12.07.2017
 */
public class DataType implements Serializable, Comparable {
    @JsonProperty
    private String className;
    @JsonProperty
    private Integer type;
    @JsonProperty
    private String typeName;
    @JsonProperty
    private Integer size;
    @JsonProperty
    private Integer precision;
    @JsonProperty
    private Integer scale;
    @JsonProperty
    private boolean isNullable;
    @JsonProperty
    private boolean isAutoincremental;

    public String getClassName() {
        return className;
    }

    public DataType setClassName(String className) {
        this.className = className;
        return this;
    }

    public Integer getType() {
        return type;
    }

    public DataType setType(Integer type) {
        this.type = type;
        return this;
    }

    public String getTypeName() {
        return typeName;
    }

    public DataType setTypeName(String typeName) {
        this.typeName = typeName;
        return this;
    }

    public Integer getSize() {
        return size;
    }

    public DataType setSize(Integer size) {
        this.size = size;
        return this;
    }

    public Integer getPrecision() {
        return precision;
    }

    public DataType setPrecision(Integer precision) {
        this.precision = precision;
        return this;
    }

    public Integer getScale() {
        return scale;
    }

    public DataType setScale(Integer scale) {
        this.scale = scale;
        return this;
    }

    public boolean isNullable() {
        return isNullable;
    }

    public DataType setNullable(boolean nullable) {
        isNullable = nullable;
        return this;
    }

    public boolean isAutoincrement() {
        return isAutoincremental;
    }

    public DataType setAutoincremental(boolean autoincremental) {
        isAutoincremental = autoincremental;
        return this;
    }

    public String toString() {
        //TODO
        return super.toString();
    }

    @Override
    public int compareTo(Object o) {
        if (this == o) return 0;
        if (o == null || getClass() != o.getClass()) return 1;

        DataType dataType = (DataType) o;

        int val1 = (type << 24) | (size << 16) | (precision << 8) | (scale);
        int val2 = (dataType.type << 24) | (dataType.size << 16) | (dataType.precision << 8) | (dataType.scale);

        return val1 - val2;
    }
}
