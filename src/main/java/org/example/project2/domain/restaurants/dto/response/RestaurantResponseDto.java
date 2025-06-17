package org.example.project2.domain.restaurants.dto.response;

import org.example.project2.domain.restaurant_lists.dto.response.ListResponseDto;
import org.example.project2.domain.restaurant_lists.entity.RestaurantLists;
import org.example.project2.domain.restaurants.entity.Restaurants;

public record RestaurantResponseDto(
        String name,
        String address,
        String description,
        String imageUrl,
        Long listId
) {
    public static RestaurantResponseDto from(Restaurants restaurants) {
        return new RestaurantResponseDto(
                restaurants.getName(),
                restaurants.getAddress(),
                restaurants.getDescription(),
                restaurants.getImageUrl(),
                restaurants.getRestaurantLists().getId()

        );
    }
}
