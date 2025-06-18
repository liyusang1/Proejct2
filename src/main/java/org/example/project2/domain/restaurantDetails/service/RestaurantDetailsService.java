package org.example.project2.domain.restaurantDetails.service;

import lombok.RequiredArgsConstructor;
import org.example.project2.domain.restaurantDetails.dto.request.PutRestaurantDetailsMultiRequestDto;
import org.example.project2.domain.restaurantDetails.dto.request.PutRestaurantDetailsRequestDto;
import org.example.project2.domain.restaurantDetails.dto.response.RestaurantDetailsResponseDto;
import org.example.project2.domain.restaurantDetails.entity.RestaurantDetails;
import org.example.project2.domain.restaurantDetails.exception.PrivateRestaurantException;
import org.example.project2.domain.restaurantDetails.repository.RestaurantDetailsRepository;
import org.example.project2.domain.restaurants.exception.AccessDenied;
import org.example.project2.domain.restaurants.exception.RestaurantNotFoundException;
import org.example.project2.global.springsecurity.PrincipalDetails;
import org.example.project2.global.util.ResponseDTO;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class RestaurantDetailsService {

    private final RestaurantDetailsRepository restaurantDetailsRepository;

    public ResponseDTO<List<RestaurantDetailsResponseDto>> getRestaurantDetailsById(Long restaurantId, PrincipalDetails principalDetails) {
        List<RestaurantDetails> details = restaurantDetailsRepository.findAllByRestaurants_Id(restaurantId);

        if (details.isEmpty()) {
            throw new RestaurantNotFoundException();
        }

        boolean isPublic = details.get(0).getRestaurants().getRestaurantLists().isPublic();
        Long ownerId = details.get(0).getRestaurants().getRestaurantLists().getMember().getId();

        if (!isPublic) {
            if (principalDetails == null || !ownerId.equals(principalDetails.getMember().getId())) {
                throw new PrivateRestaurantException();
            }
        }

        List<RestaurantDetailsResponseDto> dtos = details.stream()
                .map(RestaurantDetailsResponseDto::from)
                .toList();
        return ResponseDTO.okWithData(dtos);
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
