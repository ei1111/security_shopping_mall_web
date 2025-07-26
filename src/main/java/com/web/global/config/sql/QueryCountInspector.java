package com.web.global.config.sql;


import com.web.global.config.sql.jpa.RequestContext;
import com.web.global.config.sql.jpa.RequestContextHolder;
import org.hibernate.resource.jdbc.spi.StatementInspector;

public class QueryCountInspector implements StatementInspector {

    /*
     * 실행 과정
     * 1. client에서 Http 요청
     * 2. QueryCountInterceptor -> preHandle 처리(Http 요청 패턴 추출, RequestContext를 스레드에 초기화)
     *    -> 기존 비즈니스 로직 수행 후 응답 반환
     * 3. afterCompletion -> requestContext 조회 후 MeterRegistry에 값 저장, RequestContext 정리
     * */
    @Override
    public String inspect(String sql) {
        // HTTP 요청 컨텍스트
        RequestContext requestContext = RequestContextHolder.getContext();

        if (requestContext != null) {
            requestContext.incrementQueryCount(sql);
        }

        return sql;
    }
}
