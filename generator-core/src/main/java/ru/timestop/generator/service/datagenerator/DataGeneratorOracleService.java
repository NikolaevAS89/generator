package ru.timestop.generator.service.datagenerator;

import ru.timestop.generator.data.DataGenerator;
import ru.timestop.generator.data.generators.*;
import ru.timestop.generator.data.generators.oracle.OracleRowidGenerator;
import ru.timestop.generator.data.generators.oracle.OracleTimestampGenerator;
import ru.timestop.generator.database.object.Column;
import ru.timestop.generator.database.query.TypeNotSupportedException;

/**
 * @author t.i.m.e.s.t.o.p
 * @version 1.0.0
 * @since 24.08.2018
 */
public class DataGeneratorOracleService extends AbstractDataGeneratorService {

    @Override
    public DataGenerator newDefaultGenerator(Column column) throws TypeNotSupportedException {
        DataGenerator dataGenerator = null;
        String className = column.getClassName();
        switch (className) {
            case "oracle.sql.TIMESTAMPTZ":
                dataGenerator = new TimestampGenerator();
                break;
            case "oracle.sql.TIMESTAMP":
                dataGenerator = new OracleTimestampGenerator();
                break;
            case "[B":
                dataGenerator = new BytesGenerator(column.getSize());
                break;
            case "oracle.jdbc.OracleClob":
                if (dataGeneratorConfig.isClobEnabled()) {
                    dataGenerator = new ClobGenerator(getSqlAgentProvider().getDBAgent());
                }
                break;
            case "oracle.jdbc.OracleBlob":
                if (dataGeneratorConfig.isBlobEnabled()) {
                    dataGenerator = new BlobGenerator(getSqlAgentProvider().getDBAgent());
                }
                break;
            case "oracle.sql.ROWID":
                dataGenerator = new OracleRowidGenerator();
                break;
            case "java.sql.SQLXML":
                dataGenerator = new XmlTypeGenerator();
                break;
            default:
                dataGenerator = super.newDefaultGenerator(column);
                break;
        }
        if (dataGenerator == null) {
            throw new TypeNotSupportedException(column.getTypeName(), className);
        }
        return dataGenerator;
    }
}
