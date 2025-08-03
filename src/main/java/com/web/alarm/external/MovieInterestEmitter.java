package com.web.alarm.external;

import org.springframework.stereotype.Component;

@Component
public class MovieInterestEmitter {

    public void emitUserInterest(Long movieId) {
        try {
            Thread.sleep(200L);
        } catch (InterruptedException e) {
        }
    }
}
