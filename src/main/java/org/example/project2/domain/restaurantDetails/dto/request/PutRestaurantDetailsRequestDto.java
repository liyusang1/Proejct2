package org.example.project2.domain.restaurantDetails.dto.request;

public record PutRestaurantDetailsRequestDto(
        Long id,
        String name,
        int price,
        String imageUrl,
        String description
) {
}
