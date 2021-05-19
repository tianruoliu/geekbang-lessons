package org.geektimes.projects.user.cache;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.redis.cache.CacheKeyPrefix;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.lang.Nullable;

import java.time.Duration;

import static org.springframework.data.redis.cache.RedisCacheConfiguration.registerDefaultConverters;

/**
 * @author ajin
 */
@EnableCaching
public class RedisCacheConfig {

    private final Duration                                            ttl;
    private final Boolean                                             cacheNullValues;
    private final Boolean                                             usePrefix;
    private final CacheKeyPrefix                                      keyPrefix;
    private final RedisSerializationContext.SerializationPair<String> keySerializationPair;
    private final RedisSerializationContext.SerializationPair<?>      valueSerializationPair;
    private final ConversionService                                   conversionService;

    public static final ThreadLocal<RedisCacheConfig> REDIS_CACHE_CONFIG_THREAD_LOCAL = ThreadLocal.withInitial(
        () -> defaultCacheConfig(Thread.currentThread().getContextClassLoader()));

    @Bean
    public CacheManager cacheManager() {
        RedisCacheManager redisCacheManager = new RedisCacheManager("localhost");
        return redisCacheManager;
    }

    public RedisCacheConfig() {
        this.ttl = Duration.ZERO;
        this.cacheNullValues = Boolean.FALSE;
        this.usePrefix = Boolean.FALSE;
        this.keyPrefix = null;
        this.keySerializationPair = null;
        this.valueSerializationPair = null;
        this.conversionService = null;

    }

    public RedisCacheConfig(Duration ttl, Boolean cacheNullValues, Boolean usePrefix, CacheKeyPrefix keyPrefix,
        RedisSerializationContext.SerializationPair<String> keySerializationPair,
        RedisSerializationContext.SerializationPair<?> valueSerializationPair, ConversionService conversionService) {

        this.ttl = ttl;
        this.cacheNullValues = cacheNullValues;
        this.usePrefix = usePrefix;
        this.keyPrefix = keyPrefix;
        this.keySerializationPair = keySerializationPair;
        this.valueSerializationPair = valueSerializationPair;
        this.conversionService = conversionService;
    }

    public static RedisCacheConfig defaultCacheConfig(@Nullable ClassLoader classLoader) {

        DefaultFormattingConversionService conversionService = new DefaultFormattingConversionService();

        registerDefaultConverters(conversionService);

        return new RedisCacheConfig(Duration.ZERO, true, true, CacheKeyPrefix.simple(),
            RedisSerializationContext.SerializationPair.fromSerializer(RedisSerializer.string()),
            RedisSerializationContext.SerializationPair.fromSerializer(RedisSerializer.java(classLoader)),
            conversionService);
    }

    public Duration getTtl() {
        return ttl;
    }

    public Boolean getCacheNullValues() {
        return cacheNullValues;
    }

    public Boolean getUsePrefix() {
        return usePrefix;
    }

    public CacheKeyPrefix getKeyPrefix() {
        return keyPrefix;
    }

    public RedisSerializationContext.SerializationPair<String> getKeySerializationPair() {
        return keySerializationPair;
    }

    public RedisSerializationContext.SerializationPair<?> getValueSerializationPair() {
        return valueSerializationPair;
    }

    public ConversionService getConversionService() {
        return conversionService;
    }

    public static ThreadLocal<RedisCacheConfig> getRedisCacheConfigThreadLocal() {
        return REDIS_CACHE_CONFIG_THREAD_LOCAL;
    }
}
