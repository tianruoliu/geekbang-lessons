package org.geektimes.configuration.microprofile.config.converter;

import org.eclipse.microprofile.config.spi.Converter;

/**
 * @author ajin
 */

public abstract class AbstractConverter<T> implements Converter<T> {
    @Override
    public T convert(String value) throws IllegalArgumentException, NullPointerException {
        if (value==null){
            throw new IllegalArgumentException("the value must not be null!");
        }
        return doConvert(value);
    }

    protected abstract T doConvert(String value) ;
}
