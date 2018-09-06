package ru.timestop.generator.service.database;

import org.apache.log4j.Logger;
import ru.timestop.generator.database.format.SQLDataFormatter;
import ru.timestop.generator.database.format.SQLDataFormatterMSSQL;
import ru.timestop.generator.database.query.SQLQueryGenerator;
import ru.timestop.generator.database.query.SQLQueryGeneratorMSSQL;

/**
 * @author t.i.m.e.s.t.o.p
 * @version 1.0.0
 * @since 04.09.2018
 */
public class DataBaseAgentMSSQL extends AbstractDataBaseAgent {

    private final static Logger LOG = Logger.getLogger(DataBaseAgentMSSQL.class);
    private static final String DRIVER_NAME = "com.microsoft.sqlserver.jdbc.SQLServerDriver";

    static {
        try {
            LOG.info("Load driver (" + DRIVER_NAME + ")");
            Class.forName(DRIVER_NAME);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private final SQLDataFormatter dataFormatter;
    private final SQLQueryGenerator queryGenerator;

    public DataBaseAgentMSSQL() {
        LOG.info("DataBaseAgentMSSQL is Initialized");
        dataFormatter = new SQLDataFormatterMSSQL();
        queryGenerator = new SQLQueryGeneratorMSSQL();
    }

    @Override
    public SQLDataFormatter getDataFormater() {
        return dataFormatter;
    }

    @Override
    public SQLQueryGenerator getQueryGenerator() {
        return queryGenerator;
    }
}
