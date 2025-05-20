package com.web.board.repository;

import com.web.board.form.BoardResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BoardRepositoryCustom {

    Page<BoardResponse> findByTitleContainingOrContentContaining(String serachWord, Pageable pageable);
}
