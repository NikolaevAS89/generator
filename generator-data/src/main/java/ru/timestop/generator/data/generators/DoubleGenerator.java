package ru.timestop.generator.data.generators;

import ru.timestop.generator.data.AbstractGenerator;

/**
 * Random data generator for double
 *
 * @author t.i.m.e.s.t.o.p
 * @version 1.0.0
 * @since 24.08.2018
 */
public class DoubleGenerator extends AbstractGenerator {

    @Override
    public Object next() {
        return rand.nextDouble();
    }
}
