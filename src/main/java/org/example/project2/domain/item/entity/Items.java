package org.example.project2.domain.item.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.project2.domain.likes.entity.Likes;
import org.example.project2.domain.member.entity.Member;
import org.example.project2.domain.reply.entity.Replies;
import org.example.project2.global.entity.BaseTimeEntity;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Items extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Long id;

    private String name;

    private String description;

    private int price;

    private String imageUrl;

    private int viewCount;

    private int reportCount = 0;

    private boolean reaction1 = false;
    private boolean reaction2 = false;
    private boolean reaction3 = false;
    private boolean reaction4 = false;
    private boolean reaction5 = false;
    private boolean reaction6 = false;
    private boolean reaction7 = false;
    private boolean reaction8 = false;
    private boolean reaction9 = false;

    /**
     * 왜 @ManyToOne(fetch = FetchType.LAZY) 를 사용하는가?
     * 1. 성능 최적화 (쿼리 최소화)
     * LAZY: 연관된 Member 엔티티를 실제로 접근할 때까지 쿼리를 실행하지 않음.
     * EAGER: Items 엔티티를 조회할 때 즉시 연관된 Member도 같이 조회 (JOIN 쿼리 실행).
     * 예를 들어, 100개의 Items를 불러오면,
     * <p>
     * LAZY일 경우: Member는 실제로 접근할 때만 SELECT 실행.
     * EAGER일 경우: 100개의 Items마다 JOIN이 발생하거나, N+1 문제가 생김.
     * <p>
     * 2. N+1 문제 방지
     *
     * @ManyToOne(fetch = FetchType.EAGER)는 다대일 관계에서 기본값인데, 이로 인해 N+1 문제가 발생할 수 있음.
     * <p>
     * 이를 방지하기 위해 실무에서는 반드시 fetch = FetchType.LAZY를 명시하는 것이 권장
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)  // 외래 키 이름 일치
    private Member member;

    @OneToMany(mappedBy = "items", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Likes> likes = new ArrayList<>();

    @OneToMany(mappedBy = "items", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Replies> replies = new ArrayList<>();

    @Builder
    private Items(String name, String description, int price, String imageUrl, Member member
            , boolean reaction1, boolean reaction2, boolean reaction3, boolean reaction4, boolean reaction5,
                  boolean reaction6, boolean reaction7, boolean reaction8, boolean reaction9
    ) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.imageUrl = imageUrl;
        this.member = member;
        this.viewCount = 0;
        this.reaction1 = reaction1;
        this.reaction2 = reaction2;
        this.reaction3 = reaction3;
        this.reaction4 = reaction4;
        this.reaction5 = reaction5;
        this.reaction6 = reaction6;
        this.reaction7 = reaction7;
        this.reaction8 = reaction8;
        this.reaction9 = reaction9;
        this.reportCount = 0;
    }

    public void updateItem(String name, String description, int price, String imageUrl,
                           boolean reaction1, boolean reaction2, boolean reaction3,
                           boolean reaction4, boolean reaction5, boolean reaction6,
                           boolean reaction7, boolean reaction8, boolean reaction9
    ) {

        this.name = name;
        this.description = description;
        this.price = price;
        this.imageUrl = imageUrl;
        this.reaction1 = reaction1;
        this.reaction2 = reaction2;
        this.reaction3 = reaction3;
        this.reaction4 = reaction4;
        this.reaction5 = reaction5;
        this.reaction6 = reaction6;
        this.reaction7 = reaction7;
        this.reaction8 = reaction8;
        this.reaction9 = reaction9;
    }

    public void updateViewCount() {
        this.viewCount++;
    }

    public void updateReportCount() {
        this.reportCount++;
    }
}
