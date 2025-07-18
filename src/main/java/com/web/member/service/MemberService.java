package com.web.member.service;

import com.web.member.entity.Member;
import com.web.member.form.MemberRequest;
import com.web.member.form.MemberResponse;
import com.web.member.repository.MemberRepository;
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
    public MemberResponse save(MemberRequest request) {
        Member member = memberRepository.save(request.fromMeber(passwordEncoder));
        return MemberResponse.from(member);
    }

    public List<MemberResponse> findAll() {
        return   memberRepository.findAll()
                .stream()
                .map(MemberResponse::from)
                .toList();
    }

    public MemberResponse findById(Long id) {
        Member member = memberRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        return MemberResponse.from(member);
    }

    @Transactional
    public void delete(Long id) {
        memberRepository.deleteById(id);
    }

    @Transactional
    public void update(MemberRequest request) {
        Member member = memberRepository.findById(request.getId()).orElseThrow(IllegalArgumentException::new);
        member.update(request, passwordEncoder);
    }

    public MemberResponse findByUserId(String userId) {
        Member member = memberRepository.findByUserId(userId);
        return MemberResponse.from(member);
    }
}
