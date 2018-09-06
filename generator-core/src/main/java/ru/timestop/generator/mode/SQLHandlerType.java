package ru.timestop.generator.mode;

import ru.timestop.generator.service.database.DataBaseAgent;
import ru.timestop.generator.service.database.DataBaseAgentMSSQL;
import ru.timestop.generator.service.database.DataBaseAgentOracle;

/**
 * @author t.i.m.e.s.t.o.p
 * @version 1.0.0
 * @since 29.08.2018
 */
public enum SQLHandlerType {
    ORACLE(DataBaseAgentOracle.class), MSSQL(DataBaseAgentMSSQL.class);

    Class<? extends DataBaseAgent> sqlAgentClazz;

    SQLHandlerType(Class<? extends DataBaseAgent> sqlAgentClazz) {
        this.sqlAgentClazz = sqlAgentClazz;
    }

    public Class<? extends DataBaseAgent> agent() {
        return sqlAgentClazz;
    }
}