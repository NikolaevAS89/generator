package ru.timestop.generator.scheduled;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.timestop.generator.properties.GeneratorProperties;
import ru.timestop.generator.service.SQLAgentProvider;
import ru.timestop.generator.service.database.DataBaseAgent;
import ru.timestop.generator.service.goal.GoalService;

import java.util.Set;

/**
 * @author sbt-nikolaev1-as
 * @version 1.0.0
 * @since 29.06.2017
 */
@Component
public class ApplicationScheduledTask {

    private static final Logger log = Logger.getLogger(ApplicationScheduledTask.class);

    @Autowired
    private SQLAgentProvider provider;

    @Autowired
    private GoalService goals;

    @Autowired
    private GeneratorProperties properties;

    @Scheduled(cron = "${generator.cron.expression:0 * * * * *}")
    public void runTask() throws Exception {
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
}
