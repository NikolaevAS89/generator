package ru.timestop.generator.properties;

/**
 * @author t.i.m.e.s.t.o.p
 * @version 1.0.0
 * @since 04.09.2018
 */
public interface JournalProperties {
    /**
     * @return
     */
    boolean isFailLogEnabled();

    /**
     * @return
     */
    String getFailOutput();

    /**
     * @return
     */
    boolean isSuccessLogEnabled();

    /**
     * @return
     */
    String getSuccessOutput();
}
