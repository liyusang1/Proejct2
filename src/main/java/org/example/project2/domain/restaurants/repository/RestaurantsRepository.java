package org.example.project2.domain.restaurants.repository;

import org.example.project2.domain.restaurants.entity.Restaurants;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RestaurantsRepository extends JpaRepository<Restaurants, Long> {

    Page<Restaurants> findAllByRestaurantLists_Id(Long restaurantListsId, Pageable pageable);

    List<Restaurants> findAllByRestaurantLists_Id(Long restaurantListsId);

    int countAllBy();

    @Query("SELECT COUNT(r) FROM Restaurants r JOIN r.restaurantLists rl WHERE rl.member.id = :memberId")
    int countAllByRestaurantList_Member_Id(@org.springframework.data.repository.query.Param("memberId") Long memberId);
}
