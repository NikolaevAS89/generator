package ru.timestop.generator.data;

import java.util.Random;

/**
 * @author t.i.m.e.s.t.o.p
 * @version 1.0.0
 * @since 24.08.2018
 */
public abstract class AbstractGenerator implements DataGenerator {
    /**
     * Core of random
     */
    protected Random rand = new Random();

    /**
     * Next random value is always available
     *
     * @return true
     */
    @Override
    public boolean hasNext() {
        return true;
    }

    @Override
    public String toString() {
        return this.getClass().getName();
    }
}
