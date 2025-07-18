
package com.web.board.controller.api;

import com.web.board.entity.Board;
import com.web.board.form.BoardRequest;
import com.web.board.repository.BoardRepository;
import com.web.board.service.BoardService;
import com.web.member.entity.Member;
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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class BoardApiControllerTest {
    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private BoardService boardService;

    private Board board;

    @BeforeEach
    public void setUp() {
        MemberResponse memberResponse = memberService.save(
                MemberRequest.builder()
                        .userId("abc")
                        .password("123")
                        .name("홍길동")
                        .email("email")
                        .build()
        );

        Member member = memberRepository.findById(memberResponse.getId()).orElse(null);

        board = Board.from(
                BoardRequest.builder()
                        .title("test title")
                        .content("test content")
                        .build(),
                          member
        );
    }

    @AfterEach
    public void reset() {
        boardRepository.deleteAll();
        memberRepository.deleteAll();
    }

    @Test
    @DisplayName("게시판 글 작성 테스트")
    @WithMockUser(username = "abc", roles = {"USER"})
    void 게시판에_글을_작성할_수_있다() throws Exception {
        //given
        Board save = boardRepository.save(board);

        //when
        //then
        Assertions.assertAll(
                () -> Assertions.assertEquals(board.getTitle(), save.getTitle())
                , () -> Assertions.assertEquals(board.getContent(), save.getContent()));
    }

    @Test
    @DisplayName("Redis 테스트 할수 있다.")
    @WithMockUser(username = "abc", roles = {"USER"})
    void test_redis() throws Exception{
        //given
        Board boardResult = boardRepository.save(board);
        Long boardId = boardResult.getId();
        //when
        //then
        Long beforeRedis = checkedTime(boardId);
        Long afterRedis = checkedTime(boardId);

        System.out.println("beforeRedis = " + beforeRedis);
        System.out.println("afterRedis = " + afterRedis);

        Assertions.assertNotEquals(beforeRedis, afterRedis);
    }

    private Long checkedTime(Long boardId) {
        Long startTime = System.currentTimeMillis();
        boardService.findById(boardId);
        Long endTime = System.currentTimeMillis();
        return endTime - startTime;
    }
}
