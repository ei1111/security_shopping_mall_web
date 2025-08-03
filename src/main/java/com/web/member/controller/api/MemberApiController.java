package com.web.member.controller.api;

import com.web.global.common.util.SecurityUtill;
import com.web.member.form.MemberRequest;
import com.web.member.form.MemberResponse;
import com.web.member.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "1. 회원가입 CRUD API")
@RequestMapping("/member/v1")
public class MemberApiController {

    private final MemberService memberService;

    @PostMapping("/join")
    @Operation(summary = "회원 정보 등록 API")
    public ResponseEntity<String> save(@RequestBody MemberRequest request) {
        memberService.save(request);
        return ResponseEntity.ok("회원 가입 성공!");
    }

    @GetMapping("/detail")
    @Operation(summary = "회원 아이디로 상세 조회 API")
    public ResponseEntity<MemberResponse> detail() {
        String userId = SecurityUtill.getUserId();
        return ResponseEntity.ok().body(memberService.findByUserId(userId));
    }

    @GetMapping("/update/{id}")
    @Operation(summary = "회원 번호로 상세 조회 API")
    public ResponseEntity<MemberResponse> updateForm(@PathVariable Long id) {
        return ResponseEntity.ok().body(memberService.findById(id));
    }

    @PutMapping("/update")
    @Operation(summary = "회원 정보 수정 API")
    public ResponseEntity<String> update(@RequestBody MemberRequest request) {
        memberService.update(request);
        return ResponseEntity.ok().body("회원 수정 성공");
    }

    @GetMapping("/list")
    //@PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "회원 리스트 API")
    public ResponseEntity<List<MemberResponse>> memberList() {
        return ResponseEntity.ok(memberService.findAll());
    }
}
