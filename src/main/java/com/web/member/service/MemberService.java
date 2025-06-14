package com.web.member.service;

import com.web.member.domain.Member;
import com.web.member.form.MemberRequest;
import com.web.member.form.MemberResponse;
import com.web.member.repository.MemberRepository;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void save(MemberRequest request) {
        memberRepository.save(request.fromMeber(passwordEncoder));
    }

    public String findByIdAndPassword(MemberRequest request , HttpSession  session) {
        return memberRepository.findByUserIdAndPassword(request.getUserId(), request.getPassword())
                .map(member -> {
                    session.setAttribute("id", member.getId());
                    return "member/main";
                }).orElse("member/login");
    }

    public List<MemberResponse> findAll() {
        return   memberRepository.findAll()
                .stream()
                .map(MemberResponse::from)
                .toList();
    }

    public Member findById(Long id) {
        return memberRepository.findById(id).orElseThrow(IllegalArgumentException::new);
    }

    @Transactional
    public void delete(Long id) {
        memberRepository.deleteById(id);
    }

    @Transactional
    public void update(MemberRequest request) {
        Member member = findById(request.getId());
        member.update(request);
    }

    public MemberResponse findByUserId(String userId) {
        Member member = memberRepository.findByUserId(userId);
        return MemberResponse.from(member);
    }
}
