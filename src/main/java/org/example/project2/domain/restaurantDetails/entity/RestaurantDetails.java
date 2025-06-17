package org.example.project2.domain.restaurantDetails.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.project2.domain.restaurants.entity.Restaurants;
import org.example.project2.global.entity.BaseTimeEntity;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class RestaurantDetails extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "restaurant_details_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurants restaurants;

    private String name;

    private int price;

    private String imageUrl;

    private String description;

    @Builder
    private RestaurantDetails(
            String name,
            int price,
            String imageUrl,
            String description,
            Restaurants restaurants) {
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.description = description;
        this.restaurants = restaurants;
    }
}
