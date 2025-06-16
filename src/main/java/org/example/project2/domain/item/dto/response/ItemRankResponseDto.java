package org.example.project2.domain.item.dto.response;


import org.example.project2.domain.item.entity.Items;

public record ItemRankResponseDto(
        Long id,
        String name,
        String description,
        String imageUrl,
        Long likeCount,
        int replyCount
) {
    public static ItemRankResponseDto fromEntity(Items items, Long likeCount) {
        return new ItemRankResponseDto(
                items.getId(),
                items.getName(),
                items.getDescription(),
                items.getImageUrl(),
                likeCount,
                items.getReplies().size()
        );
    }
}
