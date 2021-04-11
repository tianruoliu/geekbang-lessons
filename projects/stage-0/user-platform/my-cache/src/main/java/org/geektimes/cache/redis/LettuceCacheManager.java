package org.geektimes.cache.redis;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.codec.ByteArrayCodec;
import org.geektimes.cache.AbstractCacheManager;

import javax.cache.Cache;
import javax.cache.configuration.Configuration;
import javax.cache.spi.CachingProvider;
import java.net.URI;
import java.util.Properties;

/**
 * {@link javax.cache.CacheManager} based on Lettuce
 *
 * @author ajin
 */

public class LettuceCacheManager extends AbstractCacheManager {

    private final RedisClient redisClient;

    // private final StatefulRedisConnection<byte[], byte[]> connection;

    public LettuceCacheManager(CachingProvider cachingProvider, URI uri, ClassLoader classLoader,
        Properties properties) {
        super(cachingProvider, uri, classLoader, properties);
        RedisURI    redisURI    = RedisURI.create(uri);
        RedisClient redisClient = RedisClient.create(redisURI);
        this.redisClient = redisClient;
        // this.connection = redisClient.connect(new ByteArrayCodec());
    }

    @Override
    protected <K, V, C extends Configuration<K, V>> Cache doCreateCache(String cacheName, C configuration) {
        StatefulRedisConnection connect = redisClient.connect(new ByteArrayCodec());
        return new LettuceCache(this, cacheName, configuration, connect);
    }

    @Override
    protected void doClose() {
        redisClient.shutdown();
    }
}
