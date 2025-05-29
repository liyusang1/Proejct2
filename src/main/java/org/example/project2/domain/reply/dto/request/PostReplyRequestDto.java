package org.example.project2.domain.reply.dto.request;

import org.example.project2.domain.item.entity.Items;
import org.example.project2.domain.member.entity.Member;
import org.example.project2.domain.reply.entity.Replies;

public record PostReplyRequestDto(
        String reply
) {

    public Replies toEntity(Member member, Items items) {
        return Replies.builder()
                .reply(this.reply)
                .member(member)
                .items(items)
                .build();
    }
}
