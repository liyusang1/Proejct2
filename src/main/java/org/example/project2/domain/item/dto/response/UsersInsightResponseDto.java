package org.example.project2.domain.item.dto.response;


public record UsersInsightResponseDto(
        int myPostsCount,
        int myLikesCount,
        int myCommentsCount,
        int myRestaurantPicksCount,
        int myRecipePicksCount
) {
}
