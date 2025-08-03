package com.web.alarm.external;

import java.util.concurrent.ThreadLocalRandom;
import org.springframework.stereotype.Component;

@Component
public class ExternalClient {
    public boolean isAvailable(long id) {
        try {
            Thread.sleep(200L);
        } catch (InterruptedException e) {
        }
        return ThreadLocalRandom.current().nextBoolean();
    }
}
