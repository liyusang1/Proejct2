package org.example.project2.domain.restaurant_lists.repository;

import org.example.project2.domain.restaurant_lists.entity.RestaurantLists;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RestaurantListsRepository extends JpaRepository<RestaurantLists, Long> {

    //Optional<RestaurantLists> findRestaurantListsById(Long id);
    Page<RestaurantLists> findAllByMember_Id(Long memberId, Pageable pageable);
    Page<RestaurantLists> findAllByIsPublicIsTrue(Pageable pageable);

    Optional<RestaurantLists> findRestaurantListsById(Long id);
}
