package org.example.project2.domain.item.service;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.project2.domain.item.dto.request.PostItemRequestDto;
import org.example.project2.domain.item.dto.response.ItemDetailResponseDto;
import org.example.project2.domain.item.dto.response.ItemRankResponseDto;
import org.example.project2.domain.item.dto.response.ItemResponseDto;
import org.example.project2.domain.item.entity.Items;
import org.example.project2.domain.item.exception.ItemIdIsInvalidException;
import org.example.project2.domain.item.repository.ItemRepository;
import org.example.project2.domain.likes.entity.Likes;
import org.example.project2.domain.likes.repository.LikeRepository;
import org.example.project2.domain.member.entity.Member;
import org.example.project2.domain.member.exception.UserNotFoundException;
import org.example.project2.domain.member.repository.MemberRepository;
import org.example.project2.global.exception.PermissionDeniedException;
import org.example.project2.global.springsecurity.PrincipalDetails;
import org.example.project2.global.util.ResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final LikeRepository likeRepository;
    private final MemberRepository memberRepository;

    public ResponseDTO<Page<ItemResponseDto>> getAllItemList(Pageable pageable, Long userId, String search) {
        Page<Items> itemsPage;

        // 검색어가 있을 경우 검색, 없으면 전체 조회
        if (search != null && !search.trim().isEmpty()) {
            itemsPage = itemRepository.findByNameContainingIgnoreCase(search, pageable);
        } else {
            itemsPage = itemRepository.findAll(pageable);
        }

        Page<ItemResponseDto> dtoPage = itemsPage.map(item -> {
            boolean isLiked = false;

            if (userId != null) {
                isLiked = likeRepository.findByMember_IdAndItems_Id(userId, item.getId())
                        .map(Likes::getStatus)
                        .orElse(false);
            }
            return ItemResponseDto.fromEntity(
                    item,
                    isLiked,
                    likeRepository.countByItems_IdAndStatusTrue(item.getId())
            );
        });

        return ResponseDTO.okWithData(dtoPage);
    }

    public ResponseDTO<ItemDetailResponseDto> getItemDetail(long itemId) {

        Items item = itemRepository.findById(itemId)
                .orElseThrow(ItemIdIsInvalidException::new);

        item.updateViewCount();

        ItemDetailResponseDto itemDetailResponseDto = ItemDetailResponseDto.fromEntity(item,
                likeRepository.countByItems_IdAndStatusTrue(item.getId()));
        return ResponseDTO.okWithData(itemDetailResponseDto);
    }

    public ResponseDTO<Void> postItem(PrincipalDetails principalDetails
            , @Valid PostItemRequestDto postItemRequestDto) {

        Member member = principalDetails.getMember();
        Member managedMember = memberRepository.findById(member.getId())
                .orElseThrow(PermissionDeniedException::new);

        itemRepository.save(postItemRequestDto.toEntity(member));
        if (managedMember.getItems().size() > 5) {
            managedMember.updateWriterBadge();
        }

        return ResponseDTO.ok();
    }

    public ResponseDTO<Void> deleteItem(PrincipalDetails principalDetails, long itemId) {

        Member member = principalDetails.getMember();

        Items item = itemRepository.findById(itemId)
                .orElseThrow(ItemIdIsInvalidException::new);

        if (!item.getMember().getId().equals(member.getId())) {
            throw new PermissionDeniedException();
        }

        itemRepository.deleteById(itemId);

        return ResponseDTO.ok();
    }

    public ResponseDTO<Void> updateItem(PrincipalDetails principalDetails,
                                        long itemId,
                                        @Valid PostItemRequestDto postItemRequestDto) {

        Member member = principalDetails.getMember();

        Items item = itemRepository.findById(itemId)
                .orElseThrow(ItemIdIsInvalidException::new);

        if (!item.getMember().getId().equals(member.getId())) {
            throw new PermissionDeniedException();
        }

        item.updateItem(postItemRequestDto.name(),
                postItemRequestDto.description(),
                postItemRequestDto.price(),
                postItemRequestDto.imageUrl(),
                postItemRequestDto.reaction1(),
                postItemRequestDto.reaction2(),
                postItemRequestDto.reaction3(),
                postItemRequestDto.reaction4(),
                postItemRequestDto.reaction5(),
                postItemRequestDto.reaction6(),
                postItemRequestDto.reaction7(),
                postItemRequestDto.reaction8(),
                postItemRequestDto.reaction9()
        );

        return ResponseDTO.ok();
    }

    public ResponseDTO<List<ItemResponseDto>> getMembersItemList(long memberId) {

        memberRepository.findById(memberId)
                .orElseThrow(UserNotFoundException::new);

        List<Items> items = itemRepository.findAllByMember_Id(memberId);
        List<ItemResponseDto> itemResponseDtos = new ArrayList<>();
        for (Items item : items) {
            itemResponseDtos.add(
                    ItemResponseDto.fromEntity(
                            item,
                            false,
                            likeRepository.countByItems_IdAndStatusTrue(item.getId())
                    )
            );
        }

        return ResponseDTO.okWithData(itemResponseDtos);
    }

    public ResponseDTO<List<ItemRankResponseDto>> getItemRanking() {

        List<ItemRankResponseDto> itemRankResponseDtos = new ArrayList<>();

        List<Items> itemsList =
        itemRepository.findTop10ByLikesCount();

        for(Items item : itemsList) {
            itemRankResponseDtos.add(ItemRankResponseDto.fromEntity(item,
                    itemRepository.countByItemIdAndStatusTrue(item.getId())));
        }

        return ResponseDTO.okWithData(itemRankResponseDtos);
    }
}

