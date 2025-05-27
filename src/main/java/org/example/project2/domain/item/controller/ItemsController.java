package org.example.project2.domain.item.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.project2.domain.item.dto.request.PostItemRequestDto;
import org.example.project2.domain.item.dto.response.ItemDetailResponseDto;
import org.example.project2.domain.item.dto.response.ItemResponseDto;
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

@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemsController {

    private final ItemService itemService;

    @GetMapping("")
    public ResponseEntity<ResponseDTO<Page<ItemResponseDto>>> getAllItemList(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "9") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String direction
    ) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(direction), sortBy));
        ResponseDTO<Page<ItemResponseDto>>
                response = itemService.getAllItemList(pageable);

        return ResponseEntity
                .status(response.getCode())
                .body(response);
    }

    @GetMapping("/{itemId}")
    public ResponseEntity<ResponseDTO<ItemDetailResponseDto>> getItemDetail(
            @PathVariable long itemId) {

        ResponseDTO<ItemDetailResponseDto> response = itemService.getItemDetail(
                itemId);
        return ResponseEntity.status(response.getCode()).body(response);
    }

    @PostMapping("")
    public ResponseEntity<ResponseDTO<Void>> postItem(
            @AuthenticationPrincipal PrincipalDetails principalDetails,
            @Valid @RequestBody PostItemRequestDto postItemRequestDto) {

        ResponseDTO<Void> response = itemService.postItem(principalDetails, postItemRequestDto);
        return ResponseEntity.status(response.getCode()).body(response);
    }
}