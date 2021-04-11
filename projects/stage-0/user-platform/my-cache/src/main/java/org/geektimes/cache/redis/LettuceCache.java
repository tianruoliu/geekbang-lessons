package org.geektimes.cache.redis;

import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import io.lettuce.core.codec.ByteArrayCodec;
import org.geektimes.cache.AbstractCache;

import javax.cache.CacheException;
import javax.cache.CacheManager;
import javax.cache.configuration.Configuration;
import java.io.*;
import java.util.Iterator;

/**
 * <pre>
 * <code>
 * RedisClient client = RedisClient.create("redis://localhost");
 * StatefulRedisConnection<String, String> connection = client.connect();
 * RedisStringCommands sync = connection.sync();
 * String value = sync.get("key");
 * </code>
 * </pre>
 *
 * @author ajin
 */

public class LettuceCache<K extends Serializable, V extends Serializable> extends AbstractCache<K, V> {


    private final StatefulRedisConnection connection;

    protected LettuceCache(CacheManager cacheManager, String cacheName, Configuration<K, V> configuration,
         StatefulRedisConnection connection) {
        super(cacheManager, cacheName, configuration);
        this.connection = connection;
    }

    @Override
    protected V doGet(K key) throws CacheException, ClassCastException {
        byte[]                                  keyBytes        = serialize(key);

       return doGet(keyBytes);
    }

    protected V doGet(byte[] keyBytes) {
        RedisCommands<byte[], byte[]>           sync            = connection.sync();
        byte[] valueBytes = sync.get(keyBytes);
        V value = deserialize(valueBytes);
        return value;
    }

    @Override
    protected V doPut(K key, V value) throws CacheException, ClassCastException {

        byte[]                                  keyBytes        = serialize(key);
        V      oldValue   = doGet(keyBytes);

        RedisCommands<byte[], byte[]>           sync            = connection.sync();

        byte[] valueBytes = serialize(value);
        sync.set(keyBytes, valueBytes);

        return oldValue;
    }

    @Override
    protected V doRemove(K key) throws CacheException, ClassCastException {
        byte[] keyBytes = serialize(key);

        V      oldValue = doGet(keyBytes);

        RedisCommands<byte[], byte[]>           sync            = connection.sync();
        sync.del(keyBytes);

        return oldValue;
    }

    @Override
    protected void doClear() throws CacheException {

    }

    @Override
    protected Iterator<Entry<K, V>> newIterator() {
        return null;
    }

    @Override
    protected void doClose() {
        this.connection.close();
    }

    // 是否可以抽象出一套序列化和反序列化的 API
    private byte[] serialize(Object value) throws CacheException {
        byte[] bytes = null;
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream)) {
            // Key -> byte[]
            objectOutputStream.writeObject(value);
            bytes = outputStream.toByteArray();
        } catch (IOException e) {
            throw new CacheException(e);
        }
        return bytes;
    }

    private V deserialize(byte[] bytes) throws CacheException {
        if (bytes == null) {
            return null;
        }
        V value = null;
        try (ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream)) {
            // byte[] -> Value
            value = (V)objectInputStream.readObject();
        } catch (Exception e) {
            throw new CacheException(e);
        }
        return value;
    }

}
