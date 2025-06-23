package com.web.member.controller.api;

import com.web.member.form.MemberRequest;
import com.web.member.form.MemberResponse;
import com.web.member.repository.MemberRepository;
import com.web.member.service.MemberService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class MemberApiControllerTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberService memberService;

    private MemberResponse memberResponse;

    @BeforeEach
    public void setUp() {
        memberResponse = memberService.save(MemberRequest.builder()
                .userId("user")
                .password("test content")
                .name("홍길동")
                .email("emial")
                .build()
        );
    }

    @AfterEach
    public void reset() {
        memberRepository.deleteAll();
    }

    @Test
    @DisplayName("저장된 회원을 테스트 할 수 있다")
    void save() throws Exception {
        //given
        //when
        //then
        Assertions.assertAll(
                () -> Assertions.assertEquals(memberResponse.getUserId(), "user")
                , () -> Assertions.assertEquals(memberResponse.getName(), "홍길동")
        );
    }

    @Test
    @DisplayName("아이디로 유저를 찾을 수 있다.")
    void findByIdToUser() throws Exception {
        //given
        String userId = memberResponse.getUserId();
        String name = memberResponse.getName();

        //when
        MemberResponse memberResponse = memberService.findByUserId(userId);

        //then
        Assertions.assertAll(
                () -> Assertions.assertEquals(memberResponse.getUserId(), userId)
                , () -> Assertions.assertEquals(memberResponse.getName(), name)
        );
    }

    @DisplayName("회원의 고유 아이디로 회원을 조회 할 수 있다.")
    @Test
    void findUserPrimaryKey() throws Exception {
        //given
        Long memberId = memberResponse.getId();

        //when
        MemberResponse memberResult = memberService.findById(memberId);

        //then
        Assertions.assertAll(
                () -> Assertions.assertEquals(memberResponse.getId(), memberResult.getId())
        );
    }


    @Test
    @DisplayName("회원을 수정 할 수 있다")
    void updateMember() throws Exception {
        //given
        MemberRequest memberRequest = MemberRequest.builder()
                .id(memberResponse.getId())
                .password("123213")
                .name("아자아자")
                .email("짱찡")
                .build();

        //when
        memberService.update(memberRequest);
        MemberResponse memberResult = memberService.findById(memberResponse.getId());

        //then
        Assertions.assertAll(
                () -> Assertions.assertEquals(memberRequest.getId(), memberResult.getId())
                , () -> Assertions.assertEquals(memberRequest.getName(), memberResult.getName())
                , () -> Assertions.assertEquals(memberRequest.getEmail(), memberResult.getEmail())
        );
    }
}