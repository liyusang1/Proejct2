package org.example.project2.domain.restaurants.dto.request;

import jakarta.validation.constraints.NotNull;

public record PutRestaurantRequestDto(
        Long id,
        String name,
        String address,
        String description,
        String imageUrl
) {
}
