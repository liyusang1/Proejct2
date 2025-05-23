package org.example.project2.domain.member.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.project2.global.entity.BaseTimeEntity;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Embedded
    protected MemberBase memberBase;

    private String provider;

    @Builder
    private Member(String email, String nickname, String password, String authority,
                   String provider) {
        this.memberBase = new MemberBase(email, nickname, password, authority);
        this.provider = provider;
    }

    public void updateProfileImage(String profileImage) {

    }
}
