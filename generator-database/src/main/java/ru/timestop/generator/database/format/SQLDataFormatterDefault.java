package ru.timestop.generator.database.format;

import org.apache.log4j.Logger;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * @author t.i.m.e.s.t.o.p
 * @version 1.0.0
 * @since 23.08.2017
 */
public class SQLDataFormatterDefault implements SQLDataFormatter {
    private static final Logger LOG = Logger.getLogger(SQLDataFormatterDefault.class);

    final static DateFormat df = new SimpleDateFormat(DATE_FORMAT);
    final String timeZone = "+3:00";

    @Override
    public String toSQLFormat(Object value) {
        String res = "null";
        if (value != null) {
            try {
                switch (value.getClass().getName()) {
                    case "java.sql.Timestamp":
                    case "java.sql.Date":
                        Date date = new Date(((Timestamp) value).getTime());
                        res = "TO_TIMESTAMP('" + df.format(date) + "', 'YYYY-MM-DD HH24:MI:SS.FF3')";
                        break;
                    case "java.lang.String":
                        res = "'" + value.toString() + "'";
                        break;
                    case "java.math.BigDecimal":
                    case "java.lang.Double":
                    case "java.lang.Float":
                    case "java.lang.Integer":
                        res = value.toString();
                        break;
                    case "java.lang.Boolean":
                        res = (boolean) value ? "1" : "0";
                        break;
                    default:
                        break;
                }
            } catch (Exception e) {
                LOG.error(e);
            }
        }
        return res;
    }
}
