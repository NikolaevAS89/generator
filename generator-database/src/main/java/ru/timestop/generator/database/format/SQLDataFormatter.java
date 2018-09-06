package ru.timestop.generator.database.format;

/**
 * @author t.i.m.e.s.t.o.p
 * @version 1.0.0
 * @since 23.08.2017
 */
public interface SQLDataFormatter {
    String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";
    String toSQLFormat(Object value);
}