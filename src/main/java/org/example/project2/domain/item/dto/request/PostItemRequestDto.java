package org.example.project2.domain.item.dto.request;

import jakarta.validation.constraints.NotNull;
import org.example.project2.domain.item.entity.Items;
import org.example.project2.domain.member.entity.Member;

public record PostItemRequestDto(
        @NotNull(message = "name은 필수값입니다")
        String name,
        @NotNull(message = "description 필수값입니다")
        String description,
        String imageUrl
) {

    public Items toEntity(Member member) {
        return Items.builder()
                .name(name)
                .description(description)
                .price(0)
                .imageUrl(imageUrl)
                .member(member)
                .build();
    }
}
