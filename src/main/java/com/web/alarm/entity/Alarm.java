package com.web.alarm.entity;

import com.web.member.entity.Member;
import com.web.member.form.MemberResponse;
import java.util.List;
import lombok.Builder;
import lombok.ToString;

public class Alarm {
    private MemberResponse member;
    private int ranking;
    private boolean isAvailable;
    private List<Long> recommendedMovieIds;

    @Builder
    public Alarm(MemberResponse member, int ranking, boolean isAvailable, List<Long> recommendedMovieIds) {
        this.member = member;
        this.ranking = ranking;
        this.isAvailable = isAvailable;
        this.recommendedMovieIds = recommendedMovieIds;
    }

    @Override
    public String toString() {
        return "Alarm{" +
                "memberUserId=" + (member != null ? member.getUserId() : null) +
                ", ranking=" + ranking +
                ", isAvailable=" + isAvailable +
                ", recommendedMovieIds=" + recommendedMovieIds +
                '}';
    }
}
