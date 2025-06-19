package org.example.project2.domain.likes.service;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.project2.domain.item.entity.Items;
import org.example.project2.domain.item.exception.ItemIdIsInvalidException;
import org.example.project2.domain.item.repository.ItemRepository;
import org.example.project2.domain.likes.dto.request.PostLikeRequestDto;
import org.example.project2.domain.likes.entity.Likes;
import org.example.project2.domain.likes.repository.LikeRepository;
import org.example.project2.domain.member.entity.Member;
import org.example.project2.domain.member.exception.UserNotFoundException;
import org.example.project2.domain.notifications.entity.Notifications;
import org.example.project2.domain.notifications.repository.NotificationsRepository;
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
    private final NotificationsRepository notificationsRepository;

    public ResponseDTO<Void> postLike(PrincipalDetails principalDetails
            , @Valid PostLikeRequestDto postLikeRequestDto) {

        if(principalDetails==null){
            throw new UserNotFoundException();
        }

        Member member = principalDetails.getMember();

        Items items = itemRepository.findById(postLikeRequestDto.itemId())
                .orElseThrow(ItemIdIsInvalidException::new);

        Optional<Likes> likesOptional = likeRepository.findByMember_IdAndItems_Id(
                principalDetails.getMember().getId(),
                items.getId());

        boolean displayNotification = false;

        if (likesOptional.isPresent()) {
            Likes likes = likesOptional.get();
            likes.toggleStatus();
            if (likes.getStatus()) {
                displayNotification = true;
            }
            likeRepository.save(likes);
        } else {
            displayNotification = true;
            likeRepository.save(postLikeRequestDto.toEntity(principalDetails.getMember(), items));
        }

        if(displayNotification){
            Long itemId = postLikeRequestDto.itemId();
            Notifications notification = Notifications.builder()
                    .title("새로운 좋아요 알림 ❤\uFE0F")
                    .link("item/" + itemId)
                    .content("회원님의 게시물 '" + items.getName() + "'에 " + member.getMemberBase().getNickname()
                            + "님이 좋아요를 눌렀습니다!")
                    .member(items.getMember())
                    .build();
            notificationsRepository.save(notification);
        }

        return ResponseDTO.ok();
    }
}

