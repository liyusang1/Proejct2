package org.example.project2.domain.item.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.project2.domain.item.dto.request.PostItemRequestDto;
import org.example.project2.domain.item.dto.response.*;
import org.example.project2.domain.item.service.ItemService;
import org.example.project2.global.springsecurity.PrincipalDetails;
import org.example.project2.global.util.ResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemsController {

    private final ItemService itemService;

    /**
     * 아이템 전체 조회
     */
    @GetMapping("")
    public ResponseEntity<ResponseDTO<Page<ItemResponseDto>>> getAllItemList(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "9") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String direction,
            @RequestParam(required = false) String search,
            @AuthenticationPrincipal PrincipalDetails principalDetails
    ) {
        Long userId = null;
        if (principalDetails != null) {
            userId = principalDetails.getMember().getId();
        }

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(direction), sortBy));
        ResponseDTO<Page<ItemResponseDto>>
                response = itemService.getAllItemList(pageable, userId,search);

        return ResponseEntity
                .status(response.getCode())
                .body(response);
    }

    /**
     * 아이템 id로 상세 조회
     */
    @GetMapping("/{itemId}")
    public ResponseEntity<ResponseDTO<ItemDetailResponseDto>> getItemDetail(
            @PathVariable long itemId) {

        ResponseDTO<ItemDetailResponseDto> response = itemService.getItemDetail(
                itemId);
        return ResponseEntity.status(response.getCode()).body(response);
    }

    /**
     * 아이템 등록
     */
    @PostMapping("")
    public ResponseEntity<ResponseDTO<Void>> postItem(
            @AuthenticationPrincipal PrincipalDetails principalDetails,
            @Valid @RequestBody PostItemRequestDto postItemRequestDto) {

        ResponseDTO<Void> response = itemService.postItem(principalDetails, postItemRequestDto);
        return ResponseEntity.status(response.getCode()).body(response);
    }

    /** 아이템 삭제 */
    @DeleteMapping("/{itemId}")
    public ResponseEntity<ResponseDTO<Void>> deleteItem(
            @AuthenticationPrincipal PrincipalDetails principalDetails,
            @PathVariable long itemId) {

        ResponseDTO<Void> response = itemService.deleteItem(principalDetails, itemId);
        return ResponseEntity.status(response.getCode()).body(response);
    }

    /**
     * 아이템 수정
     */
    @PutMapping("/{itemId}")
    public ResponseEntity<ResponseDTO<Void>> updateItem(
            @AuthenticationPrincipal PrincipalDetails principalDetails,
            @PathVariable long itemId,
            @Valid @RequestBody PostItemRequestDto postItemRequestDto) {

        ResponseDTO<Void> response = itemService.updateItem(principalDetails, itemId, postItemRequestDto);
        return ResponseEntity.status(response.getCode()).body(response);
    }

    /**
     * 유저가 작성한 아이템 조회
     */
    @GetMapping("/member/{memberId}")
    public ResponseEntity<ResponseDTO<List<ItemResponseDto>>> getMembersItemList(
            @PathVariable long memberId
    ) {

        ResponseDTO<List<ItemResponseDto>>
                response = itemService.getMembersItemList(memberId);

        return ResponseEntity
                .status(response.getCode())
                .body(response);
    }

    /**
     * 랭킹조회
     */
    @GetMapping("/rank")
    public ResponseEntity<ResponseDTO<List<ItemRankResponseDto>>> getItemRanking() {

        ResponseDTO<List<ItemRankResponseDto>> response = itemService.getItemRanking();
        return ResponseEntity.status(response.getCode()).body(response);
    }

    /**
     * 인사이트 조회
     */
    @GetMapping("/insight")
    public ResponseEntity<ResponseDTO<InsightResponseDto>> getInsights() {

        ResponseDTO<InsightResponseDto> response = itemService.getInsights();
        return ResponseEntity.status(response.getCode()).body(response);
    }

    /**
     * 인사이트 조회
     */
    @GetMapping("/users-insight")
    public ResponseEntity<ResponseDTO<UsersInsightResponseDto>> getUsersInsights(
            @AuthenticationPrincipal PrincipalDetails principalDetails
    ) {

        ResponseDTO<UsersInsightResponseDto> response = itemService.getUsersInsights(principalDetails);
        return ResponseEntity.status(response.getCode()).body(response);
    }
}