package org.geektimes.projects.user.cache;

import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * @author ajin
 */
@Configuration
@EnableRedisHttpSession
public class RedisCacheConfig {

    @Bean
    public CacheManager cacheManager() {
        RedisCacheManager redisCacheManager = new RedisCacheManager("localhost:6379");

        return redisCacheManager;
    }
}
