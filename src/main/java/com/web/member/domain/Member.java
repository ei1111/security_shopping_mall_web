package com.web.member.domain;


import com.web.audit.BaseTimeEntity;
import com.web.member.form.MemberRequest;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Comment;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Member extends BaseTimeEntity {

    @Id
    @Comment("회원번호")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Comment("아이디")
    @Column(unique = true)
    private String userId;

    @Comment("비밀번호")
    private String password;

    @Comment("이름")
    private String name;

    @Comment("이메일")
    @Column(unique = true)
    private String email;

    private String role;

    @Embedded
    private Address address;

    public Member(String userId, String password, String name, String email) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.email = email;
        this.role = makeRole(userId);
    }

    private String makeRole(String userId) {
        return userId.equalsIgnoreCase("admin") ? "ROLE_ADMIN": "ROLE_USER";
    }

    public static Member from(String userId, String password, String name, String email) {
        return new Member(userId, password, name, email);
    }

    public void update(MemberRequest request, PasswordEncoder passwordEncoder) {
        if (StringUtils.hasText(request.getPassword())) {
            this.password = passwordEncoder.encode(request.getPassword());
        }

        if(!Objects.equals(request.getName(), this.name)) {
            this.name = request.getName();
        }

        if(!Objects.equals(request.getEmail(), this.email)) {
            this.email = request.getEmail();
        }
    }

    public String getCity() {
        return address.getCity();
    }

    public String getStreet() {
        return address.getStreet();
    }

    public String getZipCode() {
        return address.getZipcode();
    }
}
