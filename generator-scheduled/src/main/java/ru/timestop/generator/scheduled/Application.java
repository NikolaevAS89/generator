package ru.timestop.generator.scheduled;

import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;

/**
 * @author t.i.m.e.s.t.o.p
 * @version 1.0.0
 * @since 29.06.2017
 */
@Service
@EnableScheduling
@SpringBootApplication
public class Application {
    private final static Logger LOG = Logger.getLogger(Application.class);

    public static void main(String[] args) {
        try {
            SpringApplication.run(ApplicationConfig.class, args);
        } catch (Exception e) {
            LOG.error(e.toString());
        }
    }
}
