package org.example.project2.domain.freeBoards.repository;

import org.example.project2.domain.freeBoards.entity.FreeBoardReplies;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FreeBoardReplyRepository extends JpaRepository<FreeBoardReplies, Long> {
    List<FreeBoardReplies> findAllByFreeBoards_Id(Long freeBoardId);
    List<FreeBoardReplies> findAllByFreeBoards_IdAndMember_Id(Long freeBoardId, Long memberId);
}
