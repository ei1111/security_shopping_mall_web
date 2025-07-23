package com.web.global.common.util;

import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtill {
    public static String getUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String userId = authentication.getName();

        if (Objects.isNull(userId)) {
            throw new RuntimeException("유저가 로그인 되지 않았습니다.");
        }

        return userId;
    }

    public static String getUserRole() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String role = authentication.getAuthorities().iterator().next().getAuthority().toString();

        if (Objects.isNull(role)) {
            throw new RuntimeException("유저가 로그인 되지 않았습니다.");
        }

        return role;
    }
}
