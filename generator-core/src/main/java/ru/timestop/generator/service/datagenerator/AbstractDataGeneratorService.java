package ru.timestop.generator.service.datagenerator;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import ru.timestop.generator.data.DataGenerator;
import ru.timestop.generator.data.generators.*;
import ru.timestop.generator.database.object.Column;
import ru.timestop.generator.database.object.DataType;
import ru.timestop.generator.database.query.TypeNotSupportedException;
import ru.timestop.generator.properties.DataGeneratorProperties;
import ru.timestop.generator.service.SQLAgentProvider;
import ru.timestop.generator.service.extended.ExtendedDataGeneratorService;
import ru.timestop.generator.service.table.DBObjectService;

import java.util.Map;
import java.util.TreeMap;

/**
 * Implementation of service for working with data generators
 *
 * @author t.i.m.e.s.t.o.p
 * @version 1.0.0
 * @since 24.08.2018
 */
abstract class AbstractDataGeneratorService implements DataGeneratorService {

    private static final Logger LOG = Logger.getLogger(AbstractDataGeneratorService.class);

    @Autowired
    private SQLAgentProvider sqlAgentProvider;

    @Autowired
    private DBObjectService DBObjectService;

    @Autowired
    private ExtendedDataGeneratorService extendedDataGeneratorService;

    @Autowired
    DataGeneratorProperties dataGeneratorConfig;

    private final Map<DataType, DataGenerator> defaultGenerators = new TreeMap<>();

    @Override
    public DataGenerator get(String columnId) {
        DataGenerator generator = extendedDataGeneratorService.get(columnId);
        if (generator == null) {
            Column column = DBObjectService.getColumn(columnId);
            generator = defaultGenerators.get(column.getDataType());
            if (generator == null) {
                try {
                    generator = newDefaultGenerator(column);
                } catch (TypeNotSupportedException e) {
                    LOG.error(e);
                    generator = new NullGenerator();
                }
                defaultGenerators.put(column.getDataType(), generator);
            }
        }
        return generator;
    }

    /**
     * @return
     */
    protected SQLAgentProvider getSqlAgentProvider() {
        return sqlAgentProvider;
    }

    /**
     * @param column
     * @return
     * @throws TypeNotSupportedException
     */
    protected DataGenerator newDefaultGenerator(Column column) throws TypeNotSupportedException {
        DataGenerator dataGenerator = null;
        String className = column.getClassName();

        switch (className) {
            case "java.lang.String":
                dataGenerator = new StringGenerator.Builder(column.getSize())
                        .setMaxSize(dataGeneratorConfig.getStringMaxSize())
                        .setDefaultSize(dataGeneratorConfig.getStringDefSize())
                        .setDefaultBoundariesEnabled(dataGeneratorConfig.isStringDefaultBoundariesEnabled())
                        .build();
                break;
            case "java.math.BigDecimal":
                dataGenerator = new BigDecimalGenerator.Builder(column.getSize(), column.getPrecision(), column.getScale())
                        .setDefaultSize(dataGeneratorConfig.getDefaultSize())
                        .setDefaultPrecision(dataGeneratorConfig.getDefaultPrecision())
                        .setMaxPrecision(dataGeneratorConfig.getMaxPrecision())
                        .setMaxScale(dataGeneratorConfig.getMaxScale())
                        .setMinScale(dataGeneratorConfig.getMinScale())
                        .setDefaultScale(dataGeneratorConfig.getDefaultScale())
                        .setMaxSize(dataGeneratorConfig.getMaxSize())
                        .setDefaultBoundariesEnabled(dataGeneratorConfig.isDecimalDefaultBoundariesEnabled())
                        .build();
                break;
            case "java.sql.Timestamp":
                dataGenerator = new TimestampGenerator();
                break;
            case "java.sql.Date":
                dataGenerator = new TimestampGenerator();
                break;
            case "java.lang.Double":
                dataGenerator = new DoubleGenerator();
                break;
            case "java.lang.Float":
                dataGenerator = new FloatGenerator();
                break;
            case "java.lang.Integer":
                dataGenerator = new IntegerGenerator();
                break;
            case "java.lang.Boolean":
                dataGenerator = new BooleanGenerator();
                break;
            case "[B":
                dataGenerator = new BytesGenerator(column.getSize());
                break;
            case "java.lang.Short":
                dataGenerator = new ShortGenerator();
                break;
            default:
                break;
        }
        if (dataGenerator == null) {
            throw new TypeNotSupportedException(column.getTypeName(), className);
        }
        return dataGenerator;
    }
}
