package ru.timestop.generator.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author t.i.m.e.s.t.o.p
 * @version 1.0.0
 * @since 04.09.2018
 */
@Component
public class GeneratorPropertiesImpl implements GeneratorProperties {
    @Value("${generator.goal.scope:keys.csv}")
    private String goalListPath;

    @Value("${generator.generators:generators.csv}")
    private String generatorListPath;

    @Value("${generator.dml.row.delete.count:1}")
    private int rowDelete;

    @Value("${generator.dml.row.insert.count:2}")
    private int rowInsert;

    @Value("${generator.dml.row.update.count:1}")
    private int rowUpdate;

    @Value("${generator.dml.batch.size.max:100}")
    private int maxBatchSize;

    @Override
    public int getRowDelete() {
        return rowDelete;
    }

    @Override
    public int getRowInsert() {
        return rowInsert;
    }

    @Override
    public int getRowUpdate() {
        return rowUpdate;
    }

    @Override
    public int getMaxBatchSize() {
        return maxBatchSize;
    }

    @Override
    public String getGoalListPath() {
        return goalListPath;
    }

    @Override
    public String getExtendedGeneratorPath() {
        return generatorListPath;
    }
}