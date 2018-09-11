package ru.timestop.generator.properties;

/**
 * @author t.i.m.e.s.t.o.p
 * @version 1.0.0
 * @since 04.09.2018
 */
public interface GeneratorProperties {
    /**
     * @return count of row for deleted
     */
    int getRowDelete();

    /**
     * @return count of row for insert
     */
    int getRowInsert();

    /**
     * @return count of row for update
     */
    int getRowUpdate();

    /**
     * @return count of query bach
     */
    int getMaxBatchSize();

    /**
     * @return path to file with list of target tables
     */
    String getGoalListPath();

    /**
     * @return path to file with list of extended generators
     */
    String getExtendedGeneratorPath();
}