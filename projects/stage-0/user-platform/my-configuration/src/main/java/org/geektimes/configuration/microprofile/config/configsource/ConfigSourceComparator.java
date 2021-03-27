package org.geektimes.configuration.microprofile.config.configsource;

import org.eclipse.microprofile.config.spi.ConfigSource;

import java.util.Comparator;

/**
 * {@link ConfigSource}优先级比较器
 *
 * @author ajin
 */
public class ConfigSourceComparator implements Comparator<ConfigSource> {

    /**
     * 单例模式
     */
    public static final Comparator<ConfigSource> INSTANCE = new ConfigSourceComparator();

    @Override
    public int compare(ConfigSource o1, ConfigSource o2) {
        return Integer.compare(o2.getOrdinal(),o1.getOrdinal());
    }
}
