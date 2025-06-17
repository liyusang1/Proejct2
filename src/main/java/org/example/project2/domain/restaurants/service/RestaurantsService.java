package org.example.project2.domain.restaurants.service;

import lombok.RequiredArgsConstructor;
import org.example.project2.domain.member.exception.UserNotFoundException;
import org.example.project2.domain.restaurantDetails.repository.RestaurantsDetailsRepository;
import org.example.project2.domain.restaurant_lists.entity.RestaurantLists;
import org.example.project2.domain.restaurant_lists.exception.RestaurantListNotFoundException;
import org.example.project2.domain.restaurant_lists.repository.RestaurantListsRepository;
import org.example.project2.domain.restaurants.dto.request.CreateRestaurantRequestDto;
import org.example.project2.domain.restaurants.dto.request.PostRestaurantDetailRequestDto;
import org.example.project2.domain.restaurants.dto.request.PutRestaurantRequestDto;
import org.example.project2.domain.restaurants.dto.response.RestaurantResponseDto;
import org.example.project2.domain.restaurants.entity.Restaurants;
import org.example.project2.domain.restaurants.exception.AccessDenied;
import org.example.project2.domain.restaurants.exception.RestaurantNotFoundException;
import org.example.project2.domain.restaurants.repository.RestaurantsRepository;
import org.example.project2.global.springsecurity.PrincipalDetails;
import org.example.project2.global.util.ResponseDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class RestaurantsService {
    // 로그인 필요한 함수는 PrincipalDetails 를 통해 유저 정보 가져오기
    // Repository에 JpaRepository를 확장하여 DAO 역할을 하게 한다.
    private final RestaurantsRepository restaurantsRepository;
    private final RestaurantListsRepository listsRepository;
    private final RestaurantsDetailsRepository restaurantsDetailsRepository;

    public Restaurants findRestaurantById(Long id) {
        return restaurantsRepository.findById(id).orElse(null);
    }

    public ResponseDTO<List<RestaurantResponseDto>> findAllByRestaurantListId(Long id) {
        List<Restaurants> restaurants = restaurantsRepository.findAllByRestaurantLists_Id(id);
        // 리스트가 비었거나 리스트 자체가 비공개 상태일 경우
        if (restaurants.isEmpty() || !restaurants.get(0).getRestaurantLists().isPublic()) {
            throw new RestaurantNotFoundException();
        }

        List<RestaurantResponseDto> restaurantResponseDto = new ArrayList<>();
        for (Restaurants restaurant : restaurants) {
            restaurantResponseDto.add(RestaurantResponseDto.from(restaurant));
        }
        return ResponseDTO.okWithData(restaurantResponseDto);
    }

    public void postRestaurant(CreateRestaurantRequestDto request, Long listId) {
        RestaurantLists restaurantLists = listsRepository.findRestaurantListsById(listId);
        if(restaurantLists == null) {
            throw new RestaurantListNotFoundException();
        }

        Restaurants savedRestaurant = request.toEntity(restaurantLists);
        if(!restaurantLists.getId().equals(savedRestaurant.getRestaurantLists().getId())) {
            throw new RestaurantNotFoundException();
        }

        restaurantsRepository.save(savedRestaurant);

        for(PostRestaurantDetailRequestDto restaurantDetailRequestDto : request.detailList()){
            restaurantsDetailsRepository.save(restaurantDetailRequestDto.toEntity(savedRestaurant));
        }
    }

    public void delete(PrincipalDetails principalDetails, Long restaurantId) {
        if (principalDetails == null) {
            throw new UserNotFoundException();
        }
        Restaurants restaurants = findRestaurantById(restaurantId);

        if(!restaurants.getRestaurantLists().getMember().getId().equals(principalDetails.getMember().getId())) {
            throw new AccessDenied();
        }
        restaurantsRepository.delete(restaurants);

    }

    public void putRestaurant(PrincipalDetails principalDetails, PutRestaurantRequestDto request, Long restaurantNum) {
        if (principalDetails == null) {
            throw new UserNotFoundException();
        }
        Restaurants restaurants = findRestaurantById(restaurantNum);
        if(!restaurants.getRestaurantLists().getMember().getId().equals(principalDetails.getMember().getId())) {
            throw new AccessDenied();
        }

        restaurants.updateRestaurant(request.name(),
                                    request.address(),
                                    request.description(),
                                    request.imageUrl());

    }

}
