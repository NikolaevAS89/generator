package ru.timestop.generator.data.generators.oracle;

import ru.timestop.generator.data.generators.TimestampGenerator;

import java.sql.Timestamp;

/**
 * Random data generator for OracleTimeStamp
 *
 * @author t.i.m.e.s.t.o.p
 * @version 1.0.0
 * @since 24.08.2018
 */
public class OracleTimestampGenerator extends TimestampGenerator {
    @Override
    public Object next() {
        return new oracle.sql.TIMESTAMP((Timestamp) super.next());
    }
}
