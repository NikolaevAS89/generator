package ru.timestop.generator.service.database;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import ru.timestop.generator.data.DataGenerator;
import ru.timestop.generator.database.object.Table;
import ru.timestop.generator.goal.Goal;
import ru.timestop.generator.properties.ConnectionProperties;
import ru.timestop.generator.service.SQLAgentProvider;
import ru.timestop.generator.service.goal.GoalService;
import ru.timestop.generator.service.journal.JournalQuery;
import ru.timestop.generator.service.table.DBObjectService;

import java.sql.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static ru.timestop.generator.IOUtilites.closeQuiet;

/**
 * @author t.i.m.e.s.t.o.p
 * @version 1.0.0
 * @since 04.09.2018
 */
public abstract class AbstractDataBaseAgent implements DataBaseAgent {

    private final static Logger LOG = Logger.getLogger(AbstractDataBaseAgent.class);

    @Autowired
    private SQLAgentProvider sqlAgentProvider;

    @Autowired
    private GoalService goalService;

    @Autowired
    private DBObjectService objectService;

    @Autowired
    private JournalQuery journalQuery;

    @Autowired
    private ConnectionProperties properties;

    private Connection connection;

    @Override
    public void open() throws SQLException {
        if (connection == null || connection.isClosed()) {
            this.connection = DriverManager.getConnection(properties.getConnectionString());
            this.connection.setAutoCommit(false);
            this.connection.rollback();
        }
    }

    @Override
    public void close() {
        try {
            this.connection.rollback();
        } catch (Exception e) {
            LOG.warn(e);
        } finally {
            closeQuiet(this.connection);
        }
    }

    @Override
    public void commit() throws SQLException {
        connection.commit();
        LOG.info("Transaction was committed");
    }

    @Override
    public void rollback() {
        try {
            connection.rollback();
        } catch (Exception e) {
            LOG.warn(e);
        }
    }

    @Override
    public void loadTable(String goalId) {
        Statement statement = null;
        try {
            Goal target = goalService.getGoal(goalId);
            if (target != null) {
                if (objectService.getTable(goalId) == null) {
                    statement = connection.createStatement();
                    String sql = getQueryGenerator().createSelectQuery(target.getSchema(), target.getTableName(), 1);
                    ResultSet resultSet = statement.executeQuery(sql);
                    ResultSetMetaData metaData = resultSet.getMetaData();
                    Table.Builder builder = new Table.Builder(target.getSchema(), target.getTableName());
                    for (int i = 1; i <= metaData.getColumnCount(); i++) {
                        builder.addColumn(i, metaData);
                    }
                    objectService.registerTable(builder.build());
                    LOG.info(goalId + " is loaded");
                }
            }
        } catch (SQLException e) {
            LOG.error(e);
        } finally {
            closeQuiet(statement);
        }
    }

    @Override
    public void deleteRows(String targetId, int rowCount) {
        deleteRows(targetId, rowCount, true);
    }

    @Override
    public void deleteRow(String targetId) {
        deleteRows(targetId, 1, false);
    }

    @Override
    public void insertRows(String targetId, int rowCount, int batchSize) {
        insertRows(targetId, rowCount, batchSize, true);
    }

    @Override
    public void insertRow(String targetId) {
        insertRows(targetId, 1, -1, false);
    }

    @Override
    public void updateRows(String targetId, int rowCount, int batchSize) {
        updateRows(targetId, rowCount, batchSize, true);
    }

    @Override
    public void updateRow(String targetId) {
        updateRows(targetId, 1, -1, false);
    }

    @Override
    public Clob createClob() {
        try {
            return connection.createClob();
        } catch (SQLException e) {
            LOG.error(e);
            return null;
        }
    }

    @Override
    public Blob createBlob() {
        try {
            return connection.createBlob();
        } catch (SQLException e) {
            LOG.error(e);
            return null;
        }
    }

