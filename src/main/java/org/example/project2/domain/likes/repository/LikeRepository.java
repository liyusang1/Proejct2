package org.example.project2.domain.likes.repository;

import org.example.project2.domain.likes.entity.Likes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository
        extends JpaRepository<Likes, Long> {

    Optional<Likes> findByMember_IdAndItems_Id(Long memberId, Long itemId);

    long countByItems_IdAndStatusTrue(Long itemId);
}
