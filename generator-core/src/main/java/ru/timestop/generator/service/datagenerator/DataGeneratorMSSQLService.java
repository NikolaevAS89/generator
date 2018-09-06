package ru.timestop.generator.service.datagenerator;

import ru.timestop.generator.data.DataGenerator;
import ru.timestop.generator.data.generators.BytesGenerator;
import ru.timestop.generator.data.generators.ShortGenerator;
import ru.timestop.generator.database.object.Column;
import ru.timestop.generator.database.query.TypeNotSupportedException;

/**
 * @author t.i.m.e.s.t.o.p
 * @version 1.0.0
 * @since 24.08.2018
 */
public class DataGeneratorMSSQLService extends AbstractDataGeneratorService {
    @Override
    public DataGenerator newDefaultGenerator(Column column) throws TypeNotSupportedException {
        DataGenerator dataGenerator = null;
        String className = column.getClassName();

        switch (className) {
            case "[B":
                if (column.getTypeName().equals("timestamp")) // MSSQL timestamp (represented in byte[]) not supported
                    throw new TypeNotSupportedException(column.getTypeName(), className);
                dataGenerator = new BytesGenerator(column.getSize() / 2);
                break;
            case "java.lang.Short":
                if (column.getTypeName().equals("tinyint"))
                    dataGenerator = new ShortGenerator((short) 255);
                break;
            default:
                break;
        }
        if (dataGenerator == null) {
            dataGenerator = super.newDefaultGenerator(column);
        }
        if (dataGenerator == null) {
            throw new TypeNotSupportedException(column.getTypeName(), className);
        }
        return dataGenerator;
    }
}
