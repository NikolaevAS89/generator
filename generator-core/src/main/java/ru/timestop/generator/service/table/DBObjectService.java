package ru.timestop.generator.service.table;


import ru.timestop.generator.database.object.Column;
import ru.timestop.generator.database.object.Table;

import java.sql.SQLException;

/**
 * @author t.i.m.e.s.t.o.p
 * @version 1.0.0
 * @since 24.08.2018
 */
public interface DBObjectService {

    /**
     * cache table metadata
     * @param table metadata
     * @throws SQLException
     */
    void registerTable(Table table) throws SQLException;

    /**
     * @param tableId
     * @return metadata of table or null if ot registered
     */
    Table getTable(String tableId);

    /**
     * @param columnId
     * @return metadata of column or null if ot registered
     */
    Column getColumn(String columnId);
}
