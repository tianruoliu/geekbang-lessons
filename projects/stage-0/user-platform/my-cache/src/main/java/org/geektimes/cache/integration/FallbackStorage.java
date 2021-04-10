package org.geektimes.cache.integration;

import javax.cache.integration.CacheLoader;
import javax.cache.integration.CacheWriter;
import java.util.Comparator;

/**
 * Fallback Storage that only extends {@link CacheLoader} and {@link CacheWriter}
 *
 * @author ajin
 */

public interface FallbackStorage<K, V> extends CacheLoader<K, V>, CacheWriter<K, V> {

    Comparator<FallbackStorage> PRIORITY_COMPARATOR = new PriorityComparator();

    /**
     * Get the priority of current {@link FallbackStorage}.
     *
     * @return the less value , the more priority.
     */
    int getPriority();

    class PriorityComparator implements Comparator<FallbackStorage> {

        @Override
        public int compare(FallbackStorage o1, FallbackStorage o2) {
            return Integer.compare(o1.getPriority(), o2.getPriority());
        }
    }
}
