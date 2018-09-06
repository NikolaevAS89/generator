package ru.timestop.generator.data;

import java.sql.Blob;
import java.sql.Clob;

/**
 * @author t.i.m.e.s.t.o.p
 * @version 1.0.0
 * @since 24.08.2018
 */
public interface LargeObjectFactory {
    /**
     * Generates CLOB object
     *
     * @return SQL CLOB object
     */
    Clob createClob();

    /**
     * Generates BLOB object
     *
     * @return SQL BLOB object
     */
    Blob createBlob();
}
