/*
package com.web.board.controller.api;

import com.web.board.domain.Board;
import com.web.board.form.BoardRequest;
import com.web.board.repository.BoardRepository;
import com.web.member.domain.Member;
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
class BoardApiControllerTest {

    @Autowired
    private BoardRepository boardRepository;
    private Board board;

    @BeforeEach
    public void setUp() {
        board = Board.from(
                BoardRequest.builder()
                        .title("test title")
                        .content("test content")
                        .build(),
                new Member()
        );
    }

    @AfterEach
    public void reset() {
        boardRepository.deleteAll();
    }


    @Test
    @DisplayName("게시판 글 작성 테스트")
    void 게시판에_글을_작성할_수_있다() throws Exception {
        //given
        Board save = boardRepository.save(board);

        //when
        //then
        Assertions.assertAll(
                () -> Assertions.assertEquals(board.getTitle(), save.getTitle())
                , () -> Assertions.assertEquals(board.getContent(), save.getContent()));
    }
}*/
