package ru.timestop.generator.service.goal;

import ru.timestop.generator.goal.Goal;

import java.util.Set;

/**
 * @author t.i.m.e.s.t.o.p
 * @version 1.0.0
 * @since 24.08.2018
 */
public interface GoalService {
    /**
     * @return available goal ids
     */
    Set<String> getGoalIds();

    /**
     * @return goal by id or null if not exist
     */
    Goal getGoal(String goalId);
}
