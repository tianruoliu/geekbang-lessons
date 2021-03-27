package org.geektimes.configuration.microprofile.config;

import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.ConfigValue;
import org.eclipse.microprofile.config.spi.ConfigSource;
import org.eclipse.microprofile.config.spi.Converter;
import org.geektimes.configuration.microprofile.config.configsource.ConfigSources;
import org.geektimes.configuration.microprofile.config.converter.Converters;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static java.util.stream.StreamSupport.stream;

/**
 * {@link Config}默认实现
 *
 * @author ajin
 */

class DefaultConfig implements Config {

    private final ConfigSources configSources;
    private final Converters    converters;

    DefaultConfig(ConfigSources configSources, Converters converters) {
        this.configSources = configSources;
        this.converters = converters;
    }

    @Override
    public <T> T getValue(String propertyName, Class<T> propertyType) {
        String       propertyValue = getPropertyValue(propertyName);
        Converter<T> converter     = doGetConverters(propertyType);
        return converter == null ? null : converter.convert(propertyValue);
    }

    protected String getPropertyValue(String propertyName) {
        String propertyValue = null;
        for (ConfigSource configSource : configSources) {
            propertyValue = configSource.getValue(propertyName);
            if (null != propertyValue) {
                break;
            }
        }
        return propertyValue;
    }

    @Override
    public ConfigValue getConfigValue(String propertyName) {
        return null;
    }

    @Override
    public <T> Optional<T> getOptionalValue(String propertyName, Class<T> propertyType) {
        T value = getValue(propertyName, propertyType);
        return Optional.ofNullable(value);
    }

    @Override
    public Iterable<String> getPropertyNames() {
        return stream(configSources.spliterator(), false).map(ConfigSource::getPropertyNames).collect(
            LinkedHashSet::new, Set::addAll, Set::addAll);
    }

    @Override
    public Iterable<ConfigSource> getConfigSources() {
        return configSources;
    }

    @Override
    public <T> Optional<Converter<T>> getConverter(Class<T> forType) {
        Converter<T> converter = doGetConverters(forType);
        // Optional.ofNullable()
        return converter == null ? Optional.empty() : Optional.of(converter);
    }

    protected <T> Converter<T> doGetConverters(Class<T> forType) {
        List<Converter> converters = this.converters.getConverters(forType);
        // 如果获取到Converter，那么就会获取优先级最高的那个
        return converters.isEmpty() ? null : converters.get(0);
    }

    @Override
    public <T> T unwrap(Class<T> type) {
        return null;
    }
}
