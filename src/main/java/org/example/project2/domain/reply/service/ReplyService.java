package org.example.project2.domain.reply.service;

import lombok.RequiredArgsConstructor;
import org.example.project2.domain.item.exception.ItemIdIsInvalidException;
import org.example.project2.domain.item.repository.ItemRepository;
import org.example.project2.domain.reply.dto.response.ReplyResponseDto;
import org.example.project2.domain.reply.repository.ReplyRepository;
import org.example.project2.global.util.ResponseDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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
}

