package org.geektimes.projects.user.cache;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;

/**
 * @author ajin
 */
@EnableCaching
public class RedisCacheConfig {

    @Bean
    public CacheManager cacheManager() {
        RedisCacheManager redisCacheManager = new RedisCacheManager("localhost");

        return redisCacheManager;
    }
}
