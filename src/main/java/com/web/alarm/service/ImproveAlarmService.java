package com.web.alarm.service;

import com.web.alarm.entity.Alarm;
import com.web.alarm.improve.MovieImprovedBookingClient;
import com.web.alarm.improve.MovieImprovedInterestEmitter;
import com.web.alarm.improve.MovieImprovedPushNotificationClient;
import com.web.alarm.improve.MovieImprovedRankingClient;
import com.web.alarm.improve.MovieImprovedRecommendClient;
import com.web.member.form.MemberResponse;
import com.web.member.service.MemberService;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ImproveAlarmService {
    private final MemberService memberService;
    private final MovieImprovedBookingClient bookingClient;
    private final MovieImprovedRecommendClient recommendClient;
    private final MovieImprovedRankingClient rankingClient;
    private final MovieImprovedInterestEmitter interestEmitter;
    private final MovieImprovedPushNotificationClient moviePushNotificationClient;

    public Alarm postAlarm(Long id) {

        MemberResponse member = memberService.findById(id);

        CompletableFuture<Integer> ranking = rankingClient.getRanking(id);
        CompletableFuture<Boolean> isAvailable = bookingClient.isAvailable(id);
        CompletableFuture<List<Long>> recommendedMovieIds = recommendClient.getRecommendedMovieIds(id);


        interestEmitter.emitUserInterest(id);

        return Alarm.builder()
                .member(member)
                .ranking(ranking.join())
                .isAvailable(isAvailable.join())
                .recommendedMovieIds(recommendedMovieIds.join())
                .build();
    }

    // 예매 확정 푸시 알림 전송
    //현재 executor.setMaxPoolSize(30);이라서 최대 30명에게 한번에 보낼수 있음
    public void sendBookingConfirmationPush(Map<Long, Long> bookingIdByUserNo) {
        List<CompletableFuture<Void>> futures = bookingIdByUserNo.entrySet()
                .stream()
                .map(entry -> moviePushNotificationClient.sendBookingConfirmation(entry.getKey(), entry.getValue())
                        .exceptionally(ex -> {
                            //여러명에게 알림 보낼때 중간에 오류가 나더라도 멈추지 않고 실행됨
                            log.warn("푸시 알림 실패", ex);
                            return null;
                        }))
                .toList();

        // 모든 비동기 작업이 끝날 때까지 대기
        CompletableFuture.allOf(futures.toArray(CompletableFuture[]::new)).join();
    }
}
