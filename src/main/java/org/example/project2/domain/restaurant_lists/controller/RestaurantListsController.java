package org.example.project2.domain.restaurant_lists.controller;

import lombok.RequiredArgsConstructor;
import org.example.project2.domain.restaurant_lists.dto.request.CreateListRequestDto;
import org.example.project2.domain.restaurant_lists.dto.request.PutListRequestDto;
import org.example.project2.domain.restaurant_lists.dto.response.ListResponseDto;
import org.example.project2.domain.restaurant_lists.service.RestaurantListsService;
import org.example.project2.global.springsecurity.PrincipalDetails;
import org.example.project2.global.util.ResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
GET: 리소스 조회
POST: 요청 데이터 처리, 주로 등록에 사용
PUT: 리소스를 대체(덮어쓰기), 해당 리소스가 없으면 생성
PATCH: 리소스 부분 변경 (PUT이 전체 변경, PATCH는 일부 변경)
DELETE: 리소스 삭제
 */

@Controller
@RequestMapping(value = "/restaurants-list")
@RequiredArgsConstructor
public class RestaurantListsController {

    private final RestaurantListsService restaurantListsService;

    @GetMapping("/my")
    public ResponseEntity<ResponseDTO<List<ListResponseDto>>> getMyRestaurantLists(
            @AuthenticationPrincipal PrincipalDetails principalDetails) {
        Long memberId = principalDetails.getMember().getId();
        ResponseDTO<List<ListResponseDto>>
                response = restaurantListsService.getRestaurantListsByMemberId(memberId);

        return ResponseEntity
                .status(response.getCode())
                .body(response);
    }

    @GetMapping("")
    public ResponseEntity<ResponseDTO<List<ListResponseDto>>> getOtherRestaurantLists(){
        ResponseDTO<List<ListResponseDto>>
                response = restaurantListsService.getRestaurantListsByIsPublicIsTrue();

        return ResponseEntity
                .status(response.getCode())
                .body(response);
    }

    @PostMapping("")
    public ResponseEntity<ResponseDTO<Void>> createRestaurantLists(
            @AuthenticationPrincipal PrincipalDetails principalDetails
            , @RequestBody CreateListRequestDto request) {

        restaurantListsService.createRestaurantList(principalDetails, request);

        return ResponseEntity.ok(ResponseDTO.ok());
    }

    // 수정할 데이터 불러오기
    @GetMapping("/info/{listId}")
    public ResponseEntity<ResponseDTO<List<ListResponseDto>>> getMyRestaurantListOne(
            @AuthenticationPrincipal PrincipalDetails principalDetails
            , @RequestParam Long listId ) {
        ResponseDTO<List<ListResponseDto>>
                response = restaurantListsService.getRestaurantListById(listId);

        return ResponseEntity
                .status(response.getCode())
                .body(response);
    }

    @PutMapping("/info")
    public ResponseEntity<ResponseDTO<Void>> updateRestaurantLists(
            @AuthenticationPrincipal PrincipalDetails principalDetails
            , @RequestBody PutListRequestDto request) {

        restaurantListsService.updateRestaurantList(principalDetails, request);

        return ResponseEntity.ok(ResponseDTO.ok());
    }

    @DeleteMapping("/{listId}")
    public ResponseEntity<ResponseDTO<Void>> deleteRestaurantLists(
            @AuthenticationPrincipal PrincipalDetails principalDetails
            ,@PathVariable Long listId
    ){

        restaurantListsService.deleteRestaurantList(principalDetails, listId);

        return ResponseEntity.ok(ResponseDTO.ok());
    }




}
