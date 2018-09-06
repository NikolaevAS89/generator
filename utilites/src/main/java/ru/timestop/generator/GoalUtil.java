package ru.timestop.generator;

/**
 * @author t.i.m.e.s.t.o.p
 * @version 1.0.0
 * @since 24.08.2018
 */
public class GoalUtil {
    /**
     * @param owner
     * @param name
     * @return
     */
    public static String getGoalId(String owner, String name) {
        return (owner + "." + name).toUpperCase();
    }

    /**
     * @param owner
     * @param table
     * @param columnName
     * @return
     */
    public static String getGoalId(String owner, String table, String columnName) {
        return (owner + "." + table + "." + columnName).toUpperCase();
    }
};