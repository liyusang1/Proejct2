package org.example.project2.domain.restaurants.dto.request;

public record PutRestaurantRequestDto(
        String name,
        String address,
        String description,
        String imageUrl
) {
}
