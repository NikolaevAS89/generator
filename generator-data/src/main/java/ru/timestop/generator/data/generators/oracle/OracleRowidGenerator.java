package ru.timestop.generator.data.generators.oracle;

import oracle.sql.ROWID;
import org.apache.log4j.Logger;
import ru.timestop.generator.data.generators.BlobGenerator;
import ru.timestop.generator.data.generators.StringGenerator;

import java.sql.SQLException;

/**
 * @author t.i.m.e.s.t.o.p
 * @version 1.0.0
 * @since 24.08.2018
 */
public class OracleRowidGenerator extends StringGenerator {
    private final static Logger LOG = Logger.getLogger(BlobGenerator.class);

    private static final int ROWID_LENGTH = 18;

    public OracleRowidGenerator() {
        super(ROWID_LENGTH);
    }

    @Override
    public Object next() {
        try {
            return new ROWID((String) super.next());
        } catch (SQLException e) {
            if (LOG.isDebugEnabled()) {
                LOG.error("ROWID object not generated, null value returned", e);
            } else {
                LOG.error("ROWID object not generated, null value returned");
            }
        }
        return null;
    }
}
