package ru.timestop.generator.database.format;

import com.sun.org.apache.xerces.internal.impl.dv.util.HexBin;
import org.apache.log4j.Logger;

import java.sql.Blob;
import java.sql.Clob;
/**
 * @author t.i.m.e.s.t.o.p
 * @version 1.0.0
 * @since 23.08.2017
 */
public class SQLDataFormatterMSSQL extends SQLDataFormatterDefault {
    private static final Logger LOG = Logger.getLogger(SQLDataFormatterMSSQL.class);

    @Override
    public String toSQLFormat(Object value) {
        String res = "null";
        if (value != null) {
            try {
                switch (value.getClass().getName()) {
                    case "[B":
                        res = "CONVERT(VARBINARY, '" + HexBin.encode((byte[]) value) + "'))";
                        break;
                    case "java.sql.Clob":
                        Clob c = (Clob) value;
                        res = "'" + c.getSubString(1, (int) c.length()) + "'";
                        break;
                    case "java.sql.Blob":
                        Blob b = (Blob) value;
                        byte bArr[] = b.getBytes(1, (int) b.length());
                        res = "CONVERT(VARBINARY, '" + HexBin.encode(bArr) + "'))";
                        break;
                    default:
                        res = super.toSQLFormat(value);
                        break;
                }
            } catch (Exception e) {
                LOG.error(e);
            }
        }
        return res;
    }
}
