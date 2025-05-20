package com.web.board.form;

import java.util.List;
import lombok.Getter;
import org.springframework.data.domain.Page;

@Getter
public class BoardPageResponse {

    Page<BoardResponse> boardResponse;
    int startPage;
    int endPage;
    long totalCount;
    List<BoardResponse> boards;

    public BoardPageResponse(Page<BoardResponse> boardResponse) {
        this.boardResponse = boardResponse;
        this.startPage = Math.max(1, getBoardPageNumer() - 4);
        this.endPage = Math.min(boardResponse.getTotalPages(), getBoardPageNumer() + 4);
        this.totalCount = boardResponse.getTotalElements();
        this.boards = boardResponse.getContent();
    }

    public int getBoardPageNumer() {
        return boardResponse.getPageable().getPageNumber();
    }
}
