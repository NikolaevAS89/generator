package ru.timestop.generator.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author t.i.m.e.s.t.o.p
 * @version 1.0.0
 * @since 04.09.2018
 */
@Component
public class DataGeneratorPropertiesImpl implements DataGeneratorProperties {

    @Value("${generator.data.clob.enable:false}")
    private boolean isClobEnabled;

    @Value("${generator.data.blob.enable:false}")
    private boolean isBlobEnabled;

    @Value("${generator.data.bigdecimal.boundary.enable:true}")
    private boolean isDefaultBoundariesEnabled;

    @Value("${generator.data.bigdecimal.precision.default:18}")
    private int defaultPrecision;

    @Value("${generator.data.bigdecimal.precision.max:22}")
    private int maxPrecision;

    @Value("${generator.data.bigdecimal.size.default:18}")
    private int defaultSize;

    @Value("${generator.data.bigdecimal.scale.min:-20}")
    private int minScale;

    @Value("${generator.data.bigdecimal.scale.max:999}")
    private int maxScale;

    @Value("${generator.data.bigdecimal.size.max:36}")
    private int maxSize;

    @Value("${generator.data.bigdecimal.scale.default:2}")
    private int defaultScale;

    @Value("${generator.data.string.size.max:100}")
    private int stringMaxSize;

    @Value("${generator.data.string.size.default:20}")
    private int stringDefSize;

    @Value("${generator.data.string.boundary.enable:true}")
    private boolean isStrDefaultBoundariesEnabled;

    @Override
    public boolean isClobEnabled() {
        return isClobEnabled;
    }

    @Override
    public boolean isBlobEnabled() {
        return isBlobEnabled;
    }

    @Override
    public boolean isDecimalDefaultBoundariesEnabled() {
        return isDefaultBoundariesEnabled;
    }

    @Override
    public int getDefaultPrecision() {
        return defaultPrecision;
    }

    @Override
    public int getDefaultSize() {
        return defaultSize;
    }

    @Override
    public int getMinScale() {
        return minScale;
    }

    @Override
    public int getMaxScale() {
        return maxScale;
    }

    @Override
    public int getMaxSize() {
        return maxSize;
    }

    @Override
    public int getDefaultScale() {
        return defaultScale;
    }

    @Override
    public int getStringMaxSize() {
        return stringMaxSize;
    }

    @Override
    public int getStringDefSize() {
        return stringDefSize;
    }

    @Override
    public boolean isStringDefaultBoundariesEnabled() {
        return isStrDefaultBoundariesEnabled;
    }

    @Override
    public int getMaxPrecision() {
        return maxPrecision;
    }
}
