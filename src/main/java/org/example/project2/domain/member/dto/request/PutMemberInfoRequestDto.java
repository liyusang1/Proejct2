package org.example.project2.domain.member.dto.request;

public record PutMemberInfoRequestDto(

        String profileImage,
        String profileMessage,
        String nickname
) {
}
