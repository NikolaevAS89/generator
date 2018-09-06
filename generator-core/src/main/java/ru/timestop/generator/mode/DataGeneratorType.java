package ru.timestop.generator.mode;

import ru.timestop.generator.service.datagenerator.DataGeneratorMSSQLService;
import ru.timestop.generator.service.datagenerator.DataGeneratorOracleService;
import ru.timestop.generator.service.datagenerator.DataGeneratorService;

/**
 * @author t.i.m.e.s.t.o.p
 * @version 1.0.0
 * @since 04.09.2018
 */
public enum DataGeneratorType {
    ORACLE_GENERATOR(DataGeneratorOracleService.class), MSSQL_GENERATOR(DataGeneratorMSSQLService.class);

    Class<? extends DataGeneratorService> datageneratorClazz;

    DataGeneratorType(Class<? extends DataGeneratorService> datageneratorClazz) {
        this.datageneratorClazz = datageneratorClazz;
    }

    public Class<? extends DataGeneratorService> service() {
        return datageneratorClazz;
    }
}
