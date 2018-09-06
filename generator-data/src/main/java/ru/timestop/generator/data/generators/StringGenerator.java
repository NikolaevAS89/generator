package ru.timestop.generator.data.generators;

import org.apache.log4j.Logger;
import ru.timestop.generator.data.AbstractGenerator;

/**
 * Random data generator for String
 *
 * @author t.i.m.e.s.t.o.p
 * @version 1.0.0
 * @since 24.08.2018
 */
public class StringGenerator extends AbstractGenerator {
    private static final Logger LOG = Logger.getLogger(StringGenerator.class);
    private final static int ALPHABET_FULL_SIZE = 126;
    private final static int ALPHABET_SIZE = 62;
    private final static char[] alphabet;
    private char[] core;

    static {
        LOG.debug("Load alphabet...");
        alphabet = new char[ALPHABET_FULL_SIZE];
        for (int i = 48, j = 0; i < 1104; i++) {
            alphabet[j++] = (char) i;
            if (i == 57) {
                i += 7;
            }
            if (i == 90) {
                i += 6;
            }
            if (i == 122) {
                i += 917;
            }
        }
    }

    public StringGenerator(int length) {
        core = new char[length];
        for (int i = 0; i < length; i++) {
            core[i] = nextChar();
        }
    }

    private char nextChar() {
        return alphabet[Math.abs(rand.nextInt()) % ALPHABET_SIZE];
    }

    @Override
    public Object next() {
        int size = (20 > core.length) ? (core.length) : (20);
        for (int i = 0; i < size; i++) {
            core[i] = nextChar();
        }
        return new String(core, 0, core.length);
    }

    @Override
    public String toString() {
        return this.getClass().getName() + "(" + core.length + ")";
    }

    public static class Builder {
        private final Integer sizeFromDb;
        private int maxSize;
        private int defaultSize;
        private boolean isDefaultBoundariesEnabled;

        public Builder(Integer sizeFromDb) {
            this.sizeFromDb = sizeFromDb;
        }

        public Builder setMaxSize(int maxSize) {
            this.maxSize = maxSize;
            return this;
        }

        public Builder setDefaultSize(int defaultSize) {
            this.defaultSize = defaultSize;
            return this;
        }

        public Builder setDefaultBoundariesEnabled(boolean defaultBoundariesEnabled) {
            isDefaultBoundariesEnabled = defaultBoundariesEnabled;
            return this;
        }

        public StringGenerator build() {
            // Если из БД получен 0, заменить его на значение по умолчанию
            int realSize = (sizeFromDb == null || sizeFromDb == 0) ? defaultSize : sizeFromDb;

            if (isDefaultBoundariesEnabled) {
                // Size не должен превышать maxSize
                realSize = realSize > maxSize ? maxSize : realSize;
            }

            return new StringGenerator(realSize);
        }
    }
}
