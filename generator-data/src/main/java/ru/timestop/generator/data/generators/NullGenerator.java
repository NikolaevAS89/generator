package ru.timestop.generator.data.generators;

import ru.timestop.generator.data.DataGenerator;

/**
 * null data generator
 *
 * @author t.i.m.e.s.t.o.p
 * @version 1.0.0
 * @since 24.08.2018
 */
public class NullGenerator implements DataGenerator {

    @Override
    public boolean hasNext() {
        return true;
    }

    @Override
    public Object next() {
        return null;
    }

    @Override
    public String toString() {
        return this.getClass().getName();
    }
}
