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
import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
}
