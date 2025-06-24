package org.example.project2.domain.recipes.dto.response;

import org.example.project2.domain.recipes.entity.Recipes;
import org.example.project2.global.util.DataFormatter;

import java.util.List;

public record RecipeDetailResponseDto(
        Long id,
        String userProfileImage,
        String userName,
        String imageUrl,
        String title,
        String description,
        List<String> ingredients,
        List<String> steps,
        int time,
        String level,
        String createdAt,
        int viewCount,
        String userProfileMessage,
        String updateAt,
        Long writerUserId
) {
    public static RecipeDetailResponseDto fromEntity(Recipes recipe) {
        return new RecipeDetailResponseDto(
                recipe.getId(),
                recipe.getMember().getProfileImage(),
                recipe.getMember().getMemberBase().getNickname(),
                recipe.getImageUrl(),
                recipe.getTitle(),
                recipe.getDescription(),
                recipe.getIngredients(),
                recipe.getSteps(),
                recipe.getTime(),
                recipe.getLevel(),
                DataFormatter.getFormattedCreatedAtWithTime(recipe.getCreatedAt()),
                recipe.getViewCount(),
                recipe.getMember().getProfileMessage(),
                DataFormatter.getFormattedCreatedAtWithTime(recipe.getUpdatedAt()),
                recipe.getMember().getId()
        );
    }
}