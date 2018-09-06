package ru.timestop.generator.data;

import java.util.Iterator;

/**
 * @author t.i.m.e.s.t.o.p
 * @version 1.0.0
 * @since 24.08.2018
 */
public interface DataGenerator extends Iterator {

    /**
     * Generate random value
     *
     * @return random value
     */
    Object next();
}
