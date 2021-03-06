package ru.timestop.generator.single;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import ru.timestop.generator.config.ApplicationContextCore;

/**
 * @author t.i.m.e.s.t.o.p
 * @version 1.0.0
 * @since 29.06.2017
 */
@Import(ApplicationContextCore.class)
@PropertySource("classpath:application.properties")
public class ApplicationConfig {

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}
