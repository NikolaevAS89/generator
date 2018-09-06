package ru.timestop.generator.single;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Service;
import ru.timestop.generator.properties.GeneratorProperties;
import ru.timestop.generator.service.SQLAgentProvider;
import ru.timestop.generator.service.database.DataBaseAgent;
import ru.timestop.generator.service.goal.GoalService;

import java.util.Set;

/**
 * @author t.i.m.e.s.t.o.p
 * @version 1.0.0
 * @since 29.06.2017
 */
@Service
@SpringBootApplication
public class Application implements CommandLineRunner {
    private final static Logger log = Logger.getLogger(Application.class);

    @Autowired
    private SQLAgentProvider provider;

    @Autowired
    private GoalService goals;

    @Autowired
    private GeneratorProperties properties;

    @Override
    public void run(String[] args) throws Exception {

        Set<String> goalIds = goals.getGoalIds();
        DataBaseAgent dbAgent = provider.getDBAgent();
        try {
            dbAgent.open();
            for (String tabDesc : goalIds) {
                dbAgent.loadTable(tabDesc);
                if (properties.getRowInsert() > 0) {
                    dbAgent.insertRows(tabDesc, properties.getRowInsert(), properties.getMaxBatchSize());
                }
                if (properties.getRowUpdate() > 0) {
                    dbAgent.updateRows(tabDesc, properties.getRowUpdate(), properties.getMaxBatchSize());
                }
                if (properties.getRowDelete() > 0) {
                    dbAgent.deleteRows(tabDesc, properties.getRowDelete());
                }
            }
        } catch (Exception e) {
            log.error(e);
            throw e;
        } finally {
            dbAgent.close();
        }
    }

    public static void main(String[] args) {
        try {
            SpringApplication.run(ApplicationConfig.class, args);
        } catch (Exception e) {
            log.error(e.toString());
        }
    }
}
