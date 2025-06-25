package com.web.board.form;

import com.querydsl.core.annotations.QueryProjection;
import com.web.board.domain.Board;
import com.web.common.util.SecurityUtill;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class BoardResponse {
    public Long boardId;
    public String title;
    public String content;
    public String userId;
    public int rowNum;

    @QueryProjection
    public BoardResponse(Long boardId, String title, String content, String userId) {
        this.boardId = boardId;
        this.title = title;
        this.content = content;
        this.userId = userId;
    }

    public static BoardResponse from(Board board) {
        return new BoardResponse(board.getId(), board.getTitle(), board.getContent(), SecurityUtill.getUserId());
    }
}
