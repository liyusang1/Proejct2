package org.example.project2.domain.restaurant_lists.dto.request;

import jakarta.validation.constraints.NotNull;
import org.example.project2.domain.member.entity.Member;
import org.example.project2.domain.restaurant_lists.entity.RestaurantLists;

public record CreateListRequestDto(
        String title,
        String description,
        @NotNull
        Boolean isPublic
){
        public RestaurantLists toEntity(Member member) {
                return RestaurantLists.builder()
                        .member(member)
                        .title(title)
                        .description(description)
                        .isPublic(isPublic)
                        .build();
        }

}
