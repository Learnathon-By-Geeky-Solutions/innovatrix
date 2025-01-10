package com.innovatrix.ahaar.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RedisService {

    public static final String REDIS_PREFIX = "USER:";

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * Retrieves a value from Redis and deserializes it to the specified entity class.
     *
     * @param key The Redis key to retrieve the value for
     * @param entityClass The target class to deserialize the Redis value into
     * @param <T> The type of the entity to be returned
     * @return The deserialized object of type T, or null if no value exists for the key
     * @throws RuntimeException If JSON deserialization fails
     */
    public<T> T get(String key, Class<T> entityClass){
        Object o=redisTemplate.opsForValue().get(key);
        ObjectMapper mapper =new ObjectMapper();
        if(o==null){
            return null;
        }
        try {
            assert o != null;
            return mapper.readValue(o.toString(),entityClass);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public void set(String key, Object o,long ttl){
        try {
            ObjectMapper objectMapper=new ObjectMapper();
            String jsonValue=objectMapper.writeValueAsString(o);
            redisTemplate.opsForValue().set(key,jsonValue,ttl, TimeUnit.HOURS);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
    public void delete(String key) {
        redisTemplate.delete(key);
    }
}
