package com.web.index;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping({"/index" , "/"})
    public String index(@CookieValue(name = "access_token", required = false) String accessToken, HttpServletRequest request) {
        boolean isNotLogin = accessToken == null || accessToken.isEmpty();
        HttpSession session = request.getSession(true); // true 하면 세션이 없으면 생성
        session.setAttribute("isNotLogin", isNotLogin);

        return "index";
    }
}
