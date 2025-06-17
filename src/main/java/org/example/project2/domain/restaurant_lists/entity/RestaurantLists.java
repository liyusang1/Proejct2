package org.example.project2.domain.restaurant_lists.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.project2.domain.member.entity.Member;
import org.example.project2.domain.restaurants.entity.Restaurants;
import org.example.project2.global.entity.BaseTimeEntity;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class RestaurantLists extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private boolean isPublic;

    @OneToMany(mappedBy = "restaurantLists", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Restaurants> restaurants = new ArrayList<>();

    @Builder
    private RestaurantLists(Member member, String title, String description, boolean isPublic) {
        this.member = member;
        this.title = title;
        this.description = description;
        this.isPublic = isPublic;
    }

    public void updateLists(String title,
                            String description,
                            boolean isPublic) {
        this.title = title;
        this.description = description;
        this.isPublic = isPublic;
    }

}
