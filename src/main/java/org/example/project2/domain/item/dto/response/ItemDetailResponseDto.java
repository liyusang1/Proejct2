package org.example.project2.domain.item.dto.response;


import org.example.project2.domain.item.entity.Items;

public record ItemDetailResponseDto(
        Long id,
        String name,
        String description,
        int price,
        String imageUrl
) {

    public static ItemDetailResponseDto fromEntity(Items items) {
        return new ItemDetailResponseDto(
                items.getId(),
                items.getName(),
                items.getDescription(),
                items.getPrice(),
                items.getImageUrl()
        );
    }
}
