package com.web.common.filter;


import com.web.common.util.JwtUtil;
import com.web.member.domain.CustomUserDetails;
import com.web.member.domain.Member;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

//요청에 한번만 실행
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException, IOException {
        String authorization = request.getHeader("Authorization");

        if (!StringUtils.hasText(authorization) || !authorization.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            //필터 종료
            return;
        }

        String token = authorization.split(" ")[1];

        //토큰 소멸시간 검증 true 소멸, false 소멸 되지 않음
        if (jwtUtil.isExpired(token)) {
            filterChain.doFilter(request, response);
            //필터 종료
            return;
        }

        String userId = jwtUtil.getUserId(token);
        String role = jwtUtil.getRole(token);

        Member member = new Member();
        member.setUserId(userId);
        member.setRole(role);

        CustomUserDetails customUserDetails = new CustomUserDetails(member);
        Authentication authToken = new UsernamePasswordAuthenticationToken(customUserDetails, null,
                customUserDetails.getAuthorities());
        //세션에 사용자 등록
        SecurityContextHolder.getContext().setAuthentication(authToken);
        filterChain.doFilter(request, response);
    }
}