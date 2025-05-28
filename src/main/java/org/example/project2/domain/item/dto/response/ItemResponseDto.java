package org.example.project2.domain.item.dto.response;


import org.example.project2.domain.item.entity.Items;
import org.example.project2.global.util.DataFormatter;

public record ItemResponseDto(
        Long id,
        String name,
        String description,
        int price,
        String imageUrl,
        String createdAt,
        String writer
) {

    public static ItemResponseDto fromEntity(Items items) {
        return new ItemResponseDto(
                items.getId(),
                items.getName(),
                items.getDescription(),
                items.getPrice(),
                items.getImageUrl(),
                DataFormatter.getFormattedCreatedAt(items.getCreatedAt()),
                items.getMember().getMemberBase().getNickname()
        );
    }
}
