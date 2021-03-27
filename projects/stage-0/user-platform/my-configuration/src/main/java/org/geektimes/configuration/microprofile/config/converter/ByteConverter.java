package org.geektimes.configuration.microprofile.config.converter;

/**
 * @author ajin
 */

public class ByteConverter extends AbstractConverter<Byte> {
    @Override
    protected Byte doConvert(String value) {
        return Byte.valueOf(value);
    }
}
