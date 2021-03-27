package org.geektimes.configuration.microprofile.config.converter;

/**
 * @author ajin
 */

public class BooleanConverter extends AbstractConverter<Boolean> {
    @Override
    protected Boolean doConvert(String value) {
        return Boolean.valueOf(value);
    }
}
