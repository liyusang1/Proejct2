package org.example.project2.domain.reply.repository;

import org.example.project2.domain.reply.entity.Replies;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReplyRepository
        extends JpaRepository<Replies, Long> {

    // 1. Items 엔티티의 id를 기준으로 댓글을 가져오기
    List<Replies> findAllByItems_Id(Long itemsId);

    // 2. Member 엔티티의 id와 Items 엔티티의 id로 댓글을 가져오기
    List<Replies> findAllByItems_IdAndMember_Id(Long itemsId, Long memberId);
}
