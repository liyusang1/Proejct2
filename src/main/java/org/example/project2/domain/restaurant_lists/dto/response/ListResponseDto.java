package org.example.project2.domain.restaurant_lists.dto.response;


import org.example.project2.domain.restaurant_lists.entity.RestaurantLists;
import org.example.project2.domain.restaurants.dto.response.RestaurantResponseDto;

import java.util.List;

public record ListResponseDto(
        Long listId,
        String listTitle,
        String description,
        boolean isPublic,
        String memberName,
        String memberProfileImageUrl,
        Long memberId,
        List<RestaurantResponseDto> restaurantList
) {

    public static ListResponseDto from(RestaurantLists restaurantLists, List<RestaurantResponseDto> responseDtoList) {
        return new ListResponseDto(
                restaurantLists.getId(),
                restaurantLists.getTitle(),
                restaurantLists.getDescription(),
                restaurantLists.isPublic(),
                restaurantLists.getMember().getMemberBase().getNickname(),
                restaurantLists.getMember().getProfileImage(),
                restaurantLists.getMember().getId(),
                responseDtoList
        );
    }
}