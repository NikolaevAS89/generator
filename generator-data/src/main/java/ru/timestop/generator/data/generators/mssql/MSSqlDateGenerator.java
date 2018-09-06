package ru.timestop.generator.data.generators.mssql;

import ru.timestop.generator.data.generators.TimestampGenerator;

import java.sql.Date;

/**
 * Random data generator for SqlDate
 *
 * @author t.i.m.e.s.t.o.p
 * @version 1.0.0
 * @since 24.08.2018
 */
public class MSSqlDateGenerator extends TimestampGenerator {

    @Override
    public Object next() {
        return new Date(nextTimestamp());
    }
}
