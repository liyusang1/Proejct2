package org.example.project2.domain.item.dto.response;


import org.example.project2.domain.item.entity.Items;
import org.example.project2.global.util.DataFormatter;

import static org.example.project2.domain.constant.itemConstantValue.BEST_LIKE_THRESHOLD;

public record ItemDetailResponseDto(
        Long id,
        String name,
        String description,
        int price,
        String imageUrl,
        String createdAt,
        String writer,
        String writerProfileImage,
        String writerProfileMessage,
        Boolean writerBadge,
        Boolean isBest,
        Long likeCount
) {

    public static ItemDetailResponseDto fromEntity(Items items,Long likeCount) {
        boolean isBest = likeCount >= BEST_LIKE_THRESHOLD;

        return new ItemDetailResponseDto(
                items.getId(),
                items.getName(),
                items.getDescription(),
                items.getPrice(),
                items.getImageUrl(),
                DataFormatter.getFormattedCreatedAt(items.getCreatedAt()),
                items.getMember().getMemberBase().getNickname(),
                items.getMember().getProfileImage(),
                items.getMember().getProfileMessage(),
                items.getMember().getWriterBadge(),
                isBest,
                likeCount
        );
    }
}
