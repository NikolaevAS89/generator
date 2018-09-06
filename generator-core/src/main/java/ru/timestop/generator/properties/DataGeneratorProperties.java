package ru.timestop.generator.properties;

/**
 * @author t.i.m.e.s.t.o.p
 * @version 1.0.0
 * @since 04.09.2018
 */
public interface DataGeneratorProperties {
    /**
     * @return true if clob objects will be generated
     */
    boolean isClobEnabled();

    /**
     * @return true if blob objects will be generated
     */
    boolean isBlobEnabled();

    /**
     * @return true if boundaries of generated numbers enable
     */
    boolean isDecimalDefaultBoundariesEnabled();

    /**
     * @return true if boundaries of generated strings enable
     */
    boolean isStringDefaultBoundariesEnabled();

    /**
     * @return default number precision
     */
    int getDefaultPrecision();

    /**
     * @return upper boundary of number precision
     */
    int getMaxPrecision();

    /**
     * @return default number size
     */
    int getDefaultSize();

    /**
     * @return lower boundary of number scale
     */
    int getMinScale();

    /**
     * @return upper boundary of number scale
     */
    int getMaxScale();

    /**
     * @return
     */
    int getMaxSize();

    /**
     * @return
     */
    int getDefaultScale();


    int getStringMaxSize();

    int getStringDefSize();
}
