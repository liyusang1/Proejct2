package org.example.project2.domain.reply.dto.request;

import org.example.project2.domain.item.entity.Items;
import org.example.project2.domain.member.entity.Member;
import org.example.project2.domain.reply.entity.Replies;

public record PostReplyRequestDto(
        String reply,
        Long itemId
) {

    public Replies toEntity(Member member, Items items) {
        if (reply != null && reply.length() > 100) {
            throw new IllegalArgumentException("댓글(reply)은 100자 이하로 입력해주세요.");
        }
        return Replies.builder()
                .reply(this.reply)
                .member(member)
                .items(items)
                .build();
    }
}
