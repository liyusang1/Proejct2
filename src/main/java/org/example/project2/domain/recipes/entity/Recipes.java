package org.example.project2.domain.recipes.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.project2.domain.member.entity.Member;
import org.example.project2.global.entity.BaseTimeEntity;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Recipes extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recipe_id")
    private Long id; //레시피 ID

    @Column(nullable = false, length = 100)
    private String title; //레시피 이름

    @Column(columnDefinition = "TEXT")
    private String description; //간단 요리 설명

    private String imageUrl; //레시피 사진

    private int viewCount; //조회수

    @ElementCollection
    @CollectionTable(name = "recipe_ingredients", joinColumns = @JoinColumn(name = "recipe_id"))
    @Column(name = "ingredient")
    private List<String> ingredients = new ArrayList<>(); //재료

    @ElementCollection
    @CollectionTable(name = "recipe_steps", joinColumns = @JoinColumn(name = "recipe_id"))
    @Column(name = "step")
    private List<String> steps = new ArrayList<>(); //조리순서

    private int time; //조리시간

    private String level; //조리난이도

    @ManyToOne(fetch = FetchType.LAZY) //다대일 관계
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Bookmark> bookmarks = new ArrayList<>();

    @Builder
    private Recipes(Member member, String title, String description,
                    List<String> ingredients, String imageUrl, int time, String level, List<String> steps, int viewCount) {
        this.member = member;
        this.title = title;
        this.description = description;
        this.ingredients = ingredients != null ? new ArrayList<>(ingredients) : new ArrayList<>();
        this.imageUrl = imageUrl;
        this.time = time;
        this.level = level;
        this.steps = steps != null ? new ArrayList<>(steps) : new ArrayList<>();
        this.viewCount = 0;
    }

    public void update(String title, String description, List<String> ingredients, String imageUrl, int time, String level, List<String> steps) {
        this.title = title;
        this.description = description;

        // 기존 재료 및 스텝 리스트를 비우고 새 리스트로 업데이트
        this.ingredients.clear();
        if (ingredients != null) {
            this.ingredients.addAll(ingredients);
        }

        this.imageUrl = imageUrl;
        this.time = time;
        this.level = level;

        this.steps.clear();
        if (steps != null) {
            this.steps.addAll(steps);
        }
    }

    public void updateViewCount() {
        this.viewCount++;
    } //조회수 카운트
}