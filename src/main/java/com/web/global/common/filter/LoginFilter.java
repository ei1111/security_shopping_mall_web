package com.web.global.common.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.web.global.common.util.JwtUtil;
import com.web.member.entity.CustomUserDetails;
import com.web.member.form.MemberRequest;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Slf4j
public class LoginFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public LoginFilter(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        setFilterProcessesUrl("/member/login");
    }

    @Override
    protected boolean requiresAuthentication(HttpServletRequest request, HttpServletResponse response) {
        return request.getMethod().equals("POST") && super.requiresAuthentication(request, response);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        UsernamePasswordAuthenticationToken authToken = null;

        try {
            MemberRequest memberRequest  = new ObjectMapper().readValue(request.getReader(), MemberRequest.class);
            String userId = memberRequest.getUserId();
            String password = memberRequest.getPassword();
            authToken = new UsernamePasswordAuthenticationToken(userId, password);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //authenticationManager에서 검증진행(디비에서 회원정보를 땡겨와서 UserDetails 서비스에서 유저정보를 받고 검증진행)
        return authenticationManager.authenticate(authToken);
    }

    //authenticationManager에서 검증 성공시 실행
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        String userId = userDetails.getUsername();

        /*role 추출*/
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        //Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();
        String role = auth.getAuthority();

        String token = jwtUtil.createJwt(userId, role);
        //response.addHeader("Authorization", "Bearer " + token);

        Cookie cookie = new Cookie("access_token", token);
        //cookie.setHttpOnly(true);             // JS 접근 차단
        //cookie.setSecure(true);               // HTTPS일 때만 전송 (운영 환경에서 꼭 true)
        cookie.setPath("/");                  // 쿠키가 유효한 경로
        cookie.setMaxAge(60 * 60 * 24);      // 1일 유효기간
        response.addCookie(cookie);

        HttpSession session = request.getSession(true);
        session.setAttribute("isNotLogin", false);
    }

    //authenticationManager에서 검증 실패시 실행
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        response.setStatus(401);
    }
}