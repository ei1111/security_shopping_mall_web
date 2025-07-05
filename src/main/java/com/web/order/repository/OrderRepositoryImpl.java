package com.web.order.repository;


import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.web.common.util.SecurityUtill;
import com.web.member.domain.QMember;
import com.web.order.domain.OrderStatus;
import com.web.order.domain.QOrder;
import com.web.order.form.OrderResponse;
import com.web.order.form.OrderSearchRequest;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;

@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<OrderResponse> orderSearch(OrderSearchRequest request) {
        QOrder order = QOrder.order;
        QMember member = QMember.member;

        return jpaQueryFactory.selectFrom(order)
                .join(order.member, member)
                .where(
                        likeMember(request.getMemberName())
                      , eqStatus(request.getOrderStatus())
                      , eqMemberId()
                )
                .fetch()
                .stream()
                .map(OrderResponse::from)
                .toList();
    }

    private BooleanExpression likeMember(String memberName) {
        return StringUtils.hasText(memberName) ? QMember.member.name.like(memberName) : null;
    }

    private BooleanExpression eqStatus(OrderStatus status) {
        return status != null ? QOrder.order.status.eq(status) : null;
    }

    private BooleanExpression eqMemberId() {
        System.out.println("jpaQueryFactory = " + SecurityUtill.getUserRole());
        return "ROLE_ADMIN".equals(SecurityUtill.getUserRole()) ? null : QMember.member.userId.eq(SecurityUtill.getUserId());
    }
}