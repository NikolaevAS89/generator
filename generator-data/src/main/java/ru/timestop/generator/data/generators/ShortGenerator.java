package ru.timestop.generator.data.generators;

import ru.timestop.generator.data.AbstractGenerator;

/**
 * @author t.i.m.e.s.t.o.p
 * @version 1.0.0
 * @since 24.08.2018
 */
public class ShortGenerator extends AbstractGenerator {

    private Short MAX_VALUE;

    public ShortGenerator(short maxValue) {
        MAX_VALUE = maxValue;
    }

    public ShortGenerator() {
    }

    @Override
    public Object next() {
        if (MAX_VALUE == null)
            return (short) rand.nextInt(Short.MAX_VALUE + 1);
        else
            return (short) rand.nextInt(MAX_VALUE);
    }
}
