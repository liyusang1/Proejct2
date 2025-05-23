package org.example.project2.domain.item.service;

import lombok.RequiredArgsConstructor;
import org.example.project2.domain.item.dto.response.ItemResponseDto;
import org.example.project2.domain.item.repository.ItemRepository;
import org.example.project2.global.util.ResponseDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;


    public ResponseDTO<List<ItemResponseDto>> getAllItemList() {
        List<ItemResponseDto> itemDtos = new ArrayList<>();

        itemRepository.findAll().forEach(item -> {
            itemDtos.add(ItemResponseDto.fromEntity(item));
        });

        return ResponseDTO.okWithData(itemDtos);
    }

}

