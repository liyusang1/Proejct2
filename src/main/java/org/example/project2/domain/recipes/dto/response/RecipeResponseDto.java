package org.example.project2.domain.recipes.dto.response;

import org.example.project2.domain.recipes.entity.Recipes;

public record RecipeResponseDto(
        Long id,
        String title,
        String imageUrl,
        String userName,
        String userProfileImage,
        int viewCount
) {
    public static RecipeResponseDto fromEntity(Recipes recipe) {
        return new RecipeResponseDto(
                recipe.getId(),
                recipe.getTitle(),
                recipe.getImageUrl(),
                recipe.getMember().getMemberBase().getNickname(),
                recipe.getMember().getProfileImage(),
                recipe.getViewCount()
        );
    }
}
