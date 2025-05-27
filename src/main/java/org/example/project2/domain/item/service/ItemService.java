package org.example.project2.domain.item.service;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.project2.domain.item.dto.request.PostItemRequestDto;
import org.example.project2.domain.item.dto.response.ItemDetailResponseDto;
import org.example.project2.domain.item.dto.response.ItemResponseDto;
import org.example.project2.domain.item.entity.Items;
import org.example.project2.domain.item.exception.ItemIdIsInvalidException;
import org.example.project2.domain.item.repository.ItemRepository;
import org.example.project2.global.springsecurity.PrincipalDetails;
import org.example.project2.global.util.ResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    public ResponseDTO<Page<ItemResponseDto>> getAllItemList(Pageable pageable) {
        Page<Items> items = itemRepository.findAll(pageable);
        return ResponseDTO.okWithData(
                items.map(ItemResponseDto::fromEntity));
    }

    public ResponseDTO<ItemDetailResponseDto> getItemDetail(long itemId) {

        Items item = itemRepository.findById(itemId)
                .orElseThrow(ItemIdIsInvalidException::new);

        ItemDetailResponseDto itemDetailResponseDto = ItemDetailResponseDto.fromEntity(item);
        return ResponseDTO.okWithData(itemDetailResponseDto);
    }

    public ResponseDTO<Void> postItem(PrincipalDetails principalDetails
            , @Valid PostItemRequestDto postItemRequestDto) {

        itemRepository.save(postItemRequestDto.toEntity());
        return ResponseDTO.ok();
    }
}

