package ru.timestop.generator.data.generators;

import ru.timestop.generator.data.AbstractGenerator;

/**
 * Random data generator for Timestamp
 *
 * @author t.i.m.e.s.t.o.p
 * @version 1.0.0
 * @since 24.08.2018
 */
public class TimestampGenerator extends AbstractGenerator {
    private final static long TIMESTAMP_MIN;
    private final static long TIMESTAMP_RANGE;

    static {
        TIMESTAMP_MIN = java.sql.Timestamp.valueOf("1970-01-01 00:00:01").getTime();
        TIMESTAMP_RANGE = java.sql.Timestamp.valueOf("2020-01-01 00:00:00").getTime() - TIMESTAMP_MIN + 1;
    }

    public long nextTimestamp() {
        return TIMESTAMP_MIN + (Math.abs(rand.nextLong() % TIMESTAMP_RANGE));
    }

    @Override
    public Object next() {
        return new java.sql.Timestamp(nextTimestamp());
    }
}
