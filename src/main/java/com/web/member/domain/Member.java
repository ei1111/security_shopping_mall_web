package com.web.member.domain;


import ch.qos.logback.core.util.StringUtil;
import com.web.audit.BaseTimeEntity;
import com.web.member.form.MemberRequest;
import jakarta.persistence.Column;
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
import lombok.ToString;
import org.hibernate.annotations.Comment;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString(exclude = "id")
public class Member extends BaseTimeEntity {

    @Id
    @Comment("회원번호")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    public void update(MemberRequest request) {
        if(!Objects.equals(request.getPassword(), this.password)) {
            this.password = request.getPassword();
        }

        if(!Objects.equals(request.getName(), this.name)) {
            this.name = request.getName();
        }
    }
}
