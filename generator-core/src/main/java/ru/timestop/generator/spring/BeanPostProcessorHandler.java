package ru.timestop.generator.spring;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.stereotype.Component;

/**
 * @author t.i.m.e.s.t.o.p
 * @version 1.0.0
 * @since 03.09.2018
 */
@Component
public class BeanPostProcessorHandler implements BeanDefinitionRegistryPostProcessor {

    private BeanDefinitionRegistry beanDefinitionRegistry;


    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        beanDefinitionRegistry = registry;

    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        //SKIP
    }

    public BeanDefinitionRegistry getBeanDefinitionRegistry(){
        return beanDefinitionRegistry;
    }
}
