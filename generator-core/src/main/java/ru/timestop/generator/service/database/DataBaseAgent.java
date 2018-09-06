package ru.timestop.generator.service.database;

import ru.timestop.generator.data.LargeObjectFactory;
import ru.timestop.generator.database.format.SQLDataFormatter;
import ru.timestop.generator.database.query.SQLQueryGenerator;

import java.io.Closeable;
import java.sql.SQLException;

/**
 * Простой сервис для вставки, удаления, обновления тестовых данных в базу данных
 * <p>
 * Insert : insert random generated row data.
 * Delete : delete number of rows less or equal signed.
 * Update : update number of rows less or equal signed. New values will be random.
 * <p>
 *
 * @author t.i.m.e.s.t.o.p
 * @version 1.0.0
 * @since 24.08.2018
 */
public interface DataBaseAgent extends LargeObjectFactory, Closeable {

    /**
     * create new connection
     *
     * @throws SQLException if something wrong
     */
    void open() throws SQLException;

    /**
     * Commit sql transaction
     *
     * @throws SQLException commit error
     */
    void commit() throws SQLException;

    /**
     * @throws SQLException
     */
    void rollback() throws SQLException;

    /**
     * load table metadata from database
     *
     * @param goalId
     */
    void loadTable(String goalId);


    /**
     * delete certain number of rows
     *
     * @param goalId   target table
     * @param rowCount number of rows
     */
    void deleteRows(String goalId, int rowCount);

    /**
     * delete one row
     *
     * @param goalId target table
     */
    void deleteRow(String goalId);

    /**
     * insert signed number of rows
     *
     * @param goalId    table ru.timestop.generator.database
     * @param rowCount  how many rows will be inserted
     * @param batchSize max inserts in one commit
     */
    void insertRows(String goalId, int rowCount, int batchSize);

    /**
     * insert single row without committing transaction
     *
     * @param goalId target table
     */
    void insertRow(String goalId);

    /**
     * update certain number of rows
     *
     * @param goalId    table ru.timestop.generator.database
     * @param rowCount  how many rows will be inserted
     * @param batchSize max inserts in one commit
     */
    void updateRows(String goalId, int rowCount, int batchSize);

    /**
     * update single row
     *
     * @param goalId target table
     */
    void updateRow(String goalId);

    /**
     * @return used data formater
     */
    SQLDataFormatter getDataFormater();

    /**
     * @return used query generator
     */
    SQLQueryGenerator getQueryGenerator();
}
