package org.example.project2.domain.member.dto.response;


import org.example.project2.domain.member.entity.Member;
import org.example.project2.global.util.DataFormatter;

public record MemberInfoResponseDto(

        String email,
        String nickname,
        String profileImage,
        Boolean writerBadge,
        String profileMessage,
        String createdAt,
        Long userId,
        Integer postCount,
        Integer likeCount,
        Boolean hasUnreadNotifications
) {

    public static MemberInfoResponseDto fromEntity(Member member,int likeCount, boolean hasUnreadNotifications) {
        return new MemberInfoResponseDto(
                member.getMemberBase().getEmail(),
                member.getMemberBase().getNickname(),
                member.getProfileImage(),
                member.getWriterBadge(),
                member.getProfileMessage(),
                DataFormatter.getFormattedCreatedAt(member.getCreatedAt()),
                member.getId(),
                member.getItems().size(),
                likeCount,
                hasUnreadNotifications
        );
    }
}
