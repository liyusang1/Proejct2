package org.example.project2.domain.recipes.dto.response;

import org.springframework.data.domain.Page;
import java.util.List;

public record MyRecipeListResponseDto(
        List<RecipeDetailResponseDto> recipes, // 내가 쓴 레시피 상세 정보 목록
        int currentPage,
        int totalPages,
        long totalElements,
        boolean isFirst,
        boolean isLast
) {
    public static MyRecipeListResponseDto fromPage(Page<RecipeDetailResponseDto> recipeDetailPage) {
        return new MyRecipeListResponseDto(
                recipeDetailPage.getContent(),
                recipeDetailPage.getNumber(),
                recipeDetailPage.getTotalPages(),
                recipeDetailPage.getTotalElements(),
                recipeDetailPage.isFirst(),
                recipeDetailPage.isLast()
        );
    }
}