    /**
     * delete first finded rows
     *
     * @param goalId
     * @param rowCount
     * @param doCommit
     */
    private void deleteRows(String goalId, int rowCount, boolean doCommit) {
        Statement statement = null;
        try {
            Goal target = goalService.getGoal(goalId);
            if (target != null) {
                statement = connection.createStatement();
                String sqlStmt = getQueryGenerator().createDeleteQuery(target.getSchema(), target.getTableName(), rowCount);
                journalQuery.setQuery(sqlStmt);
                journalQuery.addRow();
                int res = statement.executeUpdate(sqlStmt);
                if (doCommit) {
                    commit();
                }
                LOG.info("From " + goalId + " was deleted " + res + " rows.");
                journalQuery.save();
            }
        } catch (SQLException e) {
            rollback();
            journalQuery.save(e);
            LOG.error(e);
        } finally {
            if (doCommit)
                closeQuiet(statement);
        }
    }

    /**
     * @param goalId
     * @param rowCount
     * @param batchSize
     * @param doCommit
     */
    private void insertRows(String goalId, int rowCount, int batchSize, boolean doCommit) {
        PreparedStatement statement = null;
        try {
            Goal target = goalService.getGoal(goalId);
            if (target != null) {
                Table table = objectService.getTable(goalId);
                if (table == null) {
                    LOG.error("Table " + goalId + " not found.");
                } else {
                    List<String> columnIds = table.getColumns().stream().filter(itm -> !target.isSkip(itm, "I")).collect(Collectors.toList());
                    List<String> columnNames = columnIds.stream().map(itm -> table.getColumn(itm).getLabel()).collect(Collectors.toList());
                    String sql = getQueryGenerator().createInsertQuery(target.getSchema(), target.getTableName(), columnNames);
                    journalQuery.setQuery(sql);
                    statement = connection.prepareStatement(sql);
                    List<DataGenerator> generators = columnIds.stream().map(itm -> sqlAgentProvider.getDataGeneratorProvider().get(itm)).collect(Collectors.toList());
                    LOG.info("Insert into " + goalId + " starting...");
                    for (int i = 1; i <= rowCount; i++) {
                        int j = 1;
                        for (DataGenerator generator : generators) {
                            Object value;
                            if (generator.hasNext()) {
                                value = generator.next();
                            } else {
                                LOG.warn("Generator does not have the next value, null will be used");
                                value = null;
                            }
                            statement.setObject(j++, value);
                            journalQuery.addValue(value);
                        }
                        statement.addBatch();
                        journalQuery.addRow();
                        if (i >= rowCount || (i > 1 && batchSize > 0 && i % batchSize == 0)) {
                            try {
                                statement.executeBatch();
                                journalQuery.save();
                                LOG.info("Inserts into " + table.toString() + " " + i + " of " + rowCount);
                                if (doCommit) {
                                    commit();
                                }
                            } catch (SQLException e) {
                                journalQuery.save(e);
                                rollback();
                            }
                        }
                    }
                }
            }
        } catch (SQLException e) {
            rollback();
            LOG.error(e);
        } finally {
            closeQuiet(statement);
        }
    }

    /**
     * @param targetId
     * @param rowCount
     * @param batchSize
     * @param doCommit
     */
    private void updateRows(String targetId, int rowCount, int batchSize, boolean doCommit) {
        try {
            Goal target = goalService.getGoal(targetId);
            if (target != null) {
                Table table = objectService.getTable(targetId);
                if (table == null) {
                    LOG.error("Table " + targetId + " not found.");
                } else {
                    List<String> pkIds = table.getColumns().stream().filter(itm -> !target.isSkip(itm, "U")).filter(target::isPK).collect(Collectors.toList());
                    if (pkIds.isEmpty()) {
                        updateRowsWithoutPK(target, table, rowCount, batchSize, doCommit);
                    } else {
                        updateWithPK(target, table, pkIds, rowCount, batchSize, doCommit);
                    }
                }
            }
        } catch (SQLException e) {
            rollback();
            LOG.error(e);
        }
    }

    /**
     * @param goal
     * @param table
     * @param rowCount
     * @param batchSize
     * @param doCommit
     * @throws SQLException
     */
    private void updateRowsWithoutPK(Goal goal, Table table, int rowCount, int batchSize, boolean doCommit) throws SQLException {
        PreparedStatement statement = null;
        try {
            List<String> columnIds = table.getColumns().stream().filter(itm -> !goal.isSkip(itm, "U")).filter(itm -> !goal.isPK(itm)).collect(Collectors.toList());
            if (columnIds.isEmpty()) {
                LOG.warn("For table " + table.toString() + " nothing to be updated...");
            } else {
                List<String> columnNames = columnIds.stream().map(itm -> table.getColumn(itm).getLabel()).collect(Collectors.toList());
                String query = getQueryGenerator().createUpdateQuery(goal.getSchema(), goal.getTableName(), columnNames, rowCount);
                journalQuery.setQuery(query);
                statement = connection.prepareStatement(query);
                int i = 1;
                for (String colId : columnIds) {
                    Object value = sqlAgentProvider.getDataGeneratorProvider().get(colId).next();
                    statement.setObject(i++, value);
                    journalQuery.addValue(value);
                }
                journalQuery.addRow();
                statement.execute();
                if (doCommit) {
                    commit();
                }
                LOG.info("Updates " + goal + " complete");
            }
        } finally {
            closeQuiet(statement);
        }
    }

