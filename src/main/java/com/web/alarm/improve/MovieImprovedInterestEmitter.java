package com.web.alarm.improve;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MovieImprovedInterestEmitter {

    @Async("asyncExecutor")
    public void emitUserInterest(Long id) {
        try {
            Thread.sleep(200L);
        } catch (InterruptedException e) {
        }
    }
}
