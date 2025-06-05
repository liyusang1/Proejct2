package org.example.project2.domain.item.dto.response;


import org.example.project2.domain.item.entity.Items;
import org.example.project2.global.util.DataFormatter;

import static org.example.project2.domain.item.constant.itemConstantValue.BEST_LIKE_THRESHOLD;

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
        Long likeCount,
        Long writerId,
        int viewCount,
        Boolean reaction1,
        Boolean reaction2,
        Boolean reaction3,
        Boolean reaction4,
        Boolean reaction5,
        Boolean reaction6,
        Boolean reaction7,
        Boolean reaction8,
        Boolean reaction9
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
                likeCount,
                items.getMember().getId(),
                items.getViewCount(),
                items.isReaction1(),
                items.isReaction2(),
                items.isReaction3(),
                items.isReaction4(),
                items.isReaction5(),
                items.isReaction6(),
                items.isReaction7(),
                items.isReaction8(),
                items.isReaction9()
        );
    }
}