    /**
     * @param target
     * @param table
     * @param pkIds
     * @param rowCount
     * @param batchSize
     * @param doCommit
     */
    private void updateWithPK(Goal target, Table table, List<String> pkIds, int rowCount, int batchSize, boolean doCommit) {
        Statement selectStmt = null;
        PreparedStatement statement = null;
        try {
            List<String> columnIds = table.getColumns().stream()
                    .filter(itm -> !target.isSkip(itm, "U"))
                    .filter(itm -> !target.isPK(itm))
                    .collect(Collectors.toList());
            if (columnIds.isEmpty()) {
                LOG.warn("For table " + table.toString() + " nothing to be update...");
            } else {
                List<String> pkNames = pkIds.stream()
                        .map(itm -> table.getColumn(itm).getLabel())
                        .collect(Collectors.toList());
                String selectQuery = getQueryGenerator().createSelectQuery(target.getSchema(), target.getTableName(), pkNames, rowCount);

                List<String> columnNames = columnIds.stream()
                        .filter(itm -> !target.isSkip(itm, "U") && !target.isPK(itm))
                        .map(itm -> table.getColumn(itm).getLabel())
                        .collect(Collectors.toList());
                String updateQuery = getQueryGenerator().createUpdateQuery(target.getSchema(), target.getTableName(), pkNames, columnNames);

                List<DataGenerator> generators = columnIds.stream()
                        .filter(itm -> !target.isSkip(itm, "U") && !target.isPK(itm))
                        .map(itm -> table.getColumn(itm).getId())
                        .map(itm -> sqlAgentProvider.getDataGeneratorProvider().get(itm))
                        .collect(Collectors.toList());

                journalQuery.setQuery(updateQuery);
                selectStmt = connection.createStatement();
                ResultSet resultSet = selectStmt.executeQuery(selectQuery);
                statement = connection.prepareStatement(updateQuery);
                int itr = 0;
                boolean isExecuted = false;
                while (resultSet.next()) {
                    itr++;
                    int i = 1;
                    isExecuted = false;
                    for (DataGenerator generator : generators) {
                        Object value = generator.next();
                        statement.setObject(i++, value);
                        journalQuery.addValue(value);
                    }
                    for (int k = 1; k <= pkIds.size(); k++) {
                        Object value = resultSet.getObject(k);
                        statement.setObject(i++, value);
                        journalQuery.addValue(value);
                    }
                    statement.addBatch();
                    journalQuery.addRow();

                    if (itr >= rowCount || (itr > 1 && batchSize > 0 && itr % batchSize == 0)) {
                        isExecuted = true;
                        try {
                            int[] result = statement.executeBatch();
                            System.out.println(Arrays.toString(result));
                            SQLWarning warning = statement.getWarnings();
                            System.out.println(warning);
                            journalQuery.save();
                            LOG.info("Updates " + table.toString() + " " + itr + " of " + rowCount);
                            if (doCommit) {
                                commit();
                            }
                        } catch (SQLException e) {
                            journalQuery.save(e);
                            rollback();
                        }
                    }
                }
                if (!isExecuted) {
                    try {
                        statement.executeBatch();
                        journalQuery.save();
                        LOG.info("Updates " + table.toString() + " " + itr + " of " + rowCount);
                        if (doCommit) {
                            commit();
                        }
                    } catch (SQLException e) {
                        journalQuery.save(e);
                        rollback();
                        LOG.error(e);
                    }
                }
            }
        } catch (SQLException e) {
            journalQuery.save(e);
            rollback();
            LOG.error(e);
        } finally {
            closeQuiet(selectStmt);
            closeQuiet(statement);
        }
    }

}
