package com.web.common.util;

import java.util.Objects;
import org.springframework.security.core.Authentication;
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
}
