package org.example.project2.domain.restaurants.dto.request;

import org.example.project2.domain.restaurant_lists.entity.RestaurantLists;
import org.example.project2.domain.restaurants.entity.Restaurants;

import java.util.List;

public record CreateRestaurantRequestDto(
        String name,
        String address,
        String description,
        String imageUrl,
        List<PostRestaurantDetailRequestDto> detailList
){
    public Restaurants toEntity(RestaurantLists restaurantLists) {
        return Restaurants.builder()
                .name(name)
                .restaurantList(restaurantLists)
                .description(description)
                .address(address)
                .imageUrl(imageUrl)
                .build();
    }
}
