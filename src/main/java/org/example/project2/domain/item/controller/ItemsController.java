package org.example.project2.domain.item.controller;

import lombok.RequiredArgsConstructor;
import org.example.project2.domain.item.dto.response.ItemResponseDto;
import org.example.project2.domain.item.service.ItemService;
import org.example.project2.global.util.ResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemsController {

    private final ItemService itemService;

    @GetMapping("")
    public ResponseEntity<ResponseDTO<List<ItemResponseDto>>> getAllItemList() {

        ResponseDTO<List<ItemResponseDto>>
                response = itemService.getAllItemList();

        return ResponseEntity
                .status(response.getCode())
                .body(response);
    }
}