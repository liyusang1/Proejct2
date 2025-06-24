package org.example.project2.domain.restaurantDetails.Controller;

import lombok.RequiredArgsConstructor;
import org.example.project2.domain.restaurantDetails.dto.request.PutRestaurantDetailsMultiRequestDto;
import org.example.project2.domain.restaurantDetails.dto.request.PutRestaurantDetailsRequestDto;
import org.example.project2.domain.restaurantDetails.dto.response.RestaurantDetailsResponseDto;
import org.example.project2.domain.restaurantDetails.entity.RestaurantDetails;
import org.example.project2.domain.restaurantDetails.service.RestaurantDetailsService;
import org.example.project2.global.springsecurity.PrincipalDetails;
import org.example.project2.global.util.ResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor        // 자동으로 필드에 맞게 파라미터 요구하는 생성자를 생성하여 생성자 주입으로 인한 의존성 주입
@RequestMapping(value = "restaurants-list/restaurant")
public class RestaurantDetailsController {

    private final RestaurantDetailsService restaurantDetailsService;

    @GetMapping("/{restaurantId}")
    public ResponseEntity<ResponseDTO<List<RestaurantDetailsResponseDto>>>
    getRestaurantDetails(@PathVariable Long restaurantId,
                         @AuthenticationPrincipal PrincipalDetails principalDetails) {

        ResponseDTO<List<RestaurantDetailsResponseDto>> response =
                restaurantDetailsService.getRestaurantDetailsById(restaurantId, principalDetails);

        return ResponseEntity
                .status(response.getCode())
                .body(response);
    }

    // 수정할때 기존 정보 보내기 위한 요청
    @GetMapping("/info/{restaurantId}")
    public ResponseEntity<ResponseDTO<List<RestaurantDetailsResponseDto>>>
    getRestaurantDetailsInfo(@PathVariable Long restaurantId, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        ResponseDTO<List<RestaurantDetailsResponseDto>> response =
                restaurantDetailsService.getRestaurantDetailsById(restaurantId, principalDetails);

        return ResponseEntity
                .status(response.getCode())
                .body(response);
    }

    @PutMapping("/info")
    public ResponseEntity<ResponseDTO<Void>> updateRestaurantDetails(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                                                     @RequestBody PutRestaurantDetailsMultiRequestDto putRestaurantDetailsRequestDto) {
        restaurantDetailsService.putRestaurantDetails(principalDetails,putRestaurantDetailsRequestDto);
        return ResponseEntity.ok(ResponseDTO.ok());
    }

}
