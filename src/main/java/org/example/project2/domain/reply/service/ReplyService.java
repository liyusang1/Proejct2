package org.example.project2.domain.reply.service;

import lombok.RequiredArgsConstructor;
import org.example.project2.domain.item.entity.Items;
import org.example.project2.domain.item.exception.ItemIdIsInvalidException;
import org.example.project2.domain.item.repository.ItemRepository;
import org.example.project2.domain.member.entity.Member;
import org.example.project2.domain.reply.dto.request.PostReplyRequestDto;
import org.example.project2.domain.reply.dto.response.ReplyResponseDto;
import org.example.project2.domain.reply.entity.Replies;
import org.example.project2.domain.reply.exception.ReplyIdIsInvalidException;
import org.example.project2.domain.reply.repository.ReplyRepository;
import org.example.project2.global.exception.PermissionDeniedException;
import org.example.project2.global.springsecurity.PrincipalDetails;
import org.example.project2.global.util.ResponseDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ReplyService {

    private final ReplyRepository replyRepository;
    private final ItemRepository itemRepository;

    public ResponseDTO<List<ReplyResponseDto>> getAllReplies(long itemId) {

        itemRepository.findById(itemId)
                .orElseThrow(ItemIdIsInvalidException::new);

        List<ReplyResponseDto> replyResponseDtos =
                replyRepository.findAllByItems_Id(itemId).stream()
                        .map(ReplyResponseDto::fromEntity)
                        .collect(Collectors.toList());

        return ResponseDTO.okWithData(replyResponseDtos);
    }

    public ResponseDTO<Void> postReplies(
            PrincipalDetails principalDetails,
            PostReplyRequestDto postReplyRequestDto) {

        Member member = principalDetails.getMember();

        Optional<Items> itemsOptional =
                itemRepository.findById(postReplyRequestDto.itemId());

        if (itemsOptional.isEmpty()) {
            throw new ItemIdIsInvalidException();
        }

        replyRepository.save(postReplyRequestDto.toEntity(member,
                itemsOptional.get()));

        return ResponseDTO.ok();
    }

    public ResponseDTO<Void> deleteReplies(PrincipalDetails principalDetails, long replyId) {

        Member member = principalDetails.getMember();
        Optional<Replies> repliesOptional = replyRepository.findById(replyId);

        if (repliesOptional.isEmpty()) {
            throw new ReplyIdIsInvalidException();
        }

        if(!Objects.equals(member.getId(), repliesOptional.get().getMember().getId())) {
            throw new PermissionDeniedException();
        }

        replyRepository.deleteById(replyId);

        return ResponseDTO.ok();
    }
}

