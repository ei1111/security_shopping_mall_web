package com.web.alarm.service;

import com.web.alarm.entity.Alarm;
import com.web.alarm.external.ExternalClient;
import com.web.alarm.external.MovieInterestEmitter;
import com.web.alarm.external.MovieRankingClient;
import com.web.alarm.external.MovieRecommendClient;
import com.web.member.form.MemberResponse;
import com.web.member.service.MemberService;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AlarmService {

    private final MemberService memberService;
    private final MovieRankingClient rankingClient;
    private final ExternalClient externalClient;
    private final MovieRecommendClient recommendClient;
    private final MovieInterestEmitter interestEmitter;

    public Alarm postAlarm(Long id) {

        MemberResponse member = memberService.findById(id);

        int ranking = rankingClient.getRanking(id);
        boolean isAvailable =  externalClient.isAvailable(id);
        List<Long> recommendedMovieIds = recommendClient.getRecommendedMovieIds(id);

        interestEmitter.emitUserInterest(id);

        return Alarm.builder()
                .member(member)
                .ranking(ranking)
                .isAvailable(isAvailable)
                .recommendedMovieIds(recommendedMovieIds)
                .build();
    }
}
