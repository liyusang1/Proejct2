package org.example.project2.domain.member.dto.response;


import org.example.project2.domain.member.entity.Member;

public record LoginResponseDto(
        String email,
        String name,
        String token,
        Long userId,
        String profileImage
) {

    public static LoginResponseDto fromEntity(Member member, String token) {
        return new LoginResponseDto(
                member.getMemberBase().getEmail(),
                member.getMemberBase().getNickname(),
                token,
                member.getId(),
                member.getProfileImage()
        );
    }
}
