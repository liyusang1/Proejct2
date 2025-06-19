package org.example.project2.domain.freeBoards.dto.request;

import jakarta.validation.constraints.NotBlank;
import org.example.project2.domain.freeBoards.entity.FreeBoards;
import org.example.project2.domain.member.entity.Member;

public record PostFreeBoardRequestDto(
        @NotBlank(message = "title은 필수입니다.")
        String title,
        @NotBlank(message = "content는 필수입니다.")
        String content,
        String category,
        String emoji

) {
    public FreeBoards toEntity(Member member) {
        return FreeBoards.builder()
                .title(title)
                .content(content)
                .category(category)
                .emoji(emoji)

                .member(member)
                .build();
    }

}
