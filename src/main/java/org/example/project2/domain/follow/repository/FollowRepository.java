package org.example.project2.domain.follow.repository;

import org.example.project2.domain.follow.entity.Follow;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FollowRepository
        extends JpaRepository<Follow, Long> {

    Optional<Follow> findByFollower_IdAndFollowing_Id(Long followerId, Long followingId);

    // 특정 팔로워가 팔로우하는 모든 관계 중 상태가 true인 것만 가져옴
    List<Follow> findAllByFollower_IdAndStatusTrue(Long followerId);
    // 특정 팔로잉을 하는 모든 팔로워 관계 중 상태가 true인 것만 가져옴
    List<Follow> findAllByFollowing_IdAndStatusTrue(Long followingId);
    Optional<Follow> findByFollower_IdAndFollowing_IdAndStatusTrue(Long followerId, Long followingId);
}
