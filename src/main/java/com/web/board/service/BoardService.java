package com.web.board.service;

import com.web.board.domain.Board;
import com.web.board.form.BoardPageResponse;
import com.web.board.form.BoardRequest;
import com.web.board.form.BoardResponse;
import com.web.board.redis.BoardRedis;
import com.web.board.repository.BoardRepository;
import com.web.common.util.SecurityUtill;
import com.web.member.domain.Member;
import com.web.member.form.MemberResponse;
import com.web.member.repository.MemberRepository;
import com.web.member.service.MemberService;
import io.micrometer.core.annotation.Counted;
import jakarta.persistence.EntityNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
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
    private final MemberRepository memberRepository;
    private final BoardRedis boardRedis;

    public BoardPageResponse findAll(String searchText, Pageable pageable) {
        Page<BoardResponse> boardResponse = boardRepository.findByTitleContainingOrContentContaining(searchText, pageable);
        return new BoardPageResponse(boardResponse);
    }

    public BoardResponse findById(Long id) {

        if (Objects.nonNull(boardRedis.get(id))) {
            return boardRedis.get(id);
        }

        Board board = boardRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        BoardResponse boardResponse = BoardResponse.from(board);
        boardRedis.set(boardResponse);

        return boardResponse;
    }

    @Transactional
    @Counted("my.board")
    public Board save(BoardRequest boardRequest) {
        String userId = SecurityUtill.getUserId();
        Member member = memberRepository.findByUserId(userId);
        return boardRepository.save(Board.from(boardRequest, member));
    }

    @Transactional
    @Counted("my.board")
    public void update(BoardRequest boardRequest) {
        Board board = boardRepository.findById(boardRequest.getBoardId()).orElseThrow(IllegalArgumentException::new);
        Board updateBoard = board.updateForm(boardRequest);
        boardRedis.set(BoardResponse.from(updateBoard));
    }
}
