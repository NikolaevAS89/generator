package ru.timestop.generator.data.generators;

import ru.timestop.generator.data.AbstractGenerator;

import java.math.BigDecimal;

/**
 * @author t.i.m.e.s.t.o.p
 * @version 1.0.0
 * @since 24.08.2018
 */
public class BigDecimalGenerator extends AbstractGenerator {
    private final int initSize;
    private final int initPrecision;
    private final int initScale;

    private final static char[] alphabet;

    // Алфавит инициализируется значениями от '0' до '9'
    static {
        alphabet = new char[10];
        for (int i = 48, j = 0; i < 58; i++) {
            alphabet[j++] = (char) i;
        }
    }

    @Override
    public Object next() {
        int len;
        int size = initSize;
        int precision = this.initPrecision;
        int scale = this.initScale;
        byte[] scaleAsBytes = String.valueOf(Math.abs(scale)).getBytes();
        char[] bigDecimalBuilder;

        if (scale == 0 || size < 6) {
            len = precision;
            bigDecimalBuilder = new char[len];
            for (int i = 0; i < len; i++) bigDecimalBuilder[i] = nextNumeric();

        } else {
            if (size < 2 + scaleAsBytes.length + precision) {
                precision = size - 2 - scaleAsBytes.length;
            }
            if (precision > 0) {
                len = 2 + scaleAsBytes.length + precision;
                bigDecimalBuilder = new char[len];

                for (int i = 0; i < precision; i++) bigDecimalBuilder[i] = nextNumeric();
                bigDecimalBuilder[precision] = 'E';
                bigDecimalBuilder[precision + 1] = (scale > 0) ? '-' : '+';
                for (int i = 0; i < scaleAsBytes.length; i++) {
                    bigDecimalBuilder[precision + 2 + i] = (char) scaleAsBytes[i];
                }
            } else {
                len = precision + 1;
                bigDecimalBuilder = new char[len];

                for (int i = 0; i <= len; i++) bigDecimalBuilder[i] = nextNumeric();

            }
        }
        String res = new String(bigDecimalBuilder, 0, len);
        return new BigDecimal(res);
    }

    public String toString() {
        return BigDecimalGenerator.class.getName() + "(" + initSize + "," + initPrecision + "," + initScale + ")";
    }

    /**
     * @param initSize
     * @param initPrecision
     * @param initScale
     */
    private BigDecimalGenerator(int initSize, int initPrecision, int initScale) {
        this.initSize = initSize;
        this.initPrecision = initPrecision;
        this.initScale = initScale;
    }

    /**
     * @return случайное число от '0' до '9'
     */
    private char nextNumeric() {
        return alphabet[Math.abs(rand.nextInt(101)) % 10];
    }

    public static class Builder {
        private final Integer sizeFromDb;
        private final Integer precisionFromDb;
        private final Integer scaleFromDb;
        private int minScale;
        private int maxScale;
        private int maxSize;
        private int defaultScale;
        private int defaultSize;
        private int defaultPrecision;
        private int maxPrecision;
        private boolean isDefaultBoundariesEnabled;

        public Builder(Integer sizeFromDb, Integer precisionFromDb, Integer scaleFromDb) {
            this.sizeFromDb = sizeFromDb;
            this.precisionFromDb = precisionFromDb;
            this.scaleFromDb = scaleFromDb;
        }

        public Builder setMinScale(int minScale) {
            this.minScale = minScale;
            return this;
        }

        public Builder setMaxScale(int maxScale) {
            this.maxScale = maxScale;
            return this;
        }

        public Builder setDefaultSize(int defaultSize) {
            this.defaultSize = defaultSize;
            return this;
        }

        public Builder setMaxSize(int maxSize) {
            this.maxSize = maxSize;
            return this;
        }

        public Builder setDefaultPrecision(int defaultPrecision) {
            this.defaultPrecision = defaultPrecision;
            return this;
        }

        public Builder setDefaultScale(int defaultScale) {
            this.defaultScale = defaultScale;
            return this;
        }

        public Builder setDefaultBoundariesEnabled(boolean isDefaultBoundariesEnabled) {
            this.isDefaultBoundariesEnabled = isDefaultBoundariesEnabled;
            return this;
        }

        public Builder setMaxPrecision(int maxPrecision) {
            this.maxPrecision = maxPrecision;
            return this;
        }

        public BigDecimalGenerator build() {
            int realSize = (sizeFromDb == null || sizeFromDb == 0) ? defaultSize : sizeFromDb; // Если из БД получен 0, заменить его на значение по умолчанию

            int realPrecision = (precisionFromDb == null || precisionFromDb == 0) ? defaultPrecision : precisionFromDb; // Если из БД получен 0, заменить его на значение по умолчанию

            int realScale = (scaleFromDb == null || scaleFromDb == -127) ? defaultScale : scaleFromDb;

            if (isDefaultBoundariesEnabled) {
                // Size не должен превышать maxSize
                realSize = realSize > maxSize ? maxSize : realSize;
                // Если scaleFromDb в границах min, max (включительно), берем scaleFromDb
                // Если scaleFromDb > max, берем max; если scaleFromDb < min, берем min
                realScale = (realScale > maxScale ? 1 : 0) * maxScale
                        + (realScale < minScale ? 1 : 0) * minScale
                        + (realScale >= minScale && realScale <= maxScale ? realScale : 0);
                realPrecision = realPrecision > maxPrecision ? maxPrecision : realPrecision;
            }
            realPrecision = realPrecision > realSize ? realSize : realPrecision; // Precision не может превышать initSize

            return new BigDecimalGenerator(realSize, realPrecision, realScale);
        }
    }
}