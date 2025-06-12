package org.example.project2.domain.item.dto.response;


public record InsightResponseDto(
        int totalPostsCount,
        int totalLikesCount,
        int totalCommentsCount,
        int totalMembersCount,
        int totalRestaurantPicksCount,
        int totalRecipePicksCount
) {
}
