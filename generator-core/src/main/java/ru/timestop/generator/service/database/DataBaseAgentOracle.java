package ru.timestop.generator.service.database;

import org.apache.log4j.Logger;
import ru.timestop.generator.database.format.SQLDataFormatter;
import ru.timestop.generator.database.format.SQLDataFormatterOracle;
import ru.timestop.generator.database.query.SQLQueryGenerator;
import ru.timestop.generator.database.query.SQLQueryGeneratorOracle;

/**
 * @author t.i.m.e.s.t.o.p
 * @version 1.0.0
 * @since 05.06.2017
 */
public class DataBaseAgentOracle extends AbstractDataBaseAgent {

    private final static Logger LOG = Logger.getLogger(DataBaseAgentOracle.class);
    private static final String DRIVER_NAME = "oracle.jdbc.driver.OracleDriver";

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

    public DataBaseAgentOracle() {
        LOG.info("DataBaseAgentOracle is Initialized");
        dataFormatter = new SQLDataFormatterOracle();
        queryGenerator = new SQLQueryGeneratorOracle();
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
