package org.example.project2.domain.restaurants.dto.response;

import org.example.project2.domain.restaurants.entity.Restaurants;

public record RestaurantResponseDto(
        Long restaurantId,
        String name,
        String address,
        String description,
        String imageUrl,
        Long listId
) {
    public static RestaurantResponseDto from(Restaurants restaurants) {
        return new RestaurantResponseDto(
                restaurants.getId(),
                restaurants.getName(),
                restaurants.getAddress(),
                restaurants.getDescription(),
                restaurants.getImageUrl(),
                restaurants.getRestaurantLists().getId()

        );
    }
}
