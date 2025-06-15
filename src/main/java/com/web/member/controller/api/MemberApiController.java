package com.web.member.controller.api;

import com.web.member.form.MemberRequest;
import com.web.member.form.MemberResponse;
import com.web.member.service.MemberService;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "회원가입 CRUD API")
@RequestMapping("/member/v1")
public class MemberApiController {

    private final MemberService memberService;

    @PostMapping("/join")
    public ResponseEntity<String> save(@RequestBody MemberRequest request) {
        memberService.save(request);
        return ResponseEntity.ok("회원 가입 성공!");
    }

    @GetMapping("/detail")
    public ResponseEntity<MemberResponse> detail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();

        if (Objects.isNull(userId)) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().body(memberService.findByUserId(userId));
    }

    @GetMapping("/update/{id}")
    public ResponseEntity<MemberResponse> updateForm(@PathVariable Long id) {
        return ResponseEntity.ok().body(memberService.findById(id));
    }

    @PutMapping("/update")
    public ResponseEntity<String> update(@RequestBody MemberRequest request) {
        memberService.update(request);
        return ResponseEntity.ok().body("회원 수정 성공");
    }
}
