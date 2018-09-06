package ru.timestop.generator.service.goal;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.timestop.generator.goal.Goal;
import ru.timestop.generator.goal.Scope;
import ru.timestop.generator.properties.GeneratorProperties;

import javax.annotation.PostConstruct;
import java.util.Set;

import static ru.timestop.generator.IOUtilites.load;

/**
 * @author t.i.m.e.s.t.o.p
 * @version 1.0.0
 * @since 24.08.2018
 */
@Component
public class GoalServiceImpl implements GoalService {
    private final static Logger LOG = Logger.getLogger(GoalServiceImpl.class);

    @Autowired
    private GeneratorProperties properties;

    private Scope scope;

    public GoalServiceImpl() {
        scope = new Scope();
    }

    @PostConstruct
    private void loadGoals() {
        try {
            load(properties.getGoalListPath(), scope);
        } catch (Exception e) {
            LOG.error("Target loaded with error ", e);
        }
    }

    @Override
    public Set<String> getGoalIds() {
        return scope.getKeys();
    }

    @Override
    public Goal getGoal(String goalId) {
        return scope.get(goalId);
    }
}
