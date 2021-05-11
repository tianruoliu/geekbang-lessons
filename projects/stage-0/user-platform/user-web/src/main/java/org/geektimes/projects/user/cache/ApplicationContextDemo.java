package org.geektimes.projects.user.cache;

import org.geektimes.projects.user.domain.User;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author ajin
 */

public class ApplicationContextDemo {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(RedisCacheConfig.class);
        applicationContext.register(RedisCacheDemo.class);

        applicationContext.refresh();

        CacheManager   cacheManager           = applicationContext.getBean(CacheManager.class);
        RedisCacheDemo redisCacheDemo = applicationContext.getBean(RedisCacheDemo.class);
        User           user           = redisCacheDemo.findUser(1L);
        System.out.println(user);
        applicationContext.close();
    }
}
