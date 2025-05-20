package com.web.board.controller.api;

import com.web.board.form.BoardPageResponse;
import com.web.board.form.BoardRequest;
import com.web.board.form.BoardResponse;
import com.web.board.service.BoardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/board/v1")
@Tag(name = "게시판 CRUD API")
public class BoardApiController {

    private final BoardService boardService;

    @GetMapping("/list")
    @Operation(summary = "게시판 작성 리스트 조회 API")
    public BoardPageResponse list(@PageableDefault Pageable pageable,
            @RequestParam(required = false) String searchText) {
        return boardService.findAll(searchText, pageable);
    }

    @GetMapping("/form")
    @Operation(summary = "게시판 글 조회 API")
    public BoardResponse form(@RequestParam(required = false) @Parameter(example = "1") Long boardId) {
        return findBoardResponseOrDefault(boardId);
    }

    private BoardResponse findBoardResponseOrDefault(Long boardId) {
        return Optional.ofNullable(boardId)
                .map(s -> boardService.findById(s))
                .map(BoardResponse::from)
                .orElseGet(BoardResponse::new);
    }

    @PostMapping("/form")
    @Operation(summary = "게시판 글 등록 API")
    public void save(@Valid @RequestBody BoardRequest board) {
        boardService.save(board);
    }

    @PutMapping("/form")
    @Operation(summary = "게시판 글 수정 API")
    public void update(@RequestBody BoardRequest board) {
        boardService.update(board);
    }
}
