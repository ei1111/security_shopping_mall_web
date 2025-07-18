package com.web.member.repository;

import com.web.member.entity.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByUserIdAndPassword(String userId, String password);
    Member findByUserId(String userId);
}
