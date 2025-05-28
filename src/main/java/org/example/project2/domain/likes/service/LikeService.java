package org.example.project2.domain.likes.service;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.project2.domain.item.entity.Items;
import org.example.project2.domain.item.exception.ItemIdIsInvalidException;
import org.example.project2.domain.item.repository.ItemRepository;
import org.example.project2.domain.likes.dto.request.PostLikeRequestDto;
import org.example.project2.domain.likes.entity.Likes;
import org.example.project2.domain.likes.repository.LikeRepository;
import org.example.project2.global.springsecurity.PrincipalDetails;
import org.example.project2.global.util.ResponseDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class LikeService {

    private final LikeRepository likeRepository;
    private final ItemRepository itemRepository;

    public ResponseDTO<Void> postLike(PrincipalDetails principalDetails
            , @Valid PostLikeRequestDto postItemRequestDto) {

        Items items = itemRepository.findById(postItemRequestDto.itemId())
                .orElseThrow(ItemIdIsInvalidException::new);

        Optional<Likes> likesOptional = likeRepository.findByMember_IdAndItems_Id(
                principalDetails.getMember().getId(),
                items.getId());

        if (likesOptional.isPresent()) {
            Likes likes = likesOptional.get();
            likes.toggleStatus();
            likeRepository.save(likes);
        } else {
            likeRepository.save(postItemRequestDto.toEntity(principalDetails.getMember(), items));
        }

        return ResponseDTO.ok();
    }
}

