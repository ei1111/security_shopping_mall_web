package com.web.board.form;

import com.web.board.domain.Board;
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
    public int rowNum;

    public BoardResponse(Long boardId, String title, String content) {
        this.boardId = boardId;
        this.title = title;
        this.content = content;
    }

    public static BoardResponse from(Board board) {
        return new BoardResponse(board.getBoardId(), board.getTitle(), board.getContent());
    }

    public void increaseRowNum(AtomicInteger index) {
        this.rowNum = index.getAndIncrement();
    }
}
