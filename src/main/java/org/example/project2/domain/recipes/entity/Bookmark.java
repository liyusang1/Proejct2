package org.example.project2.domain.recipes.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.project2.domain.member.entity.Member;
import org.example.project2.global.entity.BaseTimeEntity; // ⭐ BaseTimeEntity 상속 확인

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "bookmarks",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"member_id", "recipe_id"}) // ⭐ recipe_id로 변경
        })
public class Bookmark extends BaseTimeEntity { // ⭐ BaseTimeEntity 상속 유지

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bookmark_id")
    private Long id;

    @Column(nullable = false) // ⭐ null 허용하지 않음 명시
    private boolean status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipe_id", nullable = false) // ⭐ recipe_id로 변경
    private Recipes recipe; // ⭐ 필드명을 recipe로 변경

    @Builder
    public Bookmark(Member member, Recipes recipe, boolean status) { // ⭐ status 필드 추가
        this.member = member;
        this.recipe = recipe;
        this.status = status; // 빌더로 생성 시 status를 설정할 수 있도록
    }

    // ⭐ 상태 토글이 아닌 명확한 활성화/비활성화 메서드를 사용하는 것이 좋습니다.
    public void activate() {
        this.status = true;
    }

    public void deactivate() {
        this.status = false;
    }

}