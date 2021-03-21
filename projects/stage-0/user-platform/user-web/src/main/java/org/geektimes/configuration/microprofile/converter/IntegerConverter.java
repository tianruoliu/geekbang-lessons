package org.geektimes.configuration.microprofile.converter;

/**
 * @author ajin
 */

public class IntegerConverter extends AbstractConverter<Integer> {
    @Override
    protected Integer doConvert(String value) {
        return Integer.valueOf(value);
    }
}
