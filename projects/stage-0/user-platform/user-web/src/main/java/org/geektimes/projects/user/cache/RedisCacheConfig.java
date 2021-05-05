package org.geektimes.projects.user.cache;

import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author ajin
 */
@Configuration
public class RedisCacheConfig {

    @Bean
    public CacheManager cacheManager() {
        RedisCacheManager redisCacheManager = new RedisCacheManager("localhost:6379");

        return redisCacheManager;
    }
}
