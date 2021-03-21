package org.geektimes.configuration.microprofile.converter;

/**
 * @author ajin
 */

public class BooleanConverter extends AbstractConverter<Boolean> {
    @Override
    protected Boolean doConvert(String value) {
        return Boolean.valueOf(value);
    }
}
