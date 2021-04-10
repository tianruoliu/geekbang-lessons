package org.geektimes.cache.integration;

import javax.cache.Cache;
import javax.cache.integration.CacheLoaderException;
import javax.cache.integration.CacheWriterException;
import java.util.List;
import java.util.ServiceLoader;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Composite multiple {@link FallbackStorage}s that instantiated by {@link ServiceLoader Java SPI}.
 *
 * @author ajin
 */

public class CompositeFallbackStorage extends AbstractFallbackStorage<Object, Object> {

    private static final ConcurrentMap<ClassLoader, List<FallbackStorage>> fallbackStoragesCache =
        new ConcurrentHashMap<>();

    private final        List<FallbackStorage>                             fallbackStorages;

    public CompositeFallbackStorage(ClassLoader classLoader) {
        super(Integer.MIN_VALUE);
        this.fallbackStorages = fallbackStoragesCache.computeIfAbsent(classLoader, this::loadFallbackStorages);
    }

    public CompositeFallbackStorage() {
        this(Thread.currentThread().getContextClassLoader());
    }

    /**
     * 获取当前ClassLoader通过SPI机制加载的FallbackStorage实例
     *
     * @param classLoader 类加载器
     */
    private List<FallbackStorage> loadFallbackStorages(ClassLoader classLoader) {
        return StreamSupport.stream(ServiceLoader.load(FallbackStorage.class, classLoader).spliterator(), false).sorted(
            PRIORITY_COMPARATOR).collect(Collectors.toList());
    }

    @Override
    public Object load(Object key) throws CacheLoaderException {
        Object value = null;

        for (FallbackStorage fallbackStorage : fallbackStorages) {
            value = fallbackStorage.load(key);
            if (null != value) {
                break;
            }
        }
        return value;
    }

    @Override
    public void write(Cache.Entry<?, ?> entry) throws CacheWriterException {
        fallbackStorages.forEach(fallbackStorage -> fallbackStorage.write(entry));
    }

    @Override
    public void delete(Object key) throws CacheWriterException {
        fallbackStorages.forEach(fallbackStorage -> fallbackStorage.delete(key));
    }
}
