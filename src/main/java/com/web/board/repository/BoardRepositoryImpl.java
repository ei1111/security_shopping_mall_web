package com.web.board.repository;



import static com.web.board.domain.QBoard.*;
import static com.web.member.domain.QMember.*;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.web.board.domain.QBoard;
import com.web.board.form.BoardResponse;
import com.web.board.form.QBoardResponse;
import com.web.member.domain.QMember;
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
                .select(
                        new QBoardResponse(
                                board.id,
                                board.title,
                                board.content,
                                member.userId
                        )
                )
                .from(board)
                .leftJoin(board.member, member)
                .where(titleEq(serachWord).or(contensEq(serachWord)))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(board.createdDate.asc())
                .fetch()
                .stream()
                .peek(s -> s.setRowNum(index.getAndIncrement()))
                .toList();

        JPAQuery<Long> countQuery = jpaQueryFactory
                .select(board.count())
                .from(board)
                .leftJoin(board.member, member)
                .where(titleEq(serachWord).or(contensEq(serachWord)));

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
