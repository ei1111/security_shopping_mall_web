package com.web.alarm.external;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import org.springframework.stereotype.Component;

@Component
public class MovieRecommendClient {

    public List<Long> getRecommendedMovieIds(Long userNo) {
        try {
            Thread.sleep(200L);
        } catch (InterruptedException e) {
        }

        List<Long> recommendedMovieIds = new ArrayList<>(5);
        for (int i = 0; i < 5; i++) {
            long randomLong = ThreadLocalRandom.current().nextLong();
            recommendedMovieIds.add(randomLong);
        }

        return recommendedMovieIds;
    }
}

