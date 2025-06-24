package org.example.project2.domain.likes.repository;

import org.example.project2.domain.likes.entity.Likes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface LikeRepository
        extends JpaRepository<Likes, Long> {

    Optional<Likes> findByMember_IdAndItems_Id(Long memberId, Long itemId);

    long countByItems_IdAndStatusTrue(Long itemId);

    int countByMember_IdAndStatusTrue(Long memberId);

    @Query("SELECT COUNT(l) FROM Likes l WHERE l.status = true AND l.items.member.id = :memberId")
    int countTotalLikesReceivedByMember(@Param("memberId") Long memberId);

    int countAllByStatusTrue();

    int countAllByMember_IdAndStatusTrue(Long member_id);
}
