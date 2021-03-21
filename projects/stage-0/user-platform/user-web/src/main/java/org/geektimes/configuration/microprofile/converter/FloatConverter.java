package org.geektimes.configuration.microprofile.converter;

/**
 * @author ajin
 */

public class FloatConverter extends AbstractConverter<Float> {
    @Override
    protected Float doConvert(String value) {
        return Float.valueOf(value);
    }
}
