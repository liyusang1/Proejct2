package org.example.project2.domain.member.repository;

import org.example.project2.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository
    extends JpaRepository<Member, Long>{

    Optional<Member> findByMemberBaseEmail(String email);

    Optional<Member> findByMemberBaseEmailAndProvider(String email, String provider);

    int countAllBy();
}
