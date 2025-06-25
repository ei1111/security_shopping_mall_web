package com.web.board.redis;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.web.board.domain.Board;
import com.web.board.form.BoardResponse;
import com.web.common.util.CustomObjectMapper;
import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BoardRedis {
    private static final String KEY_PREFIX = "board_detail:v1:";
    private static final Long EXPIRE_SECONDS = 60 * 60 *  24 * 7L; // 1주일
    private final CustomObjectMapper customObjectMapper = new CustomObjectMapper();
    private final RedisTemplate<String, String> redisTemplate;

    public BoardResponse get(Long postId) {
        String jsonString = redisTemplate.opsForValue().get(generateCacheKey(postId));

        if (jsonString == null) {
            return null;
        }

        try {
            return customObjectMapper.readValue(jsonString, BoardResponse.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public void set(BoardResponse boardResponse) {
        String jsonString;

        try {
            jsonString = customObjectMapper.writeValueAsString(boardResponse);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        //key, cache, TTL(캐시의 유효기간)
        redisTemplate.opsForValue().set(
                generateCacheKey(boardResponse.getBoardId()),
                jsonString,
                Duration.ofSeconds(EXPIRE_SECONDS)
        );
    }

    public void delete(Long boardId) {
        redisTemplate.delete(generateCacheKey(boardId));
    }

    private String generateCacheKey(Long boardId) {
        return KEY_PREFIX + boardId;
    }
}
