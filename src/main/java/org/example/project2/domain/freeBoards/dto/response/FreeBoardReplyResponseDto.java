package org.example.project2.domain.freeBoards.dto.response;

import org.example.project2.domain.freeBoards.entity.FreeBoardReplies;
import org.example.project2.global.util.DataFormatter;

public record FreeBoardReplyResponseDto(
        Long replyId,
        String reply,
        String writer,
        String writerProfileImage,
        String writerProfileMessage,
        Boolean writerBadge,
        String createdAt,
        Long writerId
) {
    public static FreeBoardReplyResponseDto fromEntity(FreeBoardReplies reply) {
        return new FreeBoardReplyResponseDto(
                reply.getId(),
                reply.getReply(),
                reply.getMember().getMemberBase().getNickname(),
                reply.getMember().getProfileImage(),
                reply.getMember().getProfileMessage(),
                reply.getMember().getWriterBadge(),
                DataFormatter.getFormattedCreatedAtWithTime(reply.getCreatedAt()),
                reply.getMember().getId()
        );
    }
}
