package org.example.project2.domain.restaurantDetails.repository;

import org.example.project2.domain.restaurantDetails.entity.RestaurantDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantsDetailsRepository extends JpaRepository<RestaurantDetails, Long> {

}
