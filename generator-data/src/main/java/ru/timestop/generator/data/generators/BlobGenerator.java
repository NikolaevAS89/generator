package ru.timestop.generator.data.generators;

import org.apache.log4j.Logger;
import ru.timestop.generator.BmpGenerator;
import ru.timestop.generator.data.DataGenerator;
import ru.timestop.generator.data.LargeObjectFactory;

import java.sql.Blob;
import java.sql.SQLException;

/**
 * Генератор случайных BLOB объектов
 *
 * @author t.i.m.e.s.t.o.p
 * @version 1.0.0
 * @since 24.08.2018
 */
public class BlobGenerator implements DataGenerator {
    private final static Logger LOG = Logger.getLogger(BlobGenerator.class);

    private BmpGenerator bmpGen = new BmpGenerator();
    private LargeObjectFactory factory;

    public BlobGenerator(LargeObjectFactory factory) {
        this.factory = factory;
    }

    @Override
    public boolean hasNext() {
        return true;
    }

    /**
     * Новый BLOB объект
     *
     * @return новый сгенерированный случайным образом BLOB объект, связанный с подключением к БД
     */
    @Override
    public Object next() {
        try {
            Blob blob = factory.createBlob();
            // Вставка массива байт с изображением в пустой BLOB объект, первая позиция - 1, не 0
            blob.setBytes(1, bmpGen.getBmp());
            return blob;
        } catch (SQLException e) {
            if (LOG.isDebugEnabled()) {
                LOG.error("Blob object not generated, null value returned", e);
            } else {
                LOG.error("Blob object not generated, null value returned");
            }
        }
        return null;
    }

    public String toString() {
        return this.getClass().getName();
    }
}
