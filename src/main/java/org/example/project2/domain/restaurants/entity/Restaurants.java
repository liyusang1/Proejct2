package org.example.project2.domain.restaurants.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.project2.domain.restaurantDetails.entity.RestaurantDetails;
import org.example.project2.domain.restaurant_lists.entity.RestaurantLists;
import org.example.project2.global.entity.BaseTimeEntity;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Restaurants extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "restaurant_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_list_id", nullable = false)
    private RestaurantLists restaurantLists;

    @Column(name = "restaurant_name", nullable = false)
    private String name;

    @Column(name = "address")
    private String address;

    @Column(name = "description", columnDefinition = "text")
    private String description;

    @Column(name = "image_url")
    private String imageUrl;

    @OneToMany(mappedBy = "restaurants", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RestaurantDetails> restaurantDetails = new ArrayList<>();

    @Builder
    private Restaurants(RestaurantLists restaurantList, String name, String address, String description, String imageUrl) {
        this.restaurantLists = restaurantList;
        this.name = name;
        this.address = address;
        this.description = description;
        this.imageUrl = imageUrl;
    }

    public void updateRestaurant(String name,
                                 String address,
                                 String description,
                                 String imageUrl) {
        this.name = name;
        this.address = address;
        this.description = description;
        this.imageUrl = imageUrl;
    }
}
