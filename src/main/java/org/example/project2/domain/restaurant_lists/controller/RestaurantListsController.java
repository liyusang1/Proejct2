package org.example.project2.domain.restaurant_lists.controller;

import lombok.RequiredArgsConstructor;
import org.example.project2.domain.restaurant_lists.dto.request.MyListRequestDto;
import org.example.project2.domain.restaurant_lists.dto.response.MyListResponseDto;
import org.example.project2.domain.restaurant_lists.service.RestaurantListsService;
import org.example.project2.global.springsecurity.PrincipalDetails;
import org.example.project2.global.util.ResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/restaurants")
@RequiredArgsConstructor
public class RestaurantListsController {

    private final RestaurantListsService restaurantListsService;

    @GetMapping("/mylist")
    public ResponseEntity<ResponseDTO<MyListResponseDto>> getMyRestaurantLists(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                                                               MyListRequestDto myListRequestDTO) {

//        ResponseDTO<MyListResponseDto> response = restaurantListsService.getMyRestaurantLists(myListRequestDTO);
//        return ResponseEntity
//                .status(response.getCode())
//                .body(response);
        return null;
    }

    @RequestMapping(name = "/otherlist")
    public ResponseEntity<ResponseDTO<Void>> getOtherRestaurantLists(){

        return null;
    }
}
