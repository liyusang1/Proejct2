package org.example.project2.domain.restaurantDetails.repository;

import org.example.project2.domain.restaurantDetails.entity.RestaurantDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RestaurantDetailsRepository extends JpaRepository<RestaurantDetails, Long> {

    List<RestaurantDetails> findAllByRestaurants_Id(Long restaurantsId);
}
