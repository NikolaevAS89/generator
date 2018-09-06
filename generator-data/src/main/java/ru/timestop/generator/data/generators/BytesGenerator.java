package ru.timestop.generator.data.generators;

import ru.timestop.generator.data.AbstractGenerator;

/**
 * Random data generator for byte array
 *
 * @author t.i.m.e.s.t.o.p
 * @version 1.0.0
 * @since 24.08.2018
 */
public class BytesGenerator extends AbstractGenerator {
    private int realSize;
    private final static byte[] alphabet;

    static {
        alphabet = new byte[128];
        for (int i = 0; i < 128; i++) {
            alphabet[i] = (byte) i;
        }
    }

    public BytesGenerator(int size) {
        realSize = (size > 100) ? (100) : size;
    }

    @Override
    public Object next() {
        byte[] buff = new byte[realSize];
        for (int i = 0; i < realSize; i++) {
            buff[i] = nextChar();
        }
        return buff;
    }

    @Override
    public String toString() {
        return BytesGenerator.class.getName() + "(" + realSize + ")";
    }

    private byte nextChar() {
        return alphabet[Math.abs(rand.nextInt()) % alphabet.length];
    }
}
