package org.example.project2.domain.recipes.dto.request;

import org.example.project2.domain.member.entity.Member;
import org.example.project2.domain.recipes.entity.Bookmark;
import org.example.project2.domain.recipes.entity.Recipes;

public record BookmarkRequestDto(
        Long recipeId
) {
    // recipe 필드명 변경에 따라 toEntity 메서드도 수정 (현재 서비스에서는 직접 빌더 사용)
    public Bookmark toEntity(Member member, Recipes recipe) {
        return Bookmark.builder()
                .status(true) // 기본적으로 활성 상태로 설정
                .member(member)
                .recipe(recipe) // 'recipes'에서 'recipe'로 변경
                .build();
    }
}
