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
        String imageUrl,
        Boolean reaction1,
        Boolean reaction2,
        Boolean reaction3,
        Boolean reaction4,
        Boolean reaction5,
        Boolean reaction6,
        Boolean reaction7,
        Boolean reaction8,
        Boolean reaction9
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
                .reaction1(reaction1)
                .reaction2(reaction2)
                .reaction3(reaction3)
                .reaction4(reaction4)
                .reaction5(reaction5)
                .reaction6(reaction6)
                .reaction7(reaction7)
                .reaction8(reaction8)
                .reaction9(reaction9)
                .build();
    }
}
