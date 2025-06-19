package org.example.project2.domain.follow.dto.response;


import org.example.project2.domain.follow.entity.Follow;

public record FollowerResponseDto(
        Long followId,
        Long memberId,
        String nickname,
        String profileImage
) {
    public static FollowerResponseDto fromEntity(Follow follow) {
        return new FollowerResponseDto(
                follow.getId(),
                follow.getFollower().getId(),
                follow.getFollower().getMemberBase().getNickname(),
                follow.getFollower().getProfileImage()
        );
    }
}

