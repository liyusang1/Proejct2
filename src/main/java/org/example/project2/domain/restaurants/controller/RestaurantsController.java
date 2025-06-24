package org.example.project2.domain.restaurants.controller;

import lombok.RequiredArgsConstructor;
import org.example.project2.domain.restaurants.dto.request.CreateRestaurantRequestDto;
import org.example.project2.domain.restaurants.dto.request.PutRestaurantRequestDto;
import org.example.project2.domain.restaurants.dto.response.RestaurantResponseDto;
import org.example.project2.domain.restaurants.service.RestaurantsService;
import org.example.project2.global.springsecurity.PrincipalDetails;
import org.example.project2.global.util.ResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor        // 자동으로 필드에 맞게 파라미터 요구하는 생성자를 생성하여 생성자 주입으로 인한 의존성 주입
@RequestMapping(value = "restaurants-list")
public class RestaurantsController {

    private final RestaurantsService restaurantsService;

    @GetMapping("/{listId}/restaurants")
    public ResponseEntity<ResponseDTO<Page<RestaurantResponseDto>>> getListInRestaurantByListId(
            @PathVariable long listId,
             @RequestParam(defaultValue = "0") int page,
             @RequestParam(defaultValue = "6") int size,
             @AuthenticationPrincipal PrincipalDetails principalDetails
            ) {
        ResponseDTO<Page<RestaurantResponseDto>>
                response = restaurantsService.findAllByRestaurantListId(listId,principalDetails, page, size);

        return ResponseEntity
                .status(response.getCode())
                .body(response);
    }

    @PostMapping("/{listId}/restaurants")
    public ResponseEntity<ResponseDTO<Void>> createRestaurant(@RequestBody CreateRestaurantRequestDto request,
                                                              @AuthenticationPrincipal PrincipalDetails principalDetails,
                                                              @PathVariable long listId) {

        restaurantsService.postRestaurant(principalDetails, request, listId);

        return ResponseEntity.ok(ResponseDTO.ok());
    }

    @DeleteMapping("/restaurant/{restaurantId}")
    public ResponseEntity<ResponseDTO<Void>> deleteRestaurant(
            @AuthenticationPrincipal PrincipalDetails principalDetails,
            @PathVariable long restaurantId) {

        restaurantsService.delete(principalDetails, restaurantId);

        return ResponseEntity.ok(ResponseDTO.ok());
    }

    @PutMapping("/restaurant/{restaurantId}")
    public ResponseEntity<ResponseDTO<Void>> updateRestaurant(@RequestBody PutRestaurantRequestDto request,
                                                              @PathVariable long restaurantId,
                                                              @AuthenticationPrincipal PrincipalDetails principalDetails) {
        restaurantsService.putRestaurant(principalDetails, request, restaurantId);
        return ResponseEntity.ok(ResponseDTO.ok());
    }


}
