package org.geektimes.cache.integration;

import javax.cache.Cache;
import javax.cache.integration.CacheLoaderException;
import javax.cache.integration.CacheWriterException;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Abstract {@link FallbackStorage} implementation.
 *
 * @author ajin
 */

public abstract class AbstractFallbackStorage<K, V> implements FallbackStorage<K, V> {

    private final int priority;

    protected AbstractFallbackStorage(int priority) {
        this.priority = priority;
    }

    /**
     * 获取优先级
     */
    @Override
    public int getPriority() {
        return priority;
    }

    @Override
    public Map<K, V> loadAll(Iterable<? extends K> keys) throws CacheLoaderException {
        Map<K, V> map = new LinkedHashMap<>();
        for (K key : keys) {
            // load(key)是抽象方法，交给子类去实现
            map.put(key, load(key));
        }
        return map;
    }

    @Override
    public void writeAll(Collection<Cache.Entry<? extends K, ? extends V>> entries) throws CacheWriterException {
        entries.forEach(this::write);

    }

    @Override
    public void deleteAll(Collection<?> keys) throws CacheWriterException {
        keys.forEach(this::delete);
    }
}
