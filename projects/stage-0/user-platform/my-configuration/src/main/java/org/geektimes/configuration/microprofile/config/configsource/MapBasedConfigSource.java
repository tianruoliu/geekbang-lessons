package org.geektimes.configuration.microprofile.config.configsource;

import org.eclipse.microprofile.config.spi.ConfigSource;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 基于Map数据结构{@link ConfigSource}实现
 *
 * @author ajin
 */

public abstract class MapBasedConfigSource implements ConfigSource {

    private final String name;
    private final int    ordinal;

    private final Map<String, String> source;


    public MapBasedConfigSource(String name, int ordinal) {
        this.name = name;
        this.ordinal = ordinal;
        this.source = getProperties();
    }


    /**
     * 设置成final，不让子类魔改
     *
     * @return 不可变 Map 类型的配置数据
     */
    @Override
    public final Map<String, String> getProperties() {
        Map<String, String> configData = new HashMap<>(16);

        try {
            prepareConfigData(configData);
        } catch (Throwable e) {
            throw new IllegalStateException("准备配置数据发生错误", e);
        }

        return Collections.unmodifiableMap(configData);
    }

    /**
     * 准备配置数据
     *
     * @param configData 配置数据
     * @throws Throwable
     */
    protected abstract void prepareConfigData(Map<String, String> configData) throws Throwable;

    @Override
    public Set<String> getPropertyNames() {
        return source.keySet();
    }

    @Override
    public String getValue(String propertyName) {
        return source.get(propertyName);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getOrdinal() {
        return ordinal;
    }

}
