package com.web.member.controller;

import com.web.member.form.MemberRequest;
import com.web.member.service.MemberService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
    private final MemberService memberService;


    @PostMapping("/login")
    public String login(@RequestBody MemberRequest request, HttpSession session) {
        String byIdAndPassword = memberService.findByIdAndPassword(request, session);
        return byIdAndPassword;
    }

    @GetMapping("/main")
    public String memberMain() {
        return "member/main";
    }

    @GetMapping("/members")
    public String members(Model model) {
        model.addAttribute("members", memberService.findAll());
        return "member/members";
    }

    @GetMapping("/detail/{id}")
    public String detail(Model model, @PathVariable Long id) {
        model.addAttribute("member", memberService.findById(id));
        return "member/detail";
    }

    @GetMapping("/delete/{id}")
    public String delete(Model model, @PathVariable Long id) {
        memberService.delete(id);
        return "redirect:member/members";
    }

    @GetMapping("/update/{id}")
    public String updateForm(Model model, @PathVariable Long id) {
        model.addAttribute("member", memberService.findById(id));
        return "member/update";
    }

    @PostMapping("/update")
    public String update(MemberRequest request) {
        memberService.update(request);
        return "redirect:/member/members";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "index";
    }
}
