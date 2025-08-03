package com.web.alarm.external;

import java.util.concurrent.ThreadLocalRandom;
import org.springframework.stereotype.Component;

@Component
public class MovieRankingClient {
    public int getRanking(Long movieId) {
        try {
            Thread.sleep(200L);
        } catch (InterruptedException e) {
        }
        return ThreadLocalRandom.current().nextInt(1, 10);
    }
}

