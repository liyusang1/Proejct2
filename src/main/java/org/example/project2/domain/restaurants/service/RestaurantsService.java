package org.example.project2.domain.restaurants.service;

import lombok.RequiredArgsConstructor;
import org.example.project2.domain.member.exception.UserNotFoundException;
import org.example.project2.domain.restaurantDetails.repository.RestaurantDetailsRepository;
import org.example.project2.domain.restaurant_lists.entity.RestaurantLists;
import org.example.project2.domain.restaurant_lists.exception.RestaurantListNotFoundException;
import org.example.project2.domain.restaurant_lists.exception.UnloginException;
import org.example.project2.domain.restaurant_lists.repository.RestaurantListsRepository;
import org.example.project2.domain.restaurants.dto.request.CreateRestaurantRequestDto;
import org.example.project2.domain.restaurants.dto.request.PostRestaurantDetailRequestDto;
import org.example.project2.domain.restaurants.dto.request.PutRestaurantRequestDto;
import org.example.project2.domain.restaurants.dto.response.RestaurantResponseDto;
import org.example.project2.domain.restaurants.entity.Restaurants;
import org.example.project2.domain.restaurants.exception.AccessDenied;
import org.example.project2.domain.restaurants.exception.RestaurantListAccessDenied;
import org.example.project2.domain.restaurants.exception.RestaurantNotFoundException;
import org.example.project2.domain.restaurants.repository.RestaurantsRepository;
import org.example.project2.global.springsecurity.PrincipalDetails;
import org.example.project2.global.util.ResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class RestaurantsService {
    // 로그인 필요한 함수는 PrincipalDetails 를 통해 유저 정보 가져오기
    // Repository에 JpaRepository를 확장하여 DAO 역할을 하게 한다.
    private final RestaurantsRepository restaurantsRepository;
    private final RestaurantListsRepository listsRepository;
    private final RestaurantDetailsRepository restaurantDetailsRepository;

    public Restaurants findRestaurantById(Long id) {
        return restaurantsRepository.findById(id).orElse(null);
    }

    public ResponseDTO<Page<RestaurantResponseDto>> findAllByRestaurantListId(Long listId, PrincipalDetails principalDetails, int page, int size) {

        Page<Restaurants> restaurants;

        //TODO 올바른 분기처리
        RestaurantLists checkRestaurant = listsRepository.findRestaurantListsById(listId).orElseThrow(
                RestaurantListNotFoundException::new
        );

        Pageable pageable = PageRequest.of(page, size); // 페이지 설정

        if(principalDetails == null ) {
            if(checkRestaurant.isPublic()) {
                restaurants = restaurantsRepository.findAllByRestaurantLists_Id(listId, pageable);
            }else{
                throw new RestaurantListAccessDenied();
            }
        }else{
            if(checkRestaurant.isPublic()) {
                restaurants = restaurantsRepository.findAllByRestaurantLists_Id(listId, pageable);
            }else {
                if (checkRestaurant.getMember().getId().equals(principalDetails.getMember().getId())){
                    restaurants = restaurantsRepository.findAllByRestaurantLists_Id(listId, pageable);
                }else {
                    throw new RestaurantListAccessDenied();
                }
            }
        }

        // RestaurantResponseDto로 변환
        Page<RestaurantResponseDto> restaurantResponseDtoPage = restaurants.map(RestaurantResponseDto::from);

        return ResponseDTO.okWithData(restaurantResponseDtoPage);
    }

    public void postRestaurant(PrincipalDetails principalDetails, CreateRestaurantRequestDto request, Long listId) {
        if (principalDetails == null) {
            throw new UnloginException();
        }
        RestaurantLists restaurantLists = listsRepository.findRestaurantListsById(listId).orElseThrow(
                RestaurantListNotFoundException::new
        );

        Restaurants savedRestaurant = request.toEntity(restaurantLists);
        if(!restaurantLists.getMember().getId().equals(principalDetails.getMember().getId())) {
            throw new AccessDenied();
        }

        restaurantsRepository.save(savedRestaurant);

        for(PostRestaurantDetailRequestDto restaurantDetailRequestDto : request.detailList()){
            restaurantDetailsRepository.save(restaurantDetailRequestDto.toEntity(savedRestaurant));
        }
    }

    public void delete(PrincipalDetails principalDetails, Long restaurantId) {
        if (principalDetails == null) {
            throw new UnloginException();
        }
        Restaurants restaurants = findRestaurantById(restaurantId);
        if (restaurants == null) {
            throw new RestaurantNotFoundException();
        }
        if(!restaurants.getRestaurantLists().getMember().getId().equals(principalDetails.getMember().getId())) {
            throw new AccessDenied();
        }
        restaurantsRepository.delete(restaurants);

    }

    public void putRestaurant(PrincipalDetails principalDetails, PutRestaurantRequestDto request, Long restaurantId) {
        if (principalDetails == null) {
            throw new UnloginException();
        }
        Restaurants restaurants = findRestaurantById(restaurantId);
        if(restaurants == null) {
            throw new RestaurantNotFoundException();
        }else if(!restaurants.getRestaurantLists().getMember().getId().equals(principalDetails.getMember().getId())) {
            throw new AccessDenied();
        }

        restaurants.updateRestaurant(request.name(),
                                    request.address(),
                                    request.description(),
                                    request.imageUrl());
    }
}
