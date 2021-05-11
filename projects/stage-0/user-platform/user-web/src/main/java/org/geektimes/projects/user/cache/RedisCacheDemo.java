package org.geektimes.projects.user.cache;

import org.geektimes.projects.user.domain.User;
import org.springframework.cache.annotation.Cacheable;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ajin
 */

public class RedisCacheDemo {

    private static final Map<Long, User> dataMap = new HashMap<Long, User>(){
        private static final long serialVersionUID = -4007249483853349294L;

        {
            for (long i = 1; i < 100 ; i++) {
                User u = new User(i,"测试"+i,"1","1@qq.com","1");
                put(i, u);
            }
        }
    };

    @Cacheable(cacheNames = "userCache", key = "#id",cacheManager = "cacheManager")
    public User findUser(Long id) {
        return dataMap.get(id);
    }
}
