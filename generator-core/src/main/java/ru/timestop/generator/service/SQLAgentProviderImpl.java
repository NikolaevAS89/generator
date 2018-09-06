package ru.timestop.generator.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import ru.timestop.generator.mode.DataGeneratorType;
import ru.timestop.generator.mode.SQLHandlerType;
import ru.timestop.generator.properties.ConnectionProperties;
import ru.timestop.generator.service.database.DataBaseAgent;
import ru.timestop.generator.service.datagenerator.DataGeneratorService;
import ru.timestop.generator.spring.ApplicationContextProvider;
import ru.timestop.generator.spring.BeanPostProcessorHandler;

import javax.annotation.PostConstruct;

/**
 * @author t.i.m.e.s.t.o.p
 * @version 1.0.0
 * @since 27.08.2018
 */
@Component
public class SQLAgentProviderImpl implements SQLAgentProvider {

    private static final Logger LOG = Logger.getLogger(SQLAgentProviderImpl.class);

    @Autowired
    private ApplicationContextProvider applicationContextProvider;
    @Autowired
    private BeanPostProcessorHandler postProcessorHandler;

    @Autowired
    private ConnectionProperties properties;

    private DataGeneratorService dataGeneratorService;

    private DataBaseAgent dataBaseAgent;

    @PostConstruct
    public void init() {
        BeanDefinitionRegistry registry = postProcessorHandler.getBeanDefinitionRegistry();

        RootBeanDefinition beanDefinitionAgent = new RootBeanDefinition();
        Class<? extends DataBaseAgent> clazz1 = loadSQLAgentClass();
        LOG.info("SQL Agent class : " + clazz1.toString());
        beanDefinitionAgent.setBeanClass(clazz1);
        beanDefinitionAgent.setTargetType(DataBaseAgent.class);
        beanDefinitionAgent.setRole(BeanDefinition.ROLE_APPLICATION);
        beanDefinitionAgent.setScope(BeanDefinition.SCOPE_SINGLETON);
        registry.registerBeanDefinition(SQLAgentProvider.SQL_HANDLER_BEAN, beanDefinitionAgent);

        RootBeanDefinition beanDefinitionGenerator = new RootBeanDefinition();
        Class<? extends DataGeneratorService> clazz2 = loadDataGeneratorClass();
        LOG.info("Data generator class : " + clazz2.toString());
        beanDefinitionGenerator.setTargetType(DataGeneratorService.class);
        beanDefinitionGenerator.setBeanClass(clazz2);
        beanDefinitionGenerator.setScope(BeanDefinition.SCOPE_SINGLETON);
        beanDefinitionGenerator.setRole(BeanDefinition.ROLE_APPLICATION);
        registry.registerBeanDefinition(SQLAgentProvider.GENERATOR_PROVIDER_BEAN, beanDefinitionGenerator);

        ApplicationContext ctx = applicationContextProvider.getApplicationContext();

        dataBaseAgent = ctx.getBean(SQL_HANDLER_BEAN, DataBaseAgent.class);
        dataGeneratorService = ctx.getBean(GENERATOR_PROVIDER_BEAN, DataGeneratorService.class);
    }

    @Override
    public DataBaseAgent getDBAgent() {
        return dataBaseAgent;
    }

    @Override
    public DataGeneratorService getDataGeneratorProvider() {
        return dataGeneratorService;
    }

    /**
     * @return loaded sql agent class
     */
    private Class<? extends DataBaseAgent> loadSQLAgentClass() {
        String sqlHandlerClassName = properties.getSQLHandlerClassName();
        try {
            return SQLHandlerType.valueOf(sqlHandlerClassName).agent();
        } catch (IllegalArgumentException e) {
            //SKIP
        }
        try {
            return ((Class<? extends DataBaseAgent>) Class.forName(sqlHandlerClassName));
        } catch (ClassNotFoundException e1) {
            throw new RuntimeException(e1);
        }
    }

    /**
     * @return loaded data generator class
     */
    private Class<? extends DataGeneratorService> loadDataGeneratorClass() {
        String sqlProviderClassName = properties.getGeneratorProviderClassName();
        try {
            return DataGeneratorType.valueOf(sqlProviderClassName).service();
        } catch (IllegalArgumentException e) {
            //SKIP
        }
        try {
            return ((Class<? extends DataGeneratorService>) Class.forName(sqlProviderClassName));
        } catch (ClassNotFoundException e1) {
            throw new RuntimeException(e1);
        }
    }
}
