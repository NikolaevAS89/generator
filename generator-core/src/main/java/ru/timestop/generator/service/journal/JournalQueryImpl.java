package ru.timestop.generator.service.journal;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.timestop.generator.database.format.SQLDataFormatter;
import ru.timestop.generator.properties.JournalProperties;
import ru.timestop.generator.service.SQLAgentProvider;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author t.i.m.e.s.t.o.p
 * @version 1.0.0
 * @since 24.08.2018
 */
@Component
public class JournalQueryImpl implements JournalQuery {

    private final static Logger LOG = Logger.getLogger(JournalQueryImpl.class);

    @Autowired
    private SQLAgentProvider sqlAgentProvider;

    @Autowired
    private JournalProperties properties;

    private List<Row> rows = null;

    private Row currentRow = null;

    private String sqlQuery = null;

    @Override
    public void setQuery(String query) {
        sqlQuery = query;
    }

    @Override
    public void addValue(Object value) {
        this.getCurrentRow().add(value);
    }

    @Override
    public void addRow() {
        this.getRows().add(currentRow);
        currentRow = null;
    }

    @Override
    public void save() {
        if (properties.isSuccessLogEnabled()) {
            try (PrintWriter pw = new PrintWriter(new FileWriter(properties.getSuccessOutput(), true))) {
                pw.println("\n");
                for (Row row : getRows()) {
                    pw.println(fillQuery(sqlQuery, sqlAgentProvider.getDBAgent().getDataFormater(), row));
                }
                pw.flush();
            } catch (IOException e1) {
                LOG.error(e1);
            }
        }
        this.reset();

    }

    @Override
    public void save(Exception e) {
        if (properties.isFailLogEnabled()) {
            try (PrintWriter pw = new PrintWriter(new FileWriter(properties.getFailOutput(), true))) {
                pw.println("/* ");
                pw.println(" * " + e.getMessage());
                pw.println(" * /");
                for (Row row : getRows()) {
                    pw.println(fillQuery(sqlQuery, sqlAgentProvider.getDBAgent().getDataFormater(), row));
                }
                pw.flush();
            } catch (IOException e1) {
                LOG.error(e1);
            }
        }
        this.reset();
    }

    @Override
    public void reset() {
        rows = null;
        currentRow = null;
    }

    /**
     * @return current buffer of rows
     */
    private List<Row> getRows() {
        if (rows == null) {
            rows = new ArrayList<>(126);
        }
        return rows;
    }

    /**
     * @return current row
     */
    private Row getCurrentRow() {
        if (currentRow == null) {
            currentRow = new Row();
        }
        return currentRow;
    }

    /**
     * @param query     template
     * @param formatter of values
     * @param row       with values
     * @return fulled query
     */
    private static String fillQuery(String query, SQLDataFormatter formatter, Row row) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0, j = 0, k = 0; i < query.length(); i++, k = i) {
            i = query.indexOf('?', i);
            if (i >= 0) {
                sb.append(query.substring(k, i));
                if (row.get(j) != null)
                    sb.append(formatter.toSQLFormat(row.get(j)));
                else sb.append("NULL");
                j++;
            } else {
                sb.append(query.substring(k));
                break;
            }
        }
        sb.append(";");
        return sb.toString();
    }

    /**
     * packaging class for simplify reading code
     */
    private static final class Row extends ArrayList<Object> {

    }
}