package ru.timestop.generator.service.table;

import org.springframework.stereotype.Component;
import ru.timestop.generator.database.object.Column;
import ru.timestop.generator.database.object.Table;

import java.util.HashMap;
import java.util.Map;

/**
 * @author t.i.m.e.s.t.o.p
 * @version 1.0.0
 * @since 24.08.2018
 */
@Component
public class DBObjectServiceImpl implements DBObjectService {
    private Map<String, Table> cashTable = new HashMap<>();
    private Map<String, Column> cashColumn = new HashMap<>();

    @Override
    public void registerTable(Table table) {
        cashTable.put(table.getID(), table);
        for (String columnId : table.getColumns()) {
            cashColumn.put(columnId, table.getColumn(columnId));
        }
    }

    @Override
    public Table getTable(String tableId) {
        return cashTable.get(tableId);
    }

    @Override
    public Column getColumn(String columnId) {
        return cashColumn.get(columnId);
    }

}
