package org.example.project2.domain.member.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.project2.domain.item.entity.Items;
import org.example.project2.domain.likes.entity.Likes;
import org.example.project2.global.entity.BaseTimeEntity;

import java.util.ArrayList;
import java.util.List;

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

    private String profileImage;

    /**
     * orphanRemoval = true란?
     * @OneToMany 또는 @OneToOne 관계에서 사용되며,
     * 부모 엔티티 컬렉션에서 자식 엔티티를 제거(remove) 하면,
     * 그 자식 엔티티는 자동으로 DB에서 삭제됩니다.
     */
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Items> items = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Likes> likes = new ArrayList<>();

    @Builder
    private Member(String email, String nickname, String password, String authority,
                   String provider) {
        this.memberBase = new MemberBase(email, nickname, password, authority);
        //default image
        this.profileImage = "https://cdn-icons-png.flaticon.com/512/149/149071.png";
        this.provider = provider;
    }

    public void updateProfileImage(String profileImage) {
    }
}
