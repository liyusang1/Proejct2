package org.example.project2.domain.restaurant_lists.service;

import lombok.RequiredArgsConstructor;
import org.example.project2.domain.restaurant_lists.dto.request.MyListRequestDto;
import org.example.project2.domain.restaurant_lists.dto.response.MyListResponseDto;
import org.example.project2.domain.restaurant_lists.repository.RestaurantListsRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class RestaurantListsService {

    private final RestaurantListsRepository restaurantListsRepository;

    public MyListResponseDto getMyRestaurantLists(MyListRequestDto myListRequestDTO) {
        Long id = myListRequestDTO.member().getId();
//        List<MyListResponseDto> myListResponseDtos = restaurantListsRepository.findAllById(id);

        return null;
    }
}
