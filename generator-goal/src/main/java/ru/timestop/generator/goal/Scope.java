package ru.timestop.generator.goal;

import org.apache.log4j.Logger;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

/**
 * @author t.i.m.e.s.t.o.p
 * @version 1.0.0
 * @since 24.08.2018
 */
public class Scope implements Consumer<String> {

    private final static Logger LOG = Logger.getLogger(Scope.class);

    private final Map<String, Goal> goals;

    public Scope() {
        this.goals = new HashMap<>();
    }

    public Set<String> getKeys() {
        return goals.keySet();
    }

    public Goal get(String goalId) {
        return goals.get(goalId);
    }

    @Override
    public void accept(String line) {
        String[] parts = line.split("\\|");
        String[] table = parts[0].split("\\.");
        if (table.length != 2) {
            LOG.warn("In line \"" + line + "\" there is not valid table description, line will be skipped");
        } else {
            Goal.Builder builder = new Goal.Builder(table[0], table[1]);
            if (parts.length > 1) {
                builder.addPK(parts[1].split(","));
            }
            if (parts.length > 2) {
                builder.addSkip(parts[2].split(","));
            }
            LOG.info(Arrays.toString(parts) + " loaded...");
            Goal goal = builder.build();
            goals.put(goal.getId(), goal);
        }
    }
}
