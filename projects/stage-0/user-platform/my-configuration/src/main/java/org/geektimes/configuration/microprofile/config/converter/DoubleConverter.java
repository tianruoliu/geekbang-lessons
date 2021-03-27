package org.geektimes.configuration.microprofile.config.converter;

/**
 * @author ajin
 */

public class DoubleConverter extends AbstractConverter<Double> {
    @Override
    protected Double doConvert(String value) {
        return Double.valueOf(value);
    }
}
