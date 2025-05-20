package com.web.board.service;

import com.web.board.domain.Board;
import com.web.board.form.BoardPageResponse;
import com.web.board.form.BoardRequest;
import com.web.board.form.BoardResponse;
import com.web.board.repository.BoardRepository;
import io.micrometer.core.annotation.Counted;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService {
    private final BoardRepository boardRepository;

    public BoardPageResponse findAll(String searchText, Pageable pageable) {
        Page<BoardResponse> boardResponse = boardRepository.findByTitleContainingOrContentContaining(searchText, pageable);
        return new BoardPageResponse(boardResponse);
    }

    public Board findById(Long id) {
        return boardRepository.findById(id).orElseThrow(IllegalArgumentException::new);
    }

    @Transactional
    @Counted("my.board")
    public void save(BoardRequest boardRequest) {
        boardRepository.save(Board.from(boardRequest));
    }

    @Transactional
    @Counted("my.board")
    public void update(BoardRequest boardRequest) {
        Board board = findById(boardRequest.getBoardId());
        board.updateForm(boardRequest);
    }
}
