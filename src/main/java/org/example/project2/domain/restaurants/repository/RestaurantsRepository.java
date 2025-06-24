package org.example.project2.domain.restaurants.repository;

import org.example.project2.domain.restaurants.entity.Restaurants;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface RestaurantsRepository extends JpaRepository<Restaurants, Long> {

    Page<Restaurants> findAllByRestaurantLists_Id(Long restaurantListsId, Pageable pageable);
    List<Restaurants> findAllByRestaurantLists_Id(Long restaurantListsId);
}
