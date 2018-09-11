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
        Class<? extends DataBaseAgent> clazz1 = loadSQLAgentClass();
        dataBaseAgent = (DataBaseAgent) registerBean(clazz1, DataBaseAgent.class, SQL_HANDLER_BEAN);

        Class<? extends DataGeneratorService> clazz2 = loadDataGeneratorClass();
        dataGeneratorService = (DataGeneratorService) registerBean(clazz2, DataGeneratorService.class, GENERATOR_PROVIDER_BEAN);
    }


    @Override
    public DataBaseAgent getDBAgent() {
        return dataBaseAgent;
    }

    @Override
    public DataGeneratorService getDataGeneratorProvider() {
        return dataGeneratorService;
    }

    @Override
    public Object registerNewBean(Class clazz) {
        Object result = registerBean(clazz, clazz, clazz.getName());
        postProcessorHandler.getBeanDefinitionRegistry().removeBeanDefinition(clazz.getName());
        return result;
    }

    /**
     * @param clazz
     * @param interfazz
     * @param name
     * @return
     */
    private Object registerBean(Class clazz, Class interfazz, String name) {
        BeanDefinitionRegistry registry = postProcessorHandler.getBeanDefinitionRegistry();
        RootBeanDefinition definition = new RootBeanDefinition();
        LOG.info("Load class " + clazz.toString());
        definition.setTargetType(interfazz);
        definition.setBeanClass(clazz);
        definition.setScope(BeanDefinition.SCOPE_SINGLETON);
        definition.setRole(BeanDefinition.ROLE_APPLICATION);
        registry.registerBeanDefinition(name, definition);

        ApplicationContext ctx = applicationContextProvider.getApplicationContext();

        return ctx.getBean(name, clazz);
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
