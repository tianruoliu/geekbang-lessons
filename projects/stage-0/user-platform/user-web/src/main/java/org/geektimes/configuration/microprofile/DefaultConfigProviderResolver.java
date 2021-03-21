package org.geektimes.configuration.microprofile;

import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.spi.ConfigBuilder;
import org.eclipse.microprofile.config.spi.ConfigProviderResolver;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * {@link ConfigProviderResolver}默认实现
 *
 * @author ajin
 */

public class DefaultConfigProviderResolver extends ConfigProviderResolver {

    private ConcurrentMap<ClassLoader, Config> configsRepository = new ConcurrentHashMap<>();

    @Override
    public Config getConfig() {
        return getConfig(null);
    }

    @Override
    public Config getConfig(ClassLoader loader) {
        return configsRepository.computeIfAbsent(resolveClassLoader(loader), this::newConfig);
        // ClassLoader classLoader = loader;
        // if (null == classLoader) {
        //     classLoader = Thread.currentThread().getContextClassLoader();
        // }
        //
        // ServiceLoader<Config> serviceLoader = ServiceLoader.load(Config.class, classLoader);
        // Iterator<Config>      iterator      = serviceLoader.iterator();
        // while (iterator.hasNext()) {
        //     return iterator.next();
        // }
        // throw new IllegalStateException("No Config implementation found!");
    }

    //    private Config loadConfig(ClassLoader classLoader) {
    //        ServiceLoader<Config> serviceLoader = ServiceLoader.load(Config.class, classLoader);
    //        Iterator<Config> iterator = serviceLoader.iterator();
    //        if (iterator.hasNext()) {
    //            // 获取 Config SPI 第一个实现
    //            return iterator.next();
    //        }
    //        throw new IllegalStateException("No Config implementation found!");
    //    }

    private ClassLoader resolveClassLoader(ClassLoader classLoader) {
        return null == classLoader ? this.getClass().getClassLoader() : classLoader;
    }

    protected ConfigBuilder newConfigBuilder(ClassLoader classLoader) {
        return new DefaultConfigBuilder(resolveClassLoader(classLoader));
    }

    protected Config newConfig(ClassLoader classLoader) {
        return newConfigBuilder(classLoader).build();
    }

    @Override
    public ConfigBuilder getBuilder() {
        return null;
    }

    @Override
    public void registerConfig(Config config, ClassLoader classLoader) {
        configsRepository.put(classLoader, config);
    }

    @Override
    public void releaseConfig(Config config) {
        List<ClassLoader> targetKeys = new LinkedList<>();

        for (Map.Entry<ClassLoader, Config> configEntry : configsRepository.entrySet()) {
            if (Objects.equals(config, configEntry.getValue())) {
                targetKeys.add(configEntry.getKey());
            }
        }

        targetKeys.forEach(configsRepository::remove);
    }
}
