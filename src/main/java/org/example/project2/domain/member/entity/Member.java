package org.example.project2.domain.member.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.project2.domain.follow.entity.Follow;
import org.example.project2.domain.freeBoards.entity.FreeBoards;
import org.example.project2.domain.item.entity.Items;
import org.example.project2.domain.likes.entity.Likes;
import org.example.project2.domain.notifications.entity.Notifications;
import org.example.project2.domain.recipes.entity.Bookmark;
import org.example.project2.domain.recipes.entity.Recipes;
import org.example.project2.domain.reply.entity.Replies;
import org.example.project2.domain.reports.entity.Reports;
import org.example.project2.domain.restaurant_lists.entity.RestaurantLists;
import org.example.project2.domain.restaurants.entity.Restaurants;
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

    private String profileMessage;

    @Column(nullable = false)
    private Boolean writerBadge = false;

    /**
     * orphanRemoval = true란?
     *
     * @OneToMany 또는 @OneToOne 관계에서 사용되며,
     * 부모 엔티티 컬렉션에서 자식 엔티티를 제거(remove) 하면,
     * 그 자식 엔티티는 자동으로 DB에서 삭제됩니다.
     */
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Items> items = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Likes> likes = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Replies> Replies = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FreeBoards> freeBoards = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Recipes> recipes = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RestaurantLists> restaurantLists = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reports> reports = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Notifications> notifications = new ArrayList<>();

    @OneToMany(mappedBy = "follower", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Follow> followings = new ArrayList<>();

    @OneToMany(mappedBy = "following", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Follow> followers = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Bookmark> bookmarks = new ArrayList<>();

    @Builder
    private Member(String email, String nickname, String password, String authority,
                   String provider, String profileImage) {
        this.memberBase = new MemberBase(email, nickname, password, authority);
        //default image
        if(provider == null) {
            this.profileImage = "https://cdn-icons-png.flaticon.com/512/149/149071.png";
        }
        this.provider = provider;
        this.profileImage = profileImage;
    }

    public void updateMemberInfo(String profileImage,
                                 String profileMessage,
                                 String nickname) {
        this.profileImage = profileImage;
        this.profileMessage = profileMessage;
        this.memberBase.changeNickname(nickname);
    }

    public void updateWriterBadge() {
            this.writerBadge = true;
    }

    public void updateProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }
}
