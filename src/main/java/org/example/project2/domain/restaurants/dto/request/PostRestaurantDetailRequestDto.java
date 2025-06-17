package org.example.project2.domain.restaurants.dto.request;

import org.example.project2.domain.restaurantDetails.entity.RestaurantDetails;
import org.example.project2.domain.restaurants.entity.Restaurants;

public record PostRestaurantDetailRequestDto(
        String name,
        int price,
        String imageUrl,
        String description
){
    public RestaurantDetails toEntity(Restaurants restaurants) {
        return RestaurantDetails.builder()
                .name(name)
                .price(price)
                .imageUrl(imageUrl)
                .description(description)
                .restaurants(restaurants)
                .build();
    }
}
