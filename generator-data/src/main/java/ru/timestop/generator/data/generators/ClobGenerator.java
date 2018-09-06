package ru.timestop.generator.data.generators;

import org.apache.log4j.Logger;
import ru.timestop.generator.data.LargeObjectFactory;

import java.sql.Clob;
import java.sql.SQLException;

/**
 * Генератор случайных CLOB объектов
 *
 * @author t.i.m.e.s.t.o.p
 * @version 1.0.0
 * @since 24.08.2018
 */
public class ClobGenerator extends StringGenerator {
    private final static Logger LOG = Logger.getLogger(BlobGenerator.class);

    private LargeObjectFactory factory;

    public ClobGenerator(LargeObjectFactory factory) {
        super(10);
        this.factory = factory;
    }

    /**
     * New BLOB object
     *
     * @return new random CLOB object linked with connection
     */
    @Override
    public Object next() {
        try {
            Clob clob = factory.createClob();
            clob.setString(1, toXml(super.next().toString()));
            return clob;
        } catch (SQLException e) {
            if (LOG.isDebugEnabled()) {
                LOG.error("Clob object not generated, null value returned", e);
            } else {
                LOG.error("Clob object not generated, null value returned");
            }
        }
        return null;
    }

    private String toXml(String body) {
        return "<xml><value>" + body + "</value></xml>";
    }
}
