package org.geektimes.configuration.microprofile.converter;

/**
 * @author ajin
 */

public class ByteConverter extends AbstractConverter<Byte> {
    @Override
    protected Byte doConvert(String value) {
        return Byte.valueOf(value);
    }
}
