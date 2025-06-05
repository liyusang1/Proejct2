package org.example.project2.domain.item.dto.response;


import org.example.project2.domain.item.entity.Items;
import org.example.project2.global.util.DataFormatter;

import static org.example.project2.domain.item.constant.itemConstantValue.BEST_LIKE_THRESHOLD;

public record ItemResponseDto(
        Long id,
        String name,
        String description,
        int price,
        String imageUrl,
        String createdAt,
        String writer,
        Boolean isLiked,
        String writerProfileImage,
        String writerProfileMessage,
        Boolean writerBadge,
        Boolean isBest,
        Long writerId
) {
    public static ItemResponseDto fromEntity(Items items, Boolean isLiked, Long likeCount) {
        boolean isBest = likeCount >= BEST_LIKE_THRESHOLD;

        return new ItemResponseDto(
                items.getId(),
                items.getName(),
                items.getDescription(),
                items.getPrice(),
                items.getImageUrl(),
                DataFormatter.getFormattedCreatedAt(items.getCreatedAt()),
                items.getMember().getMemberBase().getNickname(),
                isLiked,
                items.getMember().getProfileImage(),
                items.getMember().getProfileMessage(),
                items.getMember().getWriterBadge(),
                isBest,
                items.getMember().getId()
        );
    }
}
