package org.example.project2.domain.recipes.dto.response;

import org.example.project2.domain.recipes.entity.Recipes;

import java.util.List;

public record RecipeDetailResponseDto(
        Long id,
        String userProfileImage,
        String userName,
        String imageUrl,
        String title,
        String description,
        List<String> ingredients,
        List<String> steps
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
                recipe.getSteps()
        );
    }
}