package org.example.project2.domain.restaurants.repository;

import org.example.project2.domain.restaurants.entity.Restaurants;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface RestaurantsRepository extends JpaRepository<Restaurants, Long> {


}
