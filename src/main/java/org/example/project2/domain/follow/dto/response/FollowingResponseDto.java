package org.example.project2.domain.follow.dto.response;


import org.example.project2.domain.follow.entity.Follow;

public record FollowingResponseDto(
        Long followId,
        Long memberId,
        String nickname,
        String profileImage
) {
    public static FollowingResponseDto fromEntity(Follow follow) {
        return new FollowingResponseDto(
                follow.getId(),
                follow.getFollowing().getId(),
                follow.getFollowing().getMemberBase().getNickname(),
                follow.getFollowing().getProfileImage()
        );
    }
}

