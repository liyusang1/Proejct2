package org.example.project2.domain.freeBoards.dto.request;

import org.example.project2.domain.freeBoards.entity.FreeBoardReplies;
import org.example.project2.domain.freeBoards.entity.FreeBoards;
import org.example.project2.domain.member.entity.Member;

public record PostFreeBoardReplyRequestDto(
        String reply,
        Long freeBoardId
) {
    public FreeBoardReplies toEntity(Member member, FreeBoards freeBoards) {
        if (reply != null && reply.length() > 100) {
            throw new IllegalArgumentException("댓글(reply)은 100자 이하로 입력해주세요.");
        }
        return FreeBoardReplies.builder()
                .reply(reply)
                .member(member)
                .freeBoards(freeBoards)
                .build();
    }
}
