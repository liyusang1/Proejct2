package org.example.project2.domain.restaurantDetails.service;

import lombok.RequiredArgsConstructor;
import org.example.project2.domain.restaurantDetails.dto.request.PutRestaurantDetailsMultiRequestDto;
import org.example.project2.domain.restaurantDetails.dto.request.PutRestaurantDetailsRequestDto;
import org.example.project2.domain.restaurantDetails.dto.response.RestaurantDetailsResponseDto;
import org.example.project2.domain.restaurantDetails.entity.RestaurantDetails;
import org.example.project2.domain.restaurantDetails.repository.RestaurantDetailsRepository;
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
public class RestaurantDetailsService {

    private final RestaurantDetailsRepository restaurantDetailsRepository;
    private final RestaurantsRepository restaurantsRepository;

    // TODO 올바른 분기처리
    public ResponseDTO<List<RestaurantDetailsResponseDto>> getRestaurantDetailsById(Long restaurantId, PrincipalDetails principalDetails) {

        Restaurants checkRestaurant = restaurantsRepository.findById(restaurantId).orElseThrow(
                RestaurantNotFoundException::new
        );

        boolean isPublic = checkRestaurant.getRestaurantLists().isPublic();
        boolean isOwner = principalDetails != null &&
                checkRestaurant.getRestaurantLists().getMember().getId().equals(principalDetails.getMember().getId());

        if (!isPublic && !isOwner) {
            throw new RestaurantNotFoundException();
        }

        List<RestaurantDetails> details = restaurantDetailsRepository.findAllByRestaurants_Id(restaurantId);


        List<RestaurantDetailsResponseDto> restaurantDetailsResponseDto = new ArrayList<>();
        for (RestaurantDetails restaurant : details) {
            List<Restaurants> restaurantResponseDto = restaurantsRepository.findAllByRestaurantLists_Id(restaurantId);
            restaurantDetailsResponseDto.add(RestaurantDetailsResponseDto.from(restaurant));
        }

        return ResponseDTO.okWithData(restaurantDetailsResponseDto);
    }

    public void putRestaurantDetails(PrincipalDetails principalDetails,
                                     PutRestaurantDetailsMultiRequestDto putRestaurantDetailsMultiRequestDto) {
        if (principalDetails == null) {
            throw new AccessDenied();
        }
        // 수정해야 할 값들이 여러 개 단위로 들어오기 때문에 이런 방식을 사용
        for (PutRestaurantDetailsRequestDto requestDto : putRestaurantDetailsMultiRequestDto.list()){
            RestaurantDetails details = restaurantDetailsRepository.findById(requestDto.id()).orElseThrow(
                    RestaurantNotFoundException::new
            );
            if(details == null) {
                throw new RestaurantNotFoundException();
            }else if(!details.getRestaurants().getRestaurantLists().getMember().getId().equals(principalDetails.getMember().getId())) {
                throw new AccessDenied();
            }

            details.updateDetails(requestDto.name(),
                    requestDto.price(),
                    requestDto.imageUrl(),
                    requestDto.description());
        }
    }


}
