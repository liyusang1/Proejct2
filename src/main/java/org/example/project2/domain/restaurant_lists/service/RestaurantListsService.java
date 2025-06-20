package org.example.project2.domain.restaurant_lists.service;

import lombok.RequiredArgsConstructor;
import org.example.project2.domain.member.entity.Member;
import org.example.project2.domain.member.exception.UserNotFoundException;
import org.example.project2.domain.restaurant_lists.dto.request.CreateListRequestDto;
import org.example.project2.domain.restaurant_lists.dto.request.PutListRequestDto;
import org.example.project2.domain.restaurant_lists.dto.response.ListResponseDto;
import org.example.project2.domain.restaurant_lists.entity.RestaurantLists;
import org.example.project2.domain.restaurant_lists.exception.RestaurantListNotFoundException;
import org.example.project2.domain.restaurant_lists.exception.UnloginException;
import org.example.project2.domain.restaurant_lists.exception.UpdateAccessDenied;
import org.example.project2.domain.restaurant_lists.repository.RestaurantListsRepository;
import org.example.project2.domain.restaurants.dto.response.RestaurantResponseDto;
import org.example.project2.domain.restaurants.entity.Restaurants;
import org.example.project2.domain.restaurants.exception.RestaurantNotFoundException;
import org.example.project2.domain.restaurants.repository.RestaurantsRepository;
import org.example.project2.domain.restaurants.service.RestaurantsService;
import org.example.project2.global.springsecurity.PrincipalDetails;
import org.example.project2.global.util.ResponseDTO;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class RestaurantListsService {

    private final RestaurantListsRepository restaurantListsRepository;
    private final RestaurantsRepository restaurantsRepository;

    // 개인 리스트 찾기
    public ResponseDTO<List<ListResponseDto>> getRestaurantListsByMemberId(PrincipalDetails principalDetails) {
        Long memberId = principalDetails.getMember().getId();
        if(memberId == null) {
            throw new UnloginException();
        }
        List<RestaurantLists> restaurantLists = restaurantListsRepository.findAllByMember_Id(memberId);

        List<ListResponseDto> listResponseDtos = new ArrayList<>();

        for (RestaurantLists restaurantList : restaurantLists) {
            List<RestaurantResponseDto> responseDtoList = findAllByRestaurantListId(restaurantList.getId());
            listResponseDtos.add(ListResponseDto.from(restaurantList, responseDtoList));
        }

        return ResponseDTO.okWithData(listResponseDtos);
    }

    // 공개된 리스트 모두 가져오기
    public ResponseDTO<List<ListResponseDto>> getRestaurantListsByIsPublicIsTrue() {
        List<RestaurantLists> restaurantLists = restaurantListsRepository.findAllByIsPublicIsTrue();

        List<ListResponseDto> listResponseDtos = new ArrayList<>();
        for (RestaurantLists restaurantList : restaurantLists) {
            List<RestaurantResponseDto> responseDtoList = findAllByRestaurantListId(restaurantList.getId());
            listResponseDtos.add(ListResponseDto.from(restaurantList,responseDtoList));
        }

        return ResponseDTO.okWithData(listResponseDtos);
    }

    // 수정하거나 삭제 시 하나의 리스트 번호로 찾기
    public ResponseDTO<List<ListResponseDto>> getRestaurantListById(PrincipalDetails principalDetails,Long id) {
        if (principalDetails == null) {
            throw new UserNotFoundException();
        }
       RestaurantLists restaurantLists = restaurantListsRepository.findRestaurantListsById(id).orElseThrow(
               RestaurantListNotFoundException::new
       );
       if(!restaurantLists.getMember().getId().equals(principalDetails.getMember().getId())) {
           throw new UpdateAccessDenied();
       }

       List<ListResponseDto> listResponseDtos = new ArrayList<>();
       List<RestaurantResponseDto> responseDtoList = findAllByRestaurantListId(restaurantLists.getId());
       listResponseDtos.add(ListResponseDto.from(restaurantLists,responseDtoList));

       return ResponseDTO.okWithData(listResponseDtos);
    }

    // 리스트 생성
    public void createRestaurantList(PrincipalDetails principalDetails, CreateListRequestDto request) {

        if (principalDetails == null) {
            throw new UserNotFoundException();
        }

        Member member = principalDetails.getMember();

        RestaurantLists savedRestaurantLists = request.toEntity(member);
        restaurantListsRepository.save(savedRestaurantLists);
    }

    // 리스트 수정
    public void updateRestaurantList(PrincipalDetails principalDetails, PutListRequestDto request) {
        if (principalDetails == null) {
            throw new UserNotFoundException();
        }

        Member member = principalDetails.getMember();

        RestaurantLists updateRestaurantlist = restaurantListsRepository.findRestaurantListsById(request.id()).orElseThrow(
                RestaurantListNotFoundException::new
        );

        if(!updateRestaurantlist.getMember().getId().equals(member.getId())) {
            throw new UpdateAccessDenied();
        }

        updateRestaurantlist.updateLists(request.title(),
                request.description(), request.isPublic());

    }

    // 리스트 삭제
    public void deleteRestaurantList(PrincipalDetails principalDetails, Long id) {
        if (principalDetails == null) {
            throw new UserNotFoundException();
        }
        RestaurantLists restaurantLists = restaurantListsRepository.findRestaurantListsById(id).orElseThrow(
                RestaurantListNotFoundException::new
        );

        if(!restaurantLists.getMember().getId().equals(principalDetails.getMember().getId())) {
            throw new UpdateAccessDenied();
        }

        restaurantListsRepository.delete(restaurantLists);
    }

    public List<RestaurantResponseDto> findAllByRestaurantListId(Long id) {
        List<Restaurants> restaurants = restaurantsRepository.findAllByRestaurantLists_Id(id);

        List<RestaurantResponseDto> restaurantResponseDto = new ArrayList<>();
        for (Restaurants restaurant : restaurants) {
            restaurantResponseDto.add(RestaurantResponseDto.from(restaurant));
        }
        return restaurantResponseDto;
    }

}
