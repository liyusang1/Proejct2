package org.example.project2.domain.restaurant_lists.dto.request;

import jakarta.validation.constraints.NotNull;

public record PutListRequestDto(
        Long id,
        String title,
        String description,
        @NotNull
        boolean isPublic
) {
}
