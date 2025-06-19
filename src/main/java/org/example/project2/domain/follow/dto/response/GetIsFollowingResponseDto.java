package org.example.project2.domain.follow.dto.response;


public record GetIsFollowingResponseDto(
        boolean isFollowing
) {
    public static GetIsFollowingResponseDto fromEntity(boolean isFollowing) {
        return new GetIsFollowingResponseDto(
                isFollowing
        );
    }
}

