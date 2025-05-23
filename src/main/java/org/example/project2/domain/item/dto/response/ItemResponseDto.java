package org.example.project2.domain.item.dto.response;


import org.example.project2.domain.item.entity.Items;

public record ItemResponseDto(
        Long id,
        String name,
        String description,
        int price,
        String imageUrl
) {

    public static ItemResponseDto fromEntity(Items items) {
        return new ItemResponseDto(
                items.getId(),
                items.getName(),
                items.getDescription(),
                items.getPrice(),
                items.getImageUrl()
        );
    }
}
