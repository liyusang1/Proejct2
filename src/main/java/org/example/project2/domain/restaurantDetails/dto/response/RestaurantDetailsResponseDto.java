package org.example.project2.domain.restaurantDetails.dto.response;

import org.example.project2.domain.restaurantDetails.entity.RestaurantDetails;
import org.example.project2.domain.restaurant_lists.dto.response.ListResponseDto;
import org.example.project2.domain.restaurant_lists.entity.RestaurantLists;
import org.example.project2.domain.restaurants.dto.response.RestaurantResponseDto;
import org.example.project2.domain.restaurants.entity.Restaurants;

import java.util.List;

public record RestaurantDetailsResponseDto(
        Long id,
        String name,
        int price,
        String imageUrl,
        String description,
        RestaurantResponseDto restaurant
) {
    public static RestaurantDetailsResponseDto from(RestaurantDetails restaurantDetails) {
        return new RestaurantDetailsResponseDto(
                restaurantDetails.getId(),
                restaurantDetails.getName(),
                restaurantDetails.getPrice(),
                restaurantDetails.getImageUrl(),
                restaurantDetails.getDescription(),
                RestaurantResponseDto.from(restaurantDetails.getRestaurants())
        );
    }
}
