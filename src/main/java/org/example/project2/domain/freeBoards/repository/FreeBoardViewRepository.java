package org.example.project2.domain.freeBoards.repository;

import org.example.project2.domain.freeBoards.entity.FreeBoardView;
import org.example.project2.domain.freeBoards.entity.FreeBoards;
import org.example.project2.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface FreeBoardViewRepository extends JpaRepository<FreeBoardView, Long> {
    boolean existsByFreeBoardAndMemberAndViewedAt(FreeBoards board, Member member, LocalDate viewedAt);
    boolean existsByFreeBoardAndIpAddressAndViewedAt(FreeBoards board, String ipAddress, LocalDate viewedAt);
}
