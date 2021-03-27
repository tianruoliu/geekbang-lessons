package org.geektimes.configuration.microprofile.config.converter;

/**
 * @author ajin
 */

public class LongConverter extends AbstractConverter<Long> {
    @Override
    protected Long doConvert(String value) {
        return Long.valueOf(value);
    }
}
