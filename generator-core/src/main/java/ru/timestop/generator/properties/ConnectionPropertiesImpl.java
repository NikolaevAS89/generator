package ru.timestop.generator.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author t.i.m.e.s.t.o.p
 * @version 1.0.0
 * @since 04.09.2018
 */
@Component
public class ConnectionPropertiesImpl implements ConnectionProperties {

    @Value("${connection.handler.db.connection}")
    private String connectionString;

    @Value("${connection.handler.db.type:ORACLE}")
    private String dbType;

    @Value("${connection.handler.generator.type:ORACLE_GENERATOR}")
    private String dataGeneratoryType;

    @Override
    public String getConnectionString() {
        return connectionString;
    }

    @Override
    public String getSQLHandlerClassName() {
        return dbType;
    }

    @Override
    public String getGeneratorProviderClassName() {
        return dataGeneratoryType;
    }

}
