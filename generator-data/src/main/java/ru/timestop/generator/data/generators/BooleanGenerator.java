package ru.timestop.generator.data.generators;

import ru.timestop.generator.data.DataGenerator;

/**
 * data generator for boolean
 *
 * @author t.i.m.e.s.t.o.p
 * @version 1.0.0
 * @since 24.08.2018
 */
public class BooleanGenerator implements DataGenerator {

    private int i = 0;

    @Override
    public boolean hasNext() {
        return true;
    }

    @Override
    public Object next() {
        return i++ % 2 == 1;
    }

    public String toString() {
        return this.getClass().getName();
    }
}
