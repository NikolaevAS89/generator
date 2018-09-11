package ru.timestop.generator.service.extended;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.timestop.generator.IOUtilites;
import ru.timestop.generator.data.DataGenerator;
import ru.timestop.generator.properties.GeneratorProperties;
import ru.timestop.generator.service.SQLAgentProvider;

import javax.annotation.PostConstruct;
import java.lang.ref.SoftReference;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

/**
 * @author t.i.m.e.s.t.o.p
 * @version 1.0.0
 * @since 28.08.2018
 */
@Component
public class ExtendedDataGeneratorServiceImpl implements ExtendedDataGeneratorService {

    private final static Logger LOG = Logger.getLogger(ExtendedDataGeneratorServiceImpl.class);

    @Autowired
    private GeneratorProperties properties;

    @Autowired
    private SQLAgentProvider provider;

    private Map<String, String> binds;

    private final Map<String, SoftReference<DataGenerator>> cache = new HashMap<>();

    @PostConstruct
    public void init() {
        Parser parser = new Parser();
        IOUtilites.load(properties.getExtendedGeneratorPath(), parser);
        binds = parser.getGenerators();
    }

    @Override
    public DataGenerator get(String columnId) {
        DataGenerator result = null;
        SoftReference<DataGenerator> generatorRef = cache.get(columnId);
        DataGenerator generator = null;
        if (generatorRef != null) {
            generator = generatorRef.get();
        }
        if (generator == null) {
            String className = binds.get(columnId);
            if (className != null) {
                try {
                    Class generatorClass = Class.forName(className);
                    generator = (DataGenerator) provider.registerNewBean(generatorClass);
                    SoftReference<DataGenerator> newGeneratorRef = new SoftReference<>(generator);
                    cache.put(columnId, newGeneratorRef);
                    result = generator;
                } catch (ClassNotFoundException e) {
                    LOG.warn(e);
                }
            }
        } else {
            result = generator;
        }
        return result;
    }

    /**
     *
     */
    private class Parser implements Consumer<String> {

        private final Map<String, String> generators = new HashMap<>();

        Map<String, String> getGenerators() {
            return generators;
        }

        @Override
        public void accept(String line) {
            String[] parts = line.split("\\|");
            String[] column = parts[0].split("\\.");
            if (parts.length != 2 || column.length != 3) {
                LOG.warn("In line \"" + line + "\" there is not valid column description, line will be skipped");
            } else {
                generators.put(parts[0], parts[1]);
                LOG.info(Arrays.toString(parts) + " loaded...");
            }
        }
    }
}
