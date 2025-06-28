package com.web.member.controller.view;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/member")
public class MemberViewController {

    @GetMapping("/join")
    public String joinForm() {
        return "member/join";
    }

    @GetMapping("/login")
    public String loginForm() {
        return "member/login";
    }

    @GetMapping("/detail")
    public String detailForm() {
        return "member/detail";
    }

    @GetMapping("/logout")
    public String logout( HttpServletRequest request, HttpServletResponse response) {
        Cookie cookie = new Cookie("access_token", "");
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);

        HttpSession session = request.getSession(true); // true 하면 세션이 없으면 생성
        session.setAttribute("isNotLogin", true);
        return "/index";
    }

    @GetMapping("/update/{id}")
    public String updateForm(@PathVariable Long id, Model model) {
        model.addAttribute("id", id);
        return "member/update";
    }

    @GetMapping("/memberList")
    public String memberListForm() {
        return "member/memberList";
    }
}
