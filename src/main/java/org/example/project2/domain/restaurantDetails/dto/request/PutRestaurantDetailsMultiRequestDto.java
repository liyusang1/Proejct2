package org.example.project2.domain.restaurantDetails.dto.request;

import java.util.List;

public record PutRestaurantDetailsMultiRequestDto(
        List<PutRestaurantDetailsRequestDto> list
) {
}
