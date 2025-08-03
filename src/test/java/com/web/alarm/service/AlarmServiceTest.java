package com.web.alarm.service;

import com.web.alarm.entity.Alarm;
import com.web.member.form.MemberRequest;
import com.web.member.form.MemberResponse;
import com.web.member.repository.MemberRepository;
import com.web.member.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;

@Slf4j
@SpringBootTest
@ActiveProfiles("test")
class AlarmServiceTest {

    @Autowired
    private AlarmService alarmService;

    @Autowired
    private ImproveAlarmService improveAlarmService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;


    private MemberResponse memberResponse;

    @BeforeEach
    public void setUp() {
        memberResponse = memberService.save(
                MemberRequest.builder()
                        .userId("abc")
                        .password("123")
                        .name("홍길동")
                        .email("email")
                        .city("city")
                        .street("street")
                        .zipcode("zipcode")
                        .build()
        );
    }

    @AfterEach
    public void reset() {
        memberRepository.deleteAll();
    }


    //소요 시간 = 825ms,835ms
    @Test
    @WithMockUser(username = "abc", roles = {"USER"})
    @DisplayName("외부에서 API를 조회시 시간을 측정할수 있다.")
    void checkExternalAPI() {
        //given
        Long memberId = memberResponse.getId();
        //when
        //then
        Long startTime = System.currentTimeMillis();
        Alarm alarm = alarmService.postAlarm(memberId);
        Long endTime = System.currentTimeMillis();

        long result = endTime - startTime;

        log.info("alarm = {},  소요 시간 = {}ms", alarm, result);
    }

    //소요 시간 = 226ms
    @Test
    @WithMockUser(username = "abc", roles = {"USER"})
    @DisplayName("향상된 API를 조회시 시간을 측정할수 있다.")
    void imporveCheckExternalAPI() {
        //given
        Long memberId = memberResponse.getId();
        //when
        //then
        Long startTime = System.currentTimeMillis();
        Alarm alarm = improveAlarmService.postAlarm(memberId);
        Long endTime = System.currentTimeMillis();

        long result = endTime - startTime;

        log.info("alarm = {},  소요 시간 = {}ms", alarm, result);
    }
}