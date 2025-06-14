package com.web.index.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller("/login")
public class LoginViewController {

    @GetMapping("/login")
    public String loginForm() {
        return "login/login";
    }
}
