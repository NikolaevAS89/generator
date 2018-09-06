package ru.timestop.generator.properties;

/**
 * @author t.i.m.e.s.t.o.p
 * @version 1.0.0
 * @since 04.09.2018
 */
public interface ConnectionProperties {

    /**
     * @return connection string of database
     */
    String getConnectionString();

    /**
     * @return type or name of class sqlhandler
     */
    String getSQLHandlerClassName();

    /**
     * @return type or name of class generators provider
     */
    String getGeneratorProviderClassName();
}
