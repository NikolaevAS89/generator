package ru.timestop.generator.database.object;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * @author t.i.m.e.s.t.o.p
 * @version 1.0.0
 * @since 12.07.2017
 */
public class DataType implements Serializable {
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

    //TODO
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DataType dataType = (DataType) o;

        if (className != null ? !className.equals(dataType.className) : dataType.className != null) return false;
        if (size != null ? !size.equals(dataType.size) : dataType.size != null) return false;
        if (type != null ? !type.equals(dataType.type) : dataType.type != null) return false;
        if (typeName != null ? !typeName.equals(dataType.typeName) : dataType.typeName != null) return false;
        if (isAutoincremental != dataType.isAutoincremental) return false;
        if (isNullable != dataType.isNullable) return false;
        if (precision != null ? !precision.equals(dataType.precision) : dataType.precision != null) return false;
        return scale != null ? scale.equals(dataType.scale) : dataType.scale == null;

    }

    //TODO
    @Override
    public int hashCode() {
        int result = className != null ? className.hashCode() : 0;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (typeName != null ? typeName.hashCode() : 0);
        result = 31 * result + (size != null ? size.hashCode() : 0);
        result = 31 * result + (precision != null ? precision.hashCode() : 0);
        result = 31 * result + (scale != null ? scale.hashCode() : 0);
        result = 31 * result + (isNullable ? 1 : 0);
        result = 31 * result + (isAutoincremental ? 1 : 0);
        return result;
    }

    public String toString() {
        //TODO
        return super.toString();
    }
}
