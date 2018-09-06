package ru.timestop.generator.service.journal;

/**
 * @author t.i.m.e.s.t.o.p
 * @version 1.0.0
 * @since 06.06.2017
 */
public interface JournalQuery {

    /**
     * @param query template
     */
    void setQuery(String query);

    /**
     * @param value of next field of row
     */
    void addValue(Object value);

    /**
     * start new row
     */
    void addRow();

    /**
     * save queries as success
     */
    void save();

    /**
     * save queries as fail
     *
     * @param e describe of error
     */
    void save(Exception e);

    /**
     * drop all loaded queries
     */
    void reset();
}
