package com.web.member.form;

import com.web.member.entity.Address;
import com.web.member.entity.Member;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@Setter
@Builder
@NoArgsConstructor
@Schema(description = "회원 가입 request")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberRequest {
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

    @Schema(description = "거주도시", example = "서울")
    private  String city;

    @Schema(description = "도로명", example = "양갈로")
    private String street;

    @Schema(description = "우편번호", example = "01321")
    private String zipcode;


    public Member fromMeber(PasswordEncoder passwordEncoder) {
        Address address = new Address(city, street, zipcode);
        return Member.from(userId, passwordEncoder.encode(password), name, email, address);
    }
}
