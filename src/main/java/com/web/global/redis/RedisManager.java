package com.web.global.redis;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.web.global.common.util.CustomObjectMapper;
import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RedisManager {
    private final CustomObjectMapper customObjectMapper = new CustomObjectMapper();
    private final RedisTemplate<String, String> redisTemplate;
    private static final Long EXPIRE_SECONDS = 60 * 60 *  24 * 7L; // 1주일

    public <T> T get(RedisKeyPrefix keyPrefix, Long id, Class<T> clazz) {
        String jsonString = redisTemplate.opsForValue().get(generateCacheKey(keyPrefix, id));

        if (jsonString == null) {
            return null;
        }

        try {
            return customObjectMapper.readValue(jsonString, clazz);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public <T> void set(RedisKeyPrefix keyPrefix, Long id, T data) {
        String jsonString;

        try {
            jsonString = customObjectMapper.writeValueAsString(data);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        redisTemplate.opsForValue().set(
                generateCacheKey(keyPrefix, id),
                jsonString,
                Duration.ofSeconds(EXPIRE_SECONDS)
        );
    }

    public void delete(RedisKeyPrefix keyPrefix, Long id) {
        redisTemplate.delete(generateCacheKey(keyPrefix, id));
    }

    private String generateCacheKey(RedisKeyPrefix keyPrefix, Long id) {
        return keyPrefix.getPrefix() + id;
    }
}
