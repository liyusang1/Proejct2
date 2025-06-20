package org.example.project2.domain.restaurantDetails.dto.response;

import org.example.project2.domain.restaurantDetails.entity.RestaurantDetails;
import org.example.project2.domain.restaurant_lists.dto.response.ListResponseDto;
import org.example.project2.domain.restaurant_lists.entity.RestaurantLists;
import org.example.project2.domain.restaurants.entity.Restaurants;

public record RestaurantDetailsResponseDto(
        Long id,
        String name,
        int price,
        String imageUrl,
        String description
) {
    public static RestaurantDetailsResponseDto from(RestaurantDetails restaurantDetails) {
        return new RestaurantDetailsResponseDto(
                restaurantDetails.getId(),
                  restaurantDetails.getName(),
                restaurantDetails.getPrice(),
                restaurantDetails.getImageUrl(),
                restaurantDetails.getDescription()
        );
    }
}
