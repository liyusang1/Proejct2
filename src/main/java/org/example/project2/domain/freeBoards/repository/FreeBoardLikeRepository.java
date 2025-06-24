package org.example.project2.domain.freeBoards.repository;

import org.example.project2.domain.freeBoards.entity.FreeBoardLike;
import org.example.project2.domain.freeBoards.entity.FreeBoards;
import org.example.project2.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FreeBoardLikeRepository extends JpaRepository<FreeBoardLike, Long> {
    Optional<FreeBoardLike> findByFreeBoardAndMember(FreeBoards freeBoard, Member member);
}
