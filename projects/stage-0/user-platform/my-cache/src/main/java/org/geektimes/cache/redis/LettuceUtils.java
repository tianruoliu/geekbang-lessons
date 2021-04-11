package org.geektimes.cache.redis;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.codec.ByteArrayCodec;

import java.time.Duration;

/**
 * @author ajin
 */

public class LettuceUtils {

    private static StatefulRedisConnection<byte[], byte[]> connection;


    public static StatefulRedisConnection<byte[], byte[]> getStatefulRedisConnection(String ip, int port) {
        if (null != LettuceUtils.connection) {
            return LettuceUtils.connection;
        }
        RedisURI                                redisURI        = new RedisURI(ip, port, Duration.ofMillis(30));
        RedisClient                             redisClient     = RedisClient.create(redisURI);
        StatefulRedisConnection<byte[], byte[]> redisConnection = redisClient.connect(new ByteArrayCodec());
        LettuceUtils.connection = redisConnection;
        return redisConnection;
    }
}
