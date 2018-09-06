package ru.timestop.generator.goal;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.timestop.generator.GoalUtil;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author t.i.m.e.s.t.o.p
 * @version 1.0.0
 * @since 24.08.2018
 */
public class Goal implements Serializable {
    private final transient String id;

    @JsonProperty
    private String schema;

    @JsonProperty
    private String tableName;

    @JsonProperty
    private Set<String> pkList;

    @JsonProperty
    private Map<String, String> skipFields;

    @JsonProperty
    private Map<String, String> dataFactories;

    private Goal(String id, String schema, String tableName) {
        this.schema = schema;
        this.tableName = tableName;
        this.id = id;
    }

    public String getSchema() {
        return schema;
    }

    public String getTableName() {
        return tableName;
    }

    public boolean isPK(String columnId) {
        return pkList != null && (pkList.contains(columnId));
    }

    public boolean isSkip(String columnId, String opType) {
        return skipFields != null && skipFields.get(columnId) != null && skipFields.get(columnId).contains(opType);
    }

    public String getDataFactoryClassName(String columnName) {
        return (dataFactories == null) ? (null) : (dataFactories.get(columnName));
    }

    private void setSkipFields(Map<String, String> skipFields) {
        this.skipFields = skipFields;
    }

    private void setPKList(Set<String> pkList) {
        this.pkList = pkList;
    }

    private void setDataFactories(Map<String, String> dataFactories) {
        this.dataFactories = dataFactories;
    }

    public String toString() {
        return schema + "." + tableName;
    }

    public String getId() {
        return id;
    }

    public static class Builder {
        private final String schema;
        private final String tableName;
        private Set<String> pkSet;
        private Map<String, String> skipMap;
        private Map<String, String> dataFactories;

        public Builder(String schemaName, String tableName) {
            this.schema = schemaName;
            this.tableName = tableName;
        }

        public Builder addDataFactories(String columnLabel, String factoryClassName) {
            if (this.dataFactories == null) {
                this.dataFactories = new HashMap<>();
            }
            this.dataFactories.put(GoalUtil.getGoalId(schema, tableName, columnLabel), factoryClassName);
            return this;
        }

        public Builder addSkip(String[] skipList) {
            if (this.skipMap == null) {
                this.skipMap = new HashMap<>(skipList.length, 1.1f);
                for (String col : skipList) {
                    String[] parts = col.split(":");
                    if (parts.length > 1) {
                        skipMap.put(GoalUtil.getGoalId(schema, tableName, parts[0]), parts[1]);
                    } else {
                        skipMap.put(GoalUtil.getGoalId(schema, tableName, parts[0]), "U");
                    }
                }
            }
            return this;
        }

        public Builder addPK(String[] pk) {
            if (this.pkSet == null) {
                this.pkSet = new HashSet<>(pk.length + 1, 1.0f);
                for (String col : pk) {
                    pkSet.add(GoalUtil.getGoalId(schema, tableName, col));
                }
            }
            return this;
        }

        public Goal build() {
            String id = GoalUtil.getGoalId(schema, tableName);
            Goal res = new Goal(id, schema, tableName);
            res.setPKList(this.pkSet);
            res.setSkipFields(this.skipMap);
            res.setDataFactories(this.dataFactories);
            return res;
        }
    }
}