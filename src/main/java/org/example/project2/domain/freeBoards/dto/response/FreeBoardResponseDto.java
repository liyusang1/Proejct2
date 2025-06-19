package org.example.project2.domain.freeBoards.dto.response;

import org.example.project2.domain.freeBoards.entity.FreeBoards;
import org.example.project2.global.util.DataFormatter;

public record FreeBoardResponseDto(
        Long id,
        String title,
        String content,
        String category,
        String emoji,
        int viewCount,
        int likeCount,
        int dislikeCount,
        String createAt,
        String writer,
        String writerProfileImage,
        String writerProfileMessage,
        Boolean writerBadge,
        Long writerId,
        int replyCount
) {
    public static FreeBoardResponseDto fromEntity(FreeBoards board, int replyCount) {
        return new FreeBoardResponseDto(
                board.getId(),
                board.getTitle(),
                board.getContent(),
                board.getCategory(),
                board.getEmoji(),
                board.getViewCount(),
                board.getLikeCount(),
                board.getDislikeCount(),
                DataFormatter.getFormattedCreatedAtWithTime(board.getCreatedAt()),
                board.getMember().getMemberBase().getNickname(),
                board.getMember().getProfileImage(),
                board.getMember().getProfileMessage(),
                board.getMember().getWriterBadge(),
                board.getMember().getId(),
                replyCount
        );
    }

    public static FreeBoardResponseDto fromEntity(FreeBoards board) {
        return fromEntity(board, 0);
    }
}
