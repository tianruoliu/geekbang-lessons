package org.geektimes.configuration.microprofile.config.converter;

/**
 * @author ajin
 */

public class ShortConverter extends AbstractConverter<Short> {
    @Override
    protected Short doConvert(String value) {
        return Short.valueOf(value);
    }
}
