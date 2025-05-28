package org.example.project2.domain.likes.dto.request;

import org.example.project2.domain.item.entity.Items;
import org.example.project2.domain.likes.entity.Likes;
import org.example.project2.domain.member.entity.Member;

public record PostLikeRequestDto(
        Long itemId
) {

    public Likes toEntity(Member member, Items items) {
        return Likes.builder()
                .status(true)
                .member(member)
                .items(items)
                .build();
    }
}
