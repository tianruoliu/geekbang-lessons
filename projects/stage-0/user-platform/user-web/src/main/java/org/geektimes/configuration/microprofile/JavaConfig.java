package org.geektimes.configuration.microprofile;

import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.ConfigValue;
import org.eclipse.microprofile.config.spi.ConfigSource;
import org.eclipse.microprofile.config.spi.Converter;

import java.util.*;

/**
 * @author ajin
 */

public class JavaConfig implements Config {

    private List<ConfigSource> configSources = new LinkedList<>();

    private static Comparator<ConfigSource> configSourceComparator = (o1, o2) -> Integer.compare(o2.getOrdinal(),
        o1.getOrdinal());

    public JavaConfig() {
        ClassLoader                 classLoader   = getClass().getClassLoader();
        ServiceLoader<ConfigSource> serviceLoader = ServiceLoader.load(ConfigSource.class, classLoader);
        serviceLoader.forEach(configSources::add);
        // 排序
        configSources.sort(configSourceComparator);
    }

    /**
     * TODO
     */
    @Override
    public <T> T getValue(String propertyName, Class<T> propertyType) {
        String propertyValue = getPropertyValue(propertyName);
        // String转换成目标类型
        return null;
    }

    @Override
    public ConfigValue getConfigValue(String s) {
        return null;
    }

    @Override
    public <T> Optional<T> getOptionalValue(String propertyName, Class<T> propertyType) {
        T value = getValue(propertyName, propertyType);
        return Optional.ofNullable(value);
    }

    @Override
    public Iterable<String> getPropertyNames() {

        return null;
    }

    @Override
    public Iterable<ConfigSource> getConfigSources() {
        return Collections.unmodifiableList(configSources);
    }

    @Override
    public <T> Optional<Converter<T>> getConverter(Class<T> aClass) {
        return Optional.empty();
    }

    @Override
    public <T> T unwrap(Class<T> aClass) {
        return null;
    }

    /**
     * 根据propertyName获取propertyValue
     *
     * @param propertyName 属性名
     */
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
}
