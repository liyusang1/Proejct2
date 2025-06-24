package org.example.project2.domain.recipes.dto.response;

import org.example.project2.domain.recipes.entity.Recipes;
import org.example.project2.global.util.DataFormatter;

public record BookmarkResponseDto(
        Long id,
        String title,
        String imageUrl,
        String userName, // 레시피 작성자 이름
        String userProfileImage, // 레시피 작성자 프로필 이미지
        int time,
        String level,
        int viewCount,
        String createdAt
) {
    public BookmarkResponseDto(Recipes recipes) { // ⭐ Recipes 엔티티 받음
        this(
                recipes.getId(),
                recipes.getTitle(),
                recipes.getImageUrl(),
                recipes.getMember().getMemberBase().getNickname(), // ⭐ MemberBase에서 닉네임 가져오기
                recipes.getMember().getProfileImage(), // Member 엔티티에 직접 profileImage 필드가 있음
                recipes.getTime(),
                recipes.getLevel(),
                recipes.getViewCount(),
                DataFormatter.getFormattedCreatedAtWithTime(recipes.getCreatedAt())
        );
    }
}