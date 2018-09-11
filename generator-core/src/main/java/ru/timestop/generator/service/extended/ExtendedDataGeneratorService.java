package ru.timestop.generator.service.extended;

import ru.timestop.generator.data.DataGenerator;

/**
 * @author t.i.m.e.s.t.o.p
 * @version 1.0.0
 * @since 28.08.2018
 */
public interface ExtendedDataGeneratorService {

    /**
     * @param columnId
     * @return
     */
    DataGenerator get(String columnId);
}
