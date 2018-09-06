package ru.timestop.generator.service.datagenerator;

import ru.timestop.generator.data.DataGenerator;

/**
 * @author t.i.m.e.s.t.o.p
 * @version 1.0.0
 * @since 24.08.2018
 */
public interface DataGeneratorService {

    /**
     * @param columnId
     * @return generator of field values, if type not supported return Nulls generator
     */
    DataGenerator get(String columnId);
}
