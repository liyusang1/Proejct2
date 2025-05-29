package org.example.project2.domain.reply.dto.response;

import org.example.project2.domain.reply.entity.Replies;
import org.example.project2.global.util.DataFormatter;

public record ReplyResponseDto(
        Long replyId,
        String reply,
        String writer,
        String writerProfileImage,
        String writerProfileMessage,
        Boolean writerBadge,
        String createdAt
) {
    public static ReplyResponseDto fromEntity(Replies reply) {
        return new ReplyResponseDto(
                reply.getId(),
                reply.getReply(),
                reply.getMember().getMemberBase().getNickname(),
                reply.getMember().getProfileImage(),
                reply.getMember().getProfileMessage(),
                reply.getMember().getWriterBadge(),
                DataFormatter.getFormattedCreatedAtWithTime(reply.getCreatedAt())
        );
    }
}
