package com.web.alarm.improve;

import java.util.concurrent.CompletableFuture;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class MovieImprovedPushNotificationClient {

    @Async("asyncExecutor")
    public CompletableFuture<Void> sendBookingConfirmation(Long userNo, Long bookingId) {
        try {
            Thread.sleep(200L);
        } catch (InterruptedException e) {
        }
        return CompletableFuture.completedFuture(null);
    }
}
