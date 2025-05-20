package com.web.board.repository;

import static com.web.board.domain.QBoard.board;

import ch.qos.logback.core.util.StringUtil;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.web.board.domain.Board;
import com.web.board.form.BoardResponse;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.util.StringUtils;

@RequiredArgsConstructor
public class BoardRepositoryImpl implements BoardRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<BoardResponse> findByTitleContainingOrContentContaining(String serachWord,
            Pageable pageable) {
        AtomicInteger index = new AtomicInteger((int) pageable.getOffset() + 1);

        List<BoardResponse> boardResponse = jpaQueryFactory
                .selectFrom(board)
                .where(titleEq(serachWord).or(contensEq(serachWord)))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(board.createdDate.asc())
                .fetch()
                .stream()
                .map(s -> {
                    BoardResponse boreadResponse = BoardResponse.from(s);
                    boreadResponse.increaseRowNum(index);
                    return boreadResponse;
                })
                .toList();

        JPAQuery<Board> countQuery = jpaQueryFactory.selectFrom(board)
                .where(
                        titleEq(serachWord).or(contensEq(serachWord))
                );
        return PageableExecutionUtils.getPage(boardResponse, pageable, countQuery::fetchCount);
    }

    private BooleanBuilder titleEq(String serachWord) {
        return nullSafeBuilder(() -> board.title.contains(serachWord), serachWord);
    }

    private BooleanBuilder contensEq(String serachWord) {
        return nullSafeBuilder(() -> board.content.contains(serachWord), serachWord);
    }

    public static BooleanBuilder nullSafeBuilder(Supplier<BooleanExpression> supplier, String keyword) {
        return StringUtils.hasText(keyword) ? new BooleanBuilder(supplier.get()) : new BooleanBuilder();
    }
}
