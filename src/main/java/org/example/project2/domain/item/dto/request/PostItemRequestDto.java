package org.example.project2.domain.item.dto.request;

import jakarta.validation.constraints.NotNull;
import org.example.project2.domain.item.entity.Items;
import org.example.project2.domain.member.entity.Member;

public record PostItemRequestDto(
        @NotNull(message = "name은 필수값입니다")
        String name,
        @NotNull(message = "description 필수값입니다")
        String description,
        Integer price,
        String imageUrl
) {

    public PostItemRequestDto {
        if (price == null) {
            price = 0;
        }
    }

    public Items toEntity(Member member) {
        return Items.builder()
                .name(name)
                .description(description)
                .price(price)
                .imageUrl(imageUrl)
                .member(member)
                .build();
    }
}
