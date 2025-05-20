package com.web.member.form;

import com.web.member.domain.Member;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Schema(description = "회원 가입 response")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberResponse {
    @Schema(description = "회원번호")
    private Long id;

    @Schema(description = "아이디")
    private String userId;

    @Schema(description = "비밀번호")
    private String password;

    @Schema(description = "이름")
    private String name;

    @Schema(description = "이메일")
    private String email;

    public static MemberResponse from(Member member) {
        return new MemberResponse(member.getId(), member.getUserId(), member.getPassword(), member.getName(), member.getEmail());
    }
}
