package org.example.project2.domain.recipes.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.project2.domain.member.entity.Member;
import org.example.project2.global.entity.BaseTimeEntity;

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

    //JPA가 자동으로 테이블 관리 - DB에 나타나지 않음
    @ElementCollection
    @CollectionTable(name = "recipe_ingredients", joinColumns = @JoinColumn(name = "recipe_id"))
    @Column(name = "ingredient")
    private List<String> ingredients; //재료

    //JPA가 자동으로 테이블 관리 - DB에 나타나지 않음
    @ElementCollection
    @CollectionTable(name = "recipe_steps", joinColumns = @JoinColumn(name = "recipe_id"))
    @Column(name = "step")
    private List<String> steps; //조리순서

    private int time; //조리시간

    private String level; //조리난이도

    @ManyToOne(fetch = FetchType.LAZY) //다대일 관계
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Builder
    private Recipes(Member member, String title, String description,
                    List<String> ingredients, String imageUrl, int time, String level, List<String> steps, int viewCount) {
        this.member = member;
        this.title = title;
        this.description = description;
        this.ingredients = ingredients;
        this.imageUrl = imageUrl;
        this.time = time;
        this.level = level;
        this.steps = steps;
        this.viewCount = 0;
    }

    public void update(String title, String description, List<String> ingredients, String imageUrl, int time, String level, List<String> steps) {
        this.title = title;
        this.description = description;
        this.ingredients = ingredients;
        this.imageUrl = imageUrl;
        this.time = time;
        this.level = level;
        this.steps = steps;
    }

    public void updateViewCount() {
        this.viewCount++;
    } //조회수 카운트
}
