package ru.timestop.generator.database.format;

import com.sun.org.apache.xerces.internal.impl.dv.util.HexBin;
import oracle.sql.BLOB;
import oracle.sql.RAW;
import oracle.sql.TIMESTAMP;
import org.apache.log4j.Logger;

import java.sql.Clob;
import java.sql.Date;
import java.sql.SQLException;
/**
 * @author t.i.m.e.s.t.o.p
 * @version 1.0.0
 * @since 23.08.2017
 */
public class SQLDataFormatterOracle extends SQLDataFormatterDefault {
    private static final Logger LOG = Logger.getLogger(SQLDataFormatterOracle.class);

    @Override
    public String toSQLFormat(Object value) {
        String res = "null";
        if (value != null) {
            try {
                switch (value.getClass().getName()) {
                    case "oracle.sql.TIMESTAMP":
                        Date date2 = ((TIMESTAMP) value).dateValue();
                        res = "TO_TIMESTAMP_TZ('" + df.format(date2) + " +3:00', 'YYYY-MM-DD HH24:MI:SS.FF3 TZR')";
                        break;
                    case "[B":
                        res = "HEXTORAW('" + HexBin.encode((byte[]) value) + "')";
                        break;
                    case "oracle.sql.RAW":
                        res = "HEXTORAW('" + ((RAW) value).stringValue() + "')";
                        break;
                    case "oracle.sql.CLOB":
                        Clob c = (Clob) value;
                        res = "TO_CLOB('" + c.getSubString(1, (int) c.length()) + "')";
                        break;
                    case "oracle.sql.BLOB":
                        BLOB b = (BLOB) value;
                        byte bArr[] = b.getBytes();
                        res = "TO_BLOB(HEXTORAW('" + HexBin.encode(bArr) + "'))";
                        break;
                    default:
                        res = super.toSQLFormat(value);
                        break;
                }
            } catch (SQLException e) {
                LOG.error(e);
            }
        }
        return res;
    }
}
