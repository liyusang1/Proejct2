package org.example.project2.domain.member.dto.response;


import org.example.project2.domain.member.entity.Member;

public record MemberInfoResponseDto(

        String email,
        String nickname,
        String profileImage,
        Boolean writerBadge
) {

    public static MemberInfoResponseDto fromEntity(Member member) {
        return new MemberInfoResponseDto(
                member.getMemberBase().getEmail(),
                member.getMemberBase().getNickname(),
                member.getProfileImage(),
                member.getWriterBadge()
        );
    }
}
