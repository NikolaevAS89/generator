package ru.timestop.generator.service;

import ru.timestop.generator.service.database.DataBaseAgent;
import ru.timestop.generator.service.datagenerator.DataGeneratorService;

/**
 * @author t.i.m.e.s.t.o.p
 * @version 1.0.0
 * @since 27.08.2018
 */
public interface SQLAgentProvider {

    String SQL_HANDLER_BEAN = "SqlHandlerBean";
    String GENERATOR_PROVIDER_BEAN = "GeneratorProviderBean";

    DataBaseAgent getDBAgent();

    DataGeneratorService getDataGeneratorProvider();
}
