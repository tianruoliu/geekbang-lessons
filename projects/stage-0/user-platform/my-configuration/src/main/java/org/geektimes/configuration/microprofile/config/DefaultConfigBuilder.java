package org.geektimes.configuration.microprofile.config;

import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.spi.ConfigBuilder;
import org.eclipse.microprofile.config.spi.ConfigSource;
import org.eclipse.microprofile.config.spi.Converter;
import org.geektimes.configuration.microprofile.config.configsource.ConfigSources;
import org.geektimes.configuration.microprofile.config.converter.Converters;

/**
 * {@link ConfigBuilder} 默认实现
 *
 * @author ajin
 */

public class DefaultConfigBuilder implements ConfigBuilder {

    private final ConfigSources configSources;
    private final Converters    converters;

    public DefaultConfigBuilder(ClassLoader classLoader) {
        this.configSources = new ConfigSources(classLoader);
        this.converters = new Converters(classLoader);
    }

    @Override
    public ConfigBuilder addDefaultSources() {
        this.configSources.addDefaultConfigSources();
        return this;
    }

    @Override
    public ConfigBuilder addDiscoveredSources() {
        this.configSources.addDiscoveredConfigSources();
        return this;
    }

    @Override
    public ConfigBuilder addDiscoveredConverters() {
        this.converters.addDiscoveredConverters();
        return this;
    }

    @Override
    public ConfigBuilder forClassLoader(ClassLoader loader) {
        this.converters.setClassLoader(loader);
        this.configSources.setClassLoader(loader);
        return this;
    }

    @Override
    public ConfigBuilder withSources(ConfigSource... sources) {
        configSources.addConfigSources(sources);
        return this;
    }

    @Override
    public ConfigBuilder withConverters(Converter<?>... converters) {
        this.converters.addConverters(converters);
        return this;
    }

    @Override
    public <T> ConfigBuilder withConverter(Class<T> type, int priority, Converter<T> converter) {
        this.converters.addConverter(converter, priority, type);
        return this;
    }

    @Override
    public Config build() {
        Config config = new DefaultConfig(configSources, converters);
        return config;
    }
}
