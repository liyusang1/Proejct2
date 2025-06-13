package org.example.project2.domain.restaurant_lists.repository;

import org.example.project2.domain.restaurant_lists.dto.response.MyListResponseDto;
import org.example.project2.domain.restaurant_lists.entity.RestaurantLists;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RestaurantListsRepository extends JpaRepository<RestaurantLists, Long> {

    List<MyListResponseDto> findAllByMember_Id(Long memberId);

}
