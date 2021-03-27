package org.geektimes.configuration.microprofile.config.configsource;

import org.eclipse.microprofile.config.spi.ConfigSource;

import java.util.*;
import java.util.stream.Stream;

/**
 * @author ajin
 * @see ConfigSource
 * @see ClassLoader
 */

public class ConfigSources implements Iterable<ConfigSource> {
    /**
     * 是否添加内建 配置源
     */
    private boolean addedDefaultConfigSources = false;

    /**
     * 是否添加 外部程序创建的配置源
     */
    private boolean addedDiscoveredConfigSources = false;

    private List<ConfigSource> configSourceList = new LinkedList<>();

    private ClassLoader classLoader;

    public ConfigSources(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    public boolean isAddedDefaultConfigSources() {
        return addedDefaultConfigSources;
    }

    public boolean isAddedDiscoveredConfigSources() {
        return addedDiscoveredConfigSources;
    }

    public ClassLoader getClassLoader() {
        return classLoader;
    }

    public void setClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    /**
     * 创建当前程序创建的默认配置源
     */
    public void addDefaultConfigSources() {
        if (addedDefaultConfigSources) {
            return;
        }
        addConfigSources(JavaSystemPropertiesConfigSource.class, OperationSystemEnvironmentVariablesConfigSource.class,
            DefaultResourceConfigSource.class);

        addedDefaultConfigSources = true;
    }

    public void addDiscoveredConfigSources() {
        if (addedDiscoveredConfigSources) {
            return;
        }
        // SPI扩展的配置源
        addConfigSources(ServiceLoader.load(ConfigSource.class,classLoader));


        addedDiscoveredConfigSources = true;
    }

    public void addConfigSources(Class<? extends ConfigSource>... configSourceClasses) {
        addConfigSources(Stream.of(configSourceClasses).map(this::newInstance).toArray(ConfigSource[]::new));
    }

    public void addConfigSources(ConfigSource... configSources) {
        addConfigSources(Arrays.asList(configSources));
    }

    public void addConfigSources(Iterable<ConfigSource> configSources) {
        configSources.forEach(this.configSourceList::add);
        // 排序
        this.configSourceList.sort(ConfigSourceComparator.INSTANCE);
    }

    private ConfigSource newInstance(Class<? extends ConfigSource> configSourceClasses) {
        ConfigSource configSource;
        try {
            configSource = configSourceClasses.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new IllegalStateException(e);
        }

        return configSource;
    }

    @Override
    public Iterator<ConfigSource> iterator() {
        return this.configSourceList.iterator();
    }
}